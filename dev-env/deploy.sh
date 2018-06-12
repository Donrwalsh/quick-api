#!/bin/sh
rm -rfv dev-box/*
mkdir -p dev-box/frontend
cp ../frontend/* dev-box/frontend/ -r
mvn package -f ../api/employees/pom.xml
cp ../api/employees/target/employees-0.0.1-SNAPSHOT.war.original dev-box/employees.war
vagrant ssh -c "sudo rm -rfv /var/lib/tomcat8/webapps/ROOT/*;
				sudo cp -rv /scratch/frontend/* /var/lib/tomcat8/webapps/ROOT/;
				sudo cp -rv /scratch/*.war /var/lib/tomcat8/webapps/;"