(defproject rplevy/lein-deploy-app "0.2.1"
  :description "A Leiningen plugin to push application uberjars to AWS s3"
  :url "https://clojars.org/lein-deploy-app"
  :dependencies [[org.clojure/clojure "1.5.1"]

                 ;; temporary fix to deal with a transitive dependency
                 ;; that does not depend on fixed-versioned libraries.
                 [clj-aws-s3 "0.3.6"
                  :exclusions [org.codehaus.jackson/jackson-core-asl
                               org.codehaus.jackson/jackson-mapper-asl]]
                 [org.codehaus.jackson/jackson-core-asl "1.7.3"]
                 [org.codehaus.jackson/jackson-mapper-asl "1.7.3"]

                 [me.raynes/fs "1.4.5"]
                 [dsabanin/clj-jgit "0.2"]]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true)