#!/bin/bash

./preparePublishPatch.sh

cd ..
./gradlew -p version-catalog publish
./gradlew :bratek20-architecture:publish :bratek20-utils:publish
