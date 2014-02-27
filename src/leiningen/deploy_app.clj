(ns leiningen.deploy-app
  (:require [clojure.string :as str]
            [leiningen.core.user :as user]
            [clj-jgit.porcelain :as jgit]
            [aws.sdk.s3 :as s3]
            [leiningen.uberjar :as uj]
            [me.raynes.fs :as fs]
            [clojure.java.io :as io]))

(defn aws-creds [{:keys [username passphrase] :as bucket-spec}]
  (when-not (and username passphrase) 
    (throw (Exception. (format "Invalid credentials provided for s3 bucket: %s" bucket-spec))))
  {:access-key username :secret-key passphrase})

(defn valid-bucket? [cred bucket]
  (or (s3/bucket-exists? cred bucket)
      (throw (Exception.
              (format "Bucket %s does not exist. Check for typos or create it."
                      bucket)))))

(defn deploy-app
  "Build uberjar and deploy to s3"
  [project & args]
  (let [branch (or ((apply hash-map args) "--branch")
                   (jgit/with-repo "." (jgit/git-branch-current repo)))
        cred (aws-creds (user/resolve-credentials (:deploy-app project)))
        [bucket path] (-> (:url (:deploy-app project))
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
        (s3/put-object cred bucket key instr
                       {:content-length (fs/size uj-path)})))))
