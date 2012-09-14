(defproject lein-deploy-app "0.1.0"
  :description "A Leiningen plugin to push application uberjars to AWS s3"
  :url "https://clojars.org/lein-deploy-app"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-aws-s3 "0.3.2"]
                 [dsabanin-clj-jgit "0.1.3"]]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true)