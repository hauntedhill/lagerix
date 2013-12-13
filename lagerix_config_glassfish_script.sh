#!/bin/bash


sudo /usr/local/glassfish-4.0/bin/asadmin start-domain

sudo /usr/local/glassfish-4.0/bin/asadmin start-database --dbhost 0.0.0.0 --dbport 1527 



sudo /usr/local/glassfish-4.0/bin/asadmin create-javamail-resource --mailhost mail.gmx.net --mailuser lagerix@gmx.de --fromaddress lagerix@gmx.de --property mail.smtp.password=lagerix123:mail.smtp.auth=true mail/Email

sudo /usr/local/glassfish-4.0/bin/asadmin create-auth-realm --classname com.sun.enterprise.security.ee.auth.realm.jdbc.JDBCRealm --property jaas-context=jdbcRealm:datasource-jndi="jdbc/__default":user-table=users:user-name-column=email:password-column=password:group-table=users_groups:group-name-column=groupname:digest-algorithm=SHA-512 userMgmtJdbcRealm




#sudo cp $PWD/LagerixPrj/target/LagerixPrj-1.0.0.ear /usr/local/glassfish-4.0/glassfish/domains/domain1/autodeploy/