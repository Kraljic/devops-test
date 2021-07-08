def dockerImage

pipeline {
    agent any
    tools {
        jdk 'System  JDK 8'
        maven 'Maven DEFAULT'
    }
    environment {
        registry = "onlytesting/DockerDemo"
        registryCredential = 'DockerHub'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Test') {
          when {
              anyOf {
                branch "master"
              }
          }
          steps {
            sh 'mvn test'
          }
          post {
            always {
                junit 'target/surefire-reports/TEST-*.xml'
            }
            failure {
                rocketSend channel: '#test-projekt', message:  '${currentBuild.projectName}#${env.BRANCH_NAME}` -  :stop_sign: `${currentBuild.result}`\n'
            }
          }
        }
        stage('Build for deploy') {
            when {
                anyOf {
                    branch "develop"
                    branch "master"
                }
            }
            docker.build(registry + ":$BUILD_NUMBER")
//             steps {
//                 sh 'mvn -T 1C -Pui package -DskipTests=true'
//             }
            steps {
//                 sh 'docker build -t docker-demo .'
//                 dockerImage = docker.build registry + ":$BUILD_NUMBER"
                sh 'docker images'
            }
        }
        stage('Deploy our image') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
    }
    post {
        success {
            rocketSend channel: '#Docker-Demo-Build', message:  '@all `' + env.POM_VERSION + env.PROJECT_NAME + '#' +env.BRANCH_NAME + '` -  :leafy_green: `' + currentBuild.result + '`\n'
        }
        failure {
            rocketSend channel: '#Docker-Demo-Build', message:  '@all `' + env.POM_VERSION + env.PROJECT_NAME + '#' +env.BRANCH_NAME + '` -  :stop_sign: `' + currentBuild.result + '`\n'
        }
    }
}
