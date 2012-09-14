# lein-deploy-app

A Leiningen plugin to push application uberjars to an AWS s3 bucket, organized
by application and branch.

This plugin is similar to s3-wagon in that you configure your project to deploy
to s3, but whereas "lein deploy" is for deploying libs, lein-deploy-app is for
deploying app uberjars and does not store the uberjars in a Maven repo (as
lein-deploy-uberjar does).

A workflow involving lein-deploy-app might involve a ci server or an engineer
calling lein deploy-app. Then an operations automation framework like Chef can
easily pull down the appropriate application, version, and branch in a staging
or production environment.

## Usage

1. Put `[lein-deploy-app "0.1.0"]` into the `:plugins` vector of your
project.clj.

2. Add a project.clj configuration mapping for deploy-app:
```clojure
  :deploy-app {:s3-bucket "s3p://mybucket/releases/"
               :creds :env}
```

:s3-bucket is the bucket/path where you want to deploy your uberjars.

:creds is the credentials type. Presently only :env is supported.
If using :env, specify your s3 credentials using the environment variables
LEIN_USERNAME and LEIN_PASSWORD.

To deploy your application's uberjar to s3 for the current git branch:

```bash
  $ lein deploy-app
```

To specify something other than a current git branch:

```bash
  $ lein deploy-app --branch <NAME>
```

## To Do

### :gpg credentials option

If using :gpg, create a gpg encrypted ~/.lein/credentials.clj.gpg file out of a
credentials.clj file of the following form:

```clojure
{"s3p://mybucket/releases/" {:username "usernamegoeshere"
                             :passphrase "passphrasegoeshere"}}
```

## License

Author: Robert Levy / @rplevy-draker

Copyright © 2012 Draker, Inc.

Distributed under the Eclipse Public License, the same as Clojure.
