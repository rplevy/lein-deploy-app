# lein-deploy-app

A Leiningen plugin to for pushing application uberjars to s3. This is similar to
s3-wagon; whereas s3-wagon enables deploying libs to a Maven repo in s3 using
lein deploy, lein deploy-app deploys the application uberjar to a simple
non-Maven directory structure.

## Usage

Use this for user-level plugins:

Put `[lein-deploy-app "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your
`:user` profile, or if you are on Leiningen 1.x do `lein plugin install
lein-deploy-app 0.1.0-SNAPSHOT`.

Use this for project-level plugins:

Put `[lein-deploy-app "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your project.clj.

  $ lein deploy-app [--branch <NAME>] # current git branch if not specified

## To Do

[ ] specify bucket in project.clj file
[ ] use git branch in working dir
  [ ] optionally specify name of branch
[ ] upload uberjar for the current project version
    to BUCKET/app/branch/app-uberjar-ver.jar

## License

Copyright © 2012 Draker Labs

Distributed under the Eclipse Public License, the same as Clojure.
