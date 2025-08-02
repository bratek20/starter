#!/bin/bash

hlaFolderPath=../hla/infra
profileName=main
moduleName=$1

java -jar tool.jar update $hlaFolderPath $profileName $moduleName