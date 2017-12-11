#!/bin/sh
./gradlew publishApkPublicationToMavenLocal
adb -s emulator-5554 push test-butler-app/build/outputs/apk/test-butler-app-release.apk /data/local/tmp/com.linkedin.android.testbutler
adb -s emulator-5554 shell pm install -r "/data/local/tmp/com.linkedin.android.testbutler"
