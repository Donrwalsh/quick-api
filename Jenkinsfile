pipeline {
    agent any
	tools {
		maven 'Maven 3.5.4'
		jdk 'JDK8'
	}

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
				
				dir("api/JDBC") {
					sh 'mvn clean package -Dmaven.test.skip=true'
					dir("target") {
						stash includes: 'JDBC.war', name: 'JDBC'
					}
				}
				dir("api/JDBC_T") {
					sh 'mvn clean package -Dmaven.test.skip=true'
					dir("target") {
						stash includes: 'JDBC_T.war', name: 'JDBC_T'
					}
				}
            }
        }
        stage('Deploy') {
            steps {
				echo 'Deploying....'
				timeout(5) {
					waitUntil {
						script {
							def r = sh script: 'wget -q http://192.168.33.10:8080/JDBC/employees/12345 -O /dev/null', returnStatus: true
							return (r == 0);
							}
						}
					}
				node ('stage') {
				dir('/var/lib/tomcat8/webapps/') {
					unstash "JDBC"
					unstash "JDBC_T"
					}
				}
            }
        }
        stage('Test') {
            steps {
				dir("api/test") {
					sh 'mvn clean test -DargLine=\\"-Dkarate.env=stg\\"'
				}
                echo 'Testing..'
            }
        }
    }
	post {
			always {
				archiveArtifacts artifacts: '**/*.war', fingerprint:true
			}
		}
}