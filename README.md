# lein-deploy-app

A Leiningen plugin to push application uberjars to a simple directory structure
in an s3 bucket, organized by application and branch.

This plugin is similar to s3-wagon in that you configure your project to deploy
to s3, but lein-deploy-app is for deploying app uberjars as opposed to libs,
and does not store the uberjars in a Maven repo (as lein-deploy-uberjar does).

A workflow involving lein-deploy-app might involve a ci server or an engineer
calling lein deploy-app. Then a systems integration framework like Chef can
easily pull down the appropriate application, version, and branch in a staging
or production environment.

## Usage

Put `[lein-deploy-app "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your
project.clj.

```bash
  $ lein deploy-app [--branch <NAME>] # current git branch if not specified
```

## To Do

[ ] specify bucket in project.clj file

[ ] use git branch in working dir

  [ ] optionally specify name of branch

[ ] upload uberjar for the current project version
    to BUCKET/app/branch/app-uberjar-ver.jar

## License

Author: Robert Levy / @rplevy-draker

Copyright © 2012 Draker, Inc.

Distributed under the Eclipse Public License, the same as Clojure.
