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
                rocketSend channel: '#test-projekt', message: ':face_with_symbols_over_mouth: :face_with_symbols_over_mouth: :face_with_symbols_over_mouth: Build of test project was failed :: '
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
        always {
            rocketSend channel: '#test-projekt', message: $PROJECT_NAME - Build $BUILD_NUMBER - $BUILD_STATUS \n ${SCRIPT, template="groovy-html.template"}
        }
        success {            
            rocketSend channel: '#test-projekt', message: ':partying_face: :partying_face: :partying_face: Build of test project was success :: '
        }
    }
}
