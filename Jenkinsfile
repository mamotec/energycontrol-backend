pipeline {
    agent any

    tools {
        maven "MAVEN"
    }

    environment {
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        DOCKER_IMAGE_NAME = 'mamotec/energycontrol-backend'
    }

    stages {
         stage('Initialize'){
                steps{
                    echo "PATH = ${M2_HOME}/bin:${PATH}"
                    echo "M2_HOME = /opt/maven"
                }
            }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Spring Boot App') {
            steps {
                script {
                    sh "mvn clean install -D skipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}:latest", './docker/backend-application/Dockerfile')
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', "${DOCKER_CREDENTIALS}") {
                        def app = docker.image("${DOCKER_IMAGE_NAME}:latest")
                        app.push()
                    }
                }
            }
        }
    }
}
