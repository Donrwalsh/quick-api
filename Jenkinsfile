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
					stash includes: 'target/JDBC.war', name: 'JDBC'
				}
				dir("api/JDBC_T") {
					sh 'mvn clean package -Dmaven.test.skip=true'
				}
            }
        }
        stage('Deploy') {
            steps {
				echo 'Deploying....'
				node ('stage') {
				dir('/var/lib/tomcat8/webapps/') {
					unstash "JDBC"
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