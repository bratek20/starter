# bratek20 starter

Starter aggregating multiple base gradle modules

## Testing

User `Test all modules` IntelliJ run configurations

## Publishing

All publish scripts are stored in `bash` folder. All scripts assume you execute them EXACTLY from `bash` folder.
Use run configurations to publish specified module.

Publish sends artifacts to 2 places
1. `bratek20-starter` github artifacts
   * you NEED TO BE collaborator of `bratek20-starter` repository to do it
   * you need `githubActor` and `githubToken` gradle properties set
2. TSG artifactory
   * you need `artifactoryUsername` and `artifactoryPassword` gradle properties set
   
All modules are published the same way. Example publish steps for `bratek20-architecture` module.
1. Change `version` in `bratek20-architecture/build.gradle.kts`
2. Set the same version in `bratek20-catalog/build.gradle.kts` in variable `b20ArchVersion`
3. Increase `catalogVersion` in `bratek20-catalog/build.gradle.kts` 
4. Run `[arch] publish` configuration
5. Run `[catalog] publish` configuration
6. Use new `catalogVersion` in other projects to use published code
7. Remember to commit and push changes from build.gradle.kts files
