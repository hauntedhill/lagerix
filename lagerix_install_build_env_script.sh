#!/bin/bash

sudo apt-get -y install libstdc++6:i386 
sudo apt-get -y install lib32z1

wget -nc dl.google.com/android/adt/adt-bundle-linux-x86_64-20131030.zip
wget -nc mirror.dkd.de/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.zip

unzip -o adt-bundle-linux-x86_64-20131030.zip
unzip -o apache-maven-3.1.1-bin.zip

export ANDROID_HOME=$PWD/adt-bundle-linux-x86_64-20131030/sdk

$PWD/adt-bundle-linux-x86_64-20131030/sdk/tools/android update sdk -u -a --filter platform-tools,android-18


$PWD/apache-maven-3.1.1/bin/mvn install:install-file -Dfile=LagerixAndroidApp/libs/CaptureActivity.apklib -DgroupId=de.hscoburg.etif.vbis.lagerix.android -DartifactId=zxing-android -Dversion=2.2.0 -Dpackaging=apklib

$PWD/apache-maven-3.1.1/bin/mvn install
