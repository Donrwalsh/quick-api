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

if [ -z "$impl" ]; then impl=none; fi

if [ \( "$api" = "employees" \) -a \( "$impl" != "jdbc" -a "$impl" != "jdbc_t" \) -a \( "$impl" != "all" -a "$impl" != "none" \) ]; then
	echo -e "ERROR: Invalid Implementation Argument: $api/$impl.\n$usage"
	exit 1
fi

echo -e "[ENV:$env] [API:$api] [IMPL:$impl]\n"

# #--- File Preparer
rm -rfv staging/frontend/*
cp -rv frontend/* staging/frontend/

if [ "$impl" == "jdbc" -o "$impl" == "all" ]; then
	mvn clean package -f api/JDBC/pom.xml -Dmaven.test.skip=true
	cp -rv api/JDBC/target/JDBC.war staging/JDBC.war
fi

if [ "$impl" == "jdbc_t" -o "$impl" == "all" ]; then
	mvn clean package -f api/JDBC_T/pom.xml -Dmaven.test.skip=true
	cp -rv api/JDBC_T/target/JDBC_T.war staging/JDBC_T.war
fi

#--- File Migrater
if [ "$env" == "dev" -o "$env" == "all" ]; then
	cd dev-env
	vagrant ssh -c "sudo rm -rfv /var/lib/tomcat8/webapps/ROOT/*;
					sudo cp -rv /scratch/frontend/* /var/lib/tomcat8/webapps/ROOT/;
					sudo cp -rv /scratch/*.war /var/lib/tomcat8/webapps/;"
	cd ../
fi

if [ "$env" == "prod" -o "$env" == "all" ]; then
	scp -r staging/* deploy@quick-api.com:/home/deploy
	ssh -t deploy@quick-api.com "sudo rm -rfv /var/lib/tomcat8/webapps/ROOT/*;
		sudo cp -rv /home/deploy/frontend/* /var/lib/tomcat8/webapps/ROOT/;
		sudo cp -rv /home/deploy/*.war /var/lib/tomcat8/webapps/ ;"
fi
