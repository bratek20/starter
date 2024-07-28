#!/bin/bash

# Function to increment the patch version
increment_patch() {
  version=$1
  major=$(echo $version | cut -d. -f1)
  minor=$(echo $version | cut -d. -f2)
  patch=$(echo $version | cut -d. -f3)
  new_patch=$((patch + 1))
  echo "$major.$minor.$new_patch"
}

# File paths
catalog_file="../version-catalog/build.gradle.kts"

# Extract current versions and increment them
catalog_version=$(grep -oP '(?<=val catalogVersion = ")[0-9]+\.[0-9]+\.[0-9]+' $catalog_file)
new_catalog_version=$(increment_patch $catalog_version)

# Update versions in version-catalog/build.gradle.kts
sed -i -E "s/(val catalogVersion = \")[0-9]+\.[0-9]+\.[0-9]+(\")/val catalogVersion = \"$new_catalog_version\"/" $catalog_file

# Output the new versions set
echo "catalogVersion: $new_catalog_version"

cd ..
./gradlew -p version-catalog clean publish