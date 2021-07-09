def dockerImage
def pomXml

pipeline {
    agent any
    tools {
        jdk 'System  JDK 8'
        maven 'Maven DEFAULT'
    }
    environment {
        registry = "onlytesting/docker-demo"
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
              not {
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
          }
        }
        stage('Read pom.xml for project meta-info') {
            steps {
                script {
                    // https://plugins.jenkins.io/pipeline-utility-steps/
                    pomXml = readMavenPom file: 'pom.xml'
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
            steps {
//                 sh 'docker build -t docker-demo .'
//                 sh 'mvn -T 1C -Pui package -DskipTests=true'
                script {
                    dockerImage = docker.build(registry + ":$BUILD_NUMBER")
                }
            }
        }
        stage('Push to DockerHub') {
            when {
                anyOf {
                    branch "develop"
                    branch "master"
                }
            }
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                        dockerImage.push("latest")
                        dockerImage.push(pomXml.version)
                    }
                }
            }
        }
    }
    post {
        // Can be replaced with email notifications
        success {
            rocketSend channel: '#Docker-Demo-Build', message:  '@all `' + pomXml.name + ' :: ' + pomXml.version + ' # ' +env.BRANCH_NAME + '` -  :leafy_green: `' + currentBuild.result + '`\n'
        }
        failure {
            rocketSend channel: '#Docker-Demo-Build', message:  '@all `' + pomXml.name + ' :: ' + pomXml.version + ' # ' +env.BRANCH_NAME + '` -  :stop_sign: `' + currentBuild.result + '`\n'
        }
    }
}
