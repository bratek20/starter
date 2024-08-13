#!/bin/bash

extraGradlewArgs=$1

echo "Publishing patch version with extra args: $extraGradlewArgs"

# Run the prepare script
./preparePublishPatch.sh

# Navigate to the parent directory
cd ..

# Ensure Gradle daemons are stopped before starting
./gradlew --stop

# Run the first Gradle task with detailed logging
echo "Running ./gradlew -p version-catalog publish $extraGradlewArgs"
./gradlew -p version-catalog publish $extraGradlewArgs --info
if [ $? -ne 0 ]; then
    echo "First Gradle task failed. Exiting."
    exit 1
fi

# Run the second Gradle task with detailed logging
echo "Running ./gradlew publish $extraGradlewArgs"
./gradlew clean publish $extraGradlewArgs --info
if [ $? -ne 0 ]; then
    echo "Second Gradle task failed. Exiting."
    exit 1
fi

echo "Build and publish completed successfully."
