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
				node ('stage') {
				dir('/var/lib/tomcat8/webapps/') {
					unstash "JDBC"
					unstash "JDBC_T"
					}
				}
			waitUntil {
				try {
					sh "curl -I -s http://192.168.33.10:8080/JDBC/sanity | grep '200 OK'"
					return true
				} catch (Exception e) {
					return false
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