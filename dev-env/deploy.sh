#!/bin/sh

# Cast positional parameters to lowercase
env=${1,,} 
api=${2,,}
impl=${3,,}

usage="Usage: deploy.sh [environment] [api] [implementation]"

#--- Command Line Argument Parser
if [ \( -z "$env" \) -o \( "$env" != "dev" -a "$env" != "prod" -a "$env" != "all" \) ];
then
	echo -e "ERROR: Invalid Environment argument: $env\n$usage"
	exit 1
fi

if [ \( -z "$api" \) -o \( "$api" != "employees" -a "$api" != "e" \) ]; then
	echo -e "ERROR: Invalid API argument: $api\n$usage"
	exit 1
elif [[ "$api" =~ ^(employees|e)$ ]]; then
	api=employees
fi

if [ -z "$impl" ]; then impl=all; fi

if [ \( "$api" = "employees" \) -a \( "$impl" -a "$impl" != "jdbc" -a "$impl" != "jdbc_t" \) -a \( "$impl" != "all" \) ]; then
	echo -e "ERROR: Invalid Implementation Argument: $api/$impl.\n$usage"
	exit 1
fi

echo -e "[ENV:$env] [API:$api] [IMPL:$impl]\n"

if [ "$env" = "prod" -o "$env" = "all" ]; then
	echo "Prod deploy is unavailable at this time"
fi

#--- File Preparer
#Frontend
#Selective Maven Packaging
#Populating dev-box or whatever folder

#--- File Migrater

#--- Tester?

#rename dev-box to 'staging' or something else environment-agnostic


#rm -rfv dev-box/*
#mkdir -p dev-box/frontend
#cp ../frontend/* dev-box/frontend/ -rv


#mvn package -f ../api/JDBC/pom.xml -Dmaven.test.skip=true
#mvn package -f ../api/JDBC_T/pom.xml -Dmaven.test.skip=true
#cp ../api/JDBC/target/JDBC-0.0.1-SNAPSHOT.war.original dev-box/JDBC.war
#cp ../api/JDBC_T/target/JDBC_T-0.0.1-SNAPSHOT.war.original dev-box/JDBC_T.war
#vagrant ssh -c "sudo rm -rfv /var/lib/tomcat8/webapps/ROOT/*;
#				sudo cp -rv /scratch/frontend/* /var/lib/tomcat8/webapps/ROOT/;
#				sudo cp -rv /scratch/*.war /var/lib/tomcat8/webapps/;"
				
				
#Next thing is options to build specific implementations.'