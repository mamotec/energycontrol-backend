pipeline {
    agent any

    tools {
        jdk 'jdk-17'
    }

    environment {
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        DOCKER_IMAGE_NAME = 'mamotec/energycontrol-backend'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Spring Boot App') {
            steps {
                script {
                    def maven = tool 'M3'
                    sh "${maven}/bin/mvn clean install -D skipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def app = docker.build("${DOCKER_IMAGE_NAME}:latest", "-f ./docker/backend-application/Dockerfile .")
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