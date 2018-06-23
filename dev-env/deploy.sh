#!/bin/sh
rm -rfv dev-box/*
mkdir -p dev-box/frontend
cp ../frontend/* dev-box/frontend/ -r
mvn package -f ../api/JDBC/pom.xml -Dmaven.test.skip=true
mvn package -f ../api/JDBC_T/pom.xml -Dmaven.test.skip=true
cp ../api/JDBC/target/JDBC-0.0.1-SNAPSHOT.war.original dev-box/JDBC.war
cp ../api/JDBC_T/target/JDBC_T-0.0.1-SNAPSHOT.war.original dev-box/JDBC_T.war
vagrant ssh -c "sudo rm -rfv /var/lib/tomcat8/webapps/ROOT/*;
				sudo cp -rv /scratch/frontend/* /var/lib/tomcat8/webapps/ROOT/;
				sudo cp -rv /scratch/*.war /var/lib/tomcat8/webapps/;"