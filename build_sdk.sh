#!/bin/sh
#
# Creates the SDK JAR. Using an AAR was not feasible for deployment so we 
# are using JARs until AAR support within Gradle and Eclipse improves. 
#

# Start by building volley and the SDK
./gradlew build 

# Create a temp dir and move there, copy over files to put in JAR
REPO=`pwd`
T="$(date +%s)"
DIR=/tmp/sdk$T/
mkdir $DIR
cp -R volley/build/classes/release/* $DIR
cp -R catalyze-android/build/classes/release/* $DIR
#cp catalyze-android/src/main/libs/*-jarjar-*.jar $DIR/
cp catalyze-android/src/main/libs/http*-*.jar $DIR/
cd $DIR
#jar xf httpcore-jarjar-4.3.jar
#jar xf httpmime-jarjar-4.3.1.jar
jar xf httpcore-4.3.jar
jar xf httpmime-4.3.1.jar
jar cf catalyze-sdk.jar org com io
cp catalyze-sdk.jar $REPO/
