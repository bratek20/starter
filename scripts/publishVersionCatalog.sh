#!/bin/bash

# Determine the directory where this script is located
SCRIPT_DIR=$(dirname "$(realpath "$0")")

# Determine the project root directory (assume it's the parent of the script's directory)
PROJECT_ROOT=$(realpath "$SCRIPT_DIR/..")

# Change to the project root directory
cd "$PROJECT_ROOT"

# Execute the gradle command
./gradlew -p version-catalog publish