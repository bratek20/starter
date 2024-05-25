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
catalog_file="version-catalog/build.gradle.kts"
root_file="build.gradle.kts"

# Extract current versions and increment them
catalog_version=$(grep -oP '(?<=val catalogVersion = ")[0-9]+\.[0-9]+\.[0-9]+' $catalog_file)
new_catalog_version=$(increment_patch $catalog_version)

bratek20_starter_version=$(grep -oP '(?<=val bratek20StarterVersion = ")[0-9]+\.[0-9]+\.[0-9]+' $catalog_file)
new_bratek20_starter_version=$(increment_patch $bratek20_starter_version)

version_all=$(grep -oP '(?<=val versionAll = ")[0-9]+\.[0-9]+\.[0-9]+' $root_file)
new_version_all=$(increment_patch $version_all)

# Update versions in version-catalog/build.gradle.kts
sed -i -E "s/(val catalogVersion = \")[0-9]+\.[0-9]+\.[0-9]+(\")/val catalogVersion = \"$new_catalog_version\"/" $catalog_file
sed -i -E "s/(val bratek20StarterVersion = \")[0-9]+\.[0-9]+\.[0-9]+(\")/val bratek20StarterVersion = \"$new_bratek20_starter_version\"/" $catalog_file

# Update version in root build.gradle.kts
sed -i -E "s/(val versionAll = \")[0-9]+\.[0-9]+\.[0-9]+(\")/val versionAll = \"$new_version_all\"/" $root_file

# Output the new versions set
echo "Updated versions:"
echo "catalogVersion: $new_catalog_version"
echo "bratek20StarterVersion: $new_bratek20_starter_version"
echo "versionAll: $new_version_all"