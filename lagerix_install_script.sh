#!/bin/bash

sudo apt-get -y install libstdc++6:i386 
sudo apt-get -y install lib32z1

wget dl.google.com/android/adt/adt-bundle-linux-x86_64-20131030.zip
wget mirror.dkd.de/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.zip

unzip adt-bundle-linux-x86_64-20131030.zip
unzip apache-maven-3.1.1-bin.zip

rm adt-bundle-linux-x86_64-20131030.zip
rm apache-maven-3.1.1-bin.zip


export PATH=$PATH:$PWD/adt-bundle-linux-x86_64/sdk/platform-tools
export PATH=$PATH:$PWD/adt-bundle-linux-x86_64/sdk/tools
export PATH=$PATH:$PWD/apache-maven-3.1.1/bin
export ANDROID_HOME=$PWD/adt-bundle-linux-x86_64-20131030/sdk
$PWD/adt-bundle-linux-x86_64-20131030/sdk/tools/android update sdk -u -a --filter platform-tools,android-18


mvn install:install-file -Dfile=LagerixAndroidApp/libs/CaptureActivity.apklib -DgroupId=de.hscoburg.etif.vbis.lagerix.android -DartifactId=zxing-android -Dversion=2.2.0 -Dpackaging=apklib

mvn install


sudo /usr/local/glassfish-4.0/bin/asadmin start-domain

sudo /usr/local/glassfish-4.0/bin/asadmin start-database --dbhost 0.0.0.0 --dbport 1527 



sudo /usr/local/glassfish-4.0/bin/asadmin create-javamail-resource --mailhost mail.gmx.net --mailuser lagerix@gmx.de --fromaddress lagerix@gmx.de --property mail.smtp.password=lagerix123:mail.smtp.auth=true mail/Email

sudo /usr/local/glassfish-4.0/bin/asadmin create-auth-realm --classname com.sun.enterprise.security.ee.auth.realm.jdbc.JDBCRealm --property jaas-context=jdbcRealm:datasource-jndi="jdbc/__default":user-table=users:user-name-column=email:password-column=password:group-table=users_groups:group-name-column=groupname:digest-algorithm=SHA-512 userMgmtJdbcRealm




sudo cp $PWD/LagerixPrj/target/LagerixPrj-1.0.0.ear /usr/local/glassfish-4.0/glassfish/domains/domain1/autodeploy/
