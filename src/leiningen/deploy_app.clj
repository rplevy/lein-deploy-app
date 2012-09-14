(ns leiningen.deploy-app
  (:require [clojure.string :as str]
            [clj-jgit.porcelain :as jgit]))

(defn current-branch
  "get the current branch of a git repo"
  [repo-path]
  (jgit/with-repo repo-path
    (-> repo .getRepository .getFullBranch (str/replace #"^refs/heads/" ""))))

(defn deploy-app
  "Build uberjar and deploy to s3"
  [project & args]
  (let [branch (or ((apply hash-map args) "--branch")
                   (current-branch "."))]
    (println (format "Deploying uberjar for branch %s..." branch))))