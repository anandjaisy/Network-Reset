## Build native image on local

- `./gradlew dockerBuildNative --dry-run` // To check what steps will be executed
- Step 1 - `./gradlew clean`
- Step 2 - `./gradlew build`
- Step 3 - `./gradlew nativeCompile` // This step in not required in CI, because docker file has this step
- Step 4 - `./gradlew dockerfileNative`
- Step 5 - `./gradlew buildNativelayersTask`
- Step 6 - `./gradlew dockerPrepareContext`
- 
- 
### For optimized native image
- Step 1 - `./gradlew clean`
- Step 2 - `./gradlew build`
- Step 3 - `./gradlew nativeOptimizedCompile` // This step in not required in CI, because docker file has this step
- Step 4 - `./gradlew optimizedDockerfileNative`
- Step 5 - `./gradlew optimizedBuildNativelayersTask`
- Step 6 - `./gradlew optimizeddockerPrepareContext`

`docker buildx build -f DockerfileNative -t micronuat-0.1 .` // micronuat-0.1 is the image name


#### Health check 
https://ramsaycompanion-ppe.ramsayhealth-gcp.com.au/ramsay-companion-outlookaddin/health
or
https://ramsaycompanion-ppe.ramsayhealth-gcp.com.au/ramsay-companion-outlookaddin/health/liveness