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
            }
        }
		post {
			always {
				archiveArtifacts artifacts: 'build/libs/**/*.war', fingerprint:true
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
}