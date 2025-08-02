# bratek20 starter

Starter aggregating multiple base gradle modules

## Building

To build project it is needed to configure gradle.properties file in the root of your user with the following content:
```
githubActor=<your_github_username>
githubToken=<your_github_token>
```
As an alternative you can create environment variables
```
GITHUB_ACTOR (your_github_username) 
GITHUB_TOKEN (your_github_token)
```

## Testing

Simple `./gradlew test` can do. There are other projects like `examples` and `bratek20-logs` that need different command.
I recommend to use saved configurations for IntelliJ IDEA from .run directory.

## Publishing

All publish scripts are stored in `bash` folder. All scripts assume you execute them EXACTLY from `bash` folder.

To publish you NEED TO BE collaborator of `bratek20-starter` repository!

To publish new version of architecture
1. Open console in `bash` folder
2. Change `version` in `bratek20-architecture/build.gradle.kts`
3. Set the same version in `version-catalog/build.gradle.kts` in variable `b20ArchVersion`
4. Run `./arch/publish.sh` script (or `Publish arch` configuration) to publish artifact to Github Packages and TSG repos
5. Run `./versionCatalogPublish.sh` script to publish new version of version catalog to Github Packages and TSG repos
6. Use new `catalogVersion` from `version-catalog/build.gradle.kts` (it was updated automatically) in other projects to use published code
7. Remember to commit and push changes from build.gradle.kts files
