#!/bin/sh
#base box: geerlingguy/ubuntu1604
vagrant destroy --force
vagrant up
vagrant ssh -c "sudo apt-get update && apt-get upgrade;
				sudo timedatectl set-timezone America/Chicago;
				date;
				sudo add-apt-repository ppa:webupd8team/java;
				sudo apt-get update;
				sudo apt-get install oracle-java8-installer -y;
				java -version;
				sudo apt-get install tomcat8 -y;
				sudo apt-get install -y apache2;
				sudo apt-get install mysql-server;"
