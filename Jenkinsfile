pipeline {
    agent any

    tools {
        maven "MAVEN"
    }

    environment {
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
                    sh "docker build -f ./docker/backend-application/Dockerfile -t ${DOCKER_IMAGE_NAME}:latest ."

                    //docker.build("${DOCKER_IMAGE_NAME}:latest", './docker/backend-application/Dockerfile')
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {

                    sh "docker login -u mamotec -p MaMoTec00001!"

                    sh "docker push ${DOCKER_IMAGE_NAME}:latest"

                }
            }
        }

       stage('Docker: Delete Image') {
              steps {
                sh "docker image rm ${DOCKER_IMAGE_NAME}:latest"
              }
       }
    }
}
