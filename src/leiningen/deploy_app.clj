(ns leiningen.deploy-app
  (:require [clojure.string :as str]
            [clj-jgit.porcelain :as jgit]
            [aws.sdk.s3 :as s3]
            [leiningen.uberjar :as uj]
            [clojure.java.io :as io]))

(defn current-branch
  "get the current branch of a git repo"
  [repo-path]
  (jgit/with-repo repo-path
    (-> repo .getRepository .getFullBranch (str/replace #"^refs/heads/" ""))))

(defn aws-creds [cred-type]
  (condp = cred-type
    :env {:access-key (System/getenv "LEIN_USERNAME")
          :secret-key (System/getenv "LEIN_PASSWORD")}
    :gpg (throw (Exception. "GPG credential type not yet supported."))
    (throw (Exception. (format "Unknown credential type: %s" cred-type)))))

(defn valid-bucket? [cred bucket]
  (or (s3/bucket-exists? cred bucket)
      (throw (Exception.
              (format "Bucket %s does not exist. Check for typos or create it."
                      bucket)))))

(defn deploy-app
  "Build uberjar and deploy to s3"
  [project & args]
  (let [branch (or ((apply hash-map args) "--branch")
                   (current-branch "."))
        cred (aws-creds (:creds (:deploy-app project)))
        [bucket path] (-> (:s3-bucket (:deploy-app project))
                          (str/replace #"^s3p://" "")
                          (str/split #"/" 2))
        uj-path (uj/uberjar project) ; compile uberjar
        key (format "%s/%s/%s"
                    (str (if path (str path "/") "") (:name project))
                    branch (last (str/split uj-path #"/")))]
    (when (valid-bucket? cred bucket)
      (println (format "Deploying uberjar %s for branch %s to s3p://%s/%s..."
                       uj-path branch bucket key))
      (with-open [instr (io/input-stream uj-path)]
        (s3/put-object cred bucket key instr)))))
