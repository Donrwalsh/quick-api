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
					bat 'mvn clean package -Dmaven.test.skip=true'
				}
				dir("api/JDBC_T") {
					bat 'mvn clean package -Dmaven.test.skip=true'
				}
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
	post {
			always {
				archiveArtifacts artifacts: '**/*.war', fingerprint:true
			}
		}
}