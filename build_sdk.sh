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
# cp -R volley/build/classes/release/* $DIR
cp -R catalyze-android/build/intermediates/classes/release/* $DIR
#cp catalyze-android/src/main/libs/*-jarjar-*.jar $DIR/
cp catalyze-android/src/main/libs/*-*.jar $DIR/
cd $DIR

# dont need to include the android support jar
rm android-support-v4.jar

jar xf gson*-*.jar
jar xf okhttp*-*.jar
jar xf retrofit*-*.jar
jar cf catalyze-sdk.jar io com retrofit
cp catalyze-sdk.jar $REPO/
