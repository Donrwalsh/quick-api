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
							def stage_jdbc = sh returnStdout: true, script: 'curl -I -s http://192.168.33.10:8080/JDBC/sanity | grep "HTTP/1.1"'
							def stage_jdbc_t = sh returnStdout: true, script: 'curl -I -s http://192.168.33.10:8080/JDBC/sanity | grep "HTTP/1.1"'
							
							return stage_jdbc.contains("HTTP/1.1 200 OK") && stage_jdbc_t.contains("HTTP/1.1 200 OK")
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
				node ('prod') {
					dir('var/lib/tomcat8/webapps/') {
					unstash "JDBC"
					unstash "JDBC_T"
				}
				sh 'sleep 20'
				script {
					timeout(5) {
						waitUntil {
							def prod_jdbc = sh returnStdout: true, script: 'curl -I -s http://www.quick-api.com:8080/JDBC/sanity | grep "HTTP/1.1"'
							def prod_jdbc_t = sh returnStdout: true, script: 'curl -I -s http://www.quick-api.com:8080/JDBC/sanity | grep "HTTP/1.1"'
							
							return prod_jdbc.contains("HTTP/1.1 200 OK") && prod_jdbc_t.contains("HTTP/1.1 200 OK")
						}
					}
				}
            }
        }
    }
	post {
			always {
				archiveArtifacts artifacts: '**/*.war', fingerprint:true
			}
		}
}