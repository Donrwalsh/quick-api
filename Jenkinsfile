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
        stage('Test') {
            steps {
				echo 'Staging...'
				node ('stage') {
				dir('/var/lib/tomcat8/webapps/') {
					unstash "JDBC"
					unstash "JDBC_T"
					}
				}
				sh 'sleep 20'
				script {
					timeout(5) {
						waitUntil {
							def jdbc = sh returnStdout: true, script: 'curl -I -s http://192.168.33.10:8080/JDBC/sanity | grep "HTTP/1.1"'
							def jdbc_t = sh returnStdout: true, script: 'curl -I -s http://192.168.33.10:8080/JDBC/sanity | grep "HTTP/1.1"'
							
							return jdbc.contains("HTTP/1.1 200 OK") && jdbc_t.contains("HTTP/1.1 200 OK")
						}
					}
				}
				echo 'Testing....'
				dir("api/test") {
					sh 'mvn clean test -DargLine=\\"-Dkarate.env=stg\\"'
				}				
			}
		}
        stage('Deploy') {
            steps {
                echo 'Deploying.....'
            }
        }
    }
	post {
			always {
				archiveArtifacts artifacts: '**/*.war', fingerprint:true
			}
		}
}