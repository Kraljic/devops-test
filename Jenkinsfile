pipeline {
    agent any
    tools {
        jdk 'System  JDK 8'
        maven 'Maven DEFAULT'
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
                rocketSend channel: '#test-projekt', message:  '`DockerDemo` -  :stop_sign: `Failure`\n'
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
                sh 'mvn -T 1C -Pui package -DskipTests=true'
            }
        }
    }
    post {
        success {            
            rocketSend channel: '#test-projekt', message:  '`DockerDemo` -  :leafy_green: `Success` \n'
        }
    }
}
