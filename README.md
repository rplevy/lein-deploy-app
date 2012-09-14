# lein-deploy-app

A Leiningen plugin to push application uberjars to an s3 bucket.

Differences between lein-deploy-app and s3-wagon or lein-deploy-uberjar:
whereas s3-wagon enables deploying libs to a Maven repo in s3, and
lein-deploy-uberjar enables deploying an application uberjar to Maven,
lein-deploy-app deploys the application uberjar to a simple non-Maven
directory structure. A key advantage is that the jars are grouped by branch.

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
