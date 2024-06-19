#!/bin/bash

./preparePublishPatch.sh

cd ..
./gradlew -p version-catalog publish
./gradlew publish
