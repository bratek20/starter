#!/bin/bash

hlaFolderPath=../hla/arch
profileName=main
moduleName=$1

java -jar tool.jar start $hlaFolderPath $profileName $moduleName