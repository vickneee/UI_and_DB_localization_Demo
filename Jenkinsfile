pipeline {

    agent any

    environment {
            PATH = "/usr/local/bin:${env.PATH}"

            SONARQUBE_SERVER = 'SonarQubeServer' // SonarQube server name in Jenkins config
            SONAR_TOKEN = 'SONAR_TOKEN' // SONAR_TOKEN=ID in Jenkins credentials, using Secret text as Secret=your_sonar_token
            // Define Docker Hub credentials ID
            DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
            // Define Docker Hub repository name
            DOCKERHUB_REPO = 'vickneee/ui_and_db_localization_mysql'
            // Define Docker image tag
            DOCKER_IMAGE_TAG = 'latest'
        }

    tools {
        maven 'Maven3'
    }

    stages {

        stage('Checking') {
            steps{
                git branch:'main', url:'https://github.com/vickneee/UI_and_DB_localization_Demo.git'
            }
        }

        stage ('Build') {
            steps {
                sh  'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Code Coverage') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv("${env.SONARQUBE_SERVER}") {
                        // First line is Mac local sonar-scanner path -> use correct path,
                        // Sixth line use -Dsonar.login instead token (newer version). Test what works
                        // Local sonar scanner needs to be running in background
                        // Update Jenkinsfile, to test SonarQube without properties file, working well
                        sh """
                            /usr/local/sonarscanner/bin/sonar-scanner \
                            -Dsonar.projectKey=SonarQube_UI_and_DB \
                            -Dsonar.sources=src \
                            -Dsonar.projectName=SonarQube_UI_and_DB \
                            -Dsonar.host.url=http://localhost:9000 \
                            -Dsonar.login=${env.SONAR_TOKEN} \
                            -Dsonar.java.binaries=target/classes \
                        """
                    }
                }
            }
        }

        stage('Build Docker Image') {
             steps {
                sh 'docker build -t $DOCKERHUB_REPO:$DOCKER_IMAGE_TAG .'
             }
        }

        // Create repo in Docker Hub to push it (Run Dockerfile)
        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        docker login -u $DOCKER_USER -p $DOCKER_PASS
                        docker push $DOCKERHUB_REPO:$DOCKER_IMAGE_TAG
                    '''
                }
            }
        }
    }
}