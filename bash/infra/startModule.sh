#!/bin/bash

hlaFolderPath=../../hla/infra
profileName=main
moduleName=$1

java -jar ../tool.jar start $hlaFolderPath $profileName $moduleName