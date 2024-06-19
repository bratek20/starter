#!/bin/bash

extraGradlewArgs=$1

echo "Publishing patch version with extra args: $extraGradlewArgs"

./preparePublishPatch.sh

cd ..
./gradlew -p version-catalog publish $extraGradlewArgs
./gradlew publish $extraGradlewArgs
