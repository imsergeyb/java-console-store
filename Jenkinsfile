pipeline{
    agent any
    stages{
        stage('Fetch source code'){
            steps{
            git branch: 'main', url: 'https://github.com/imsergeyb/java-console-store.git'
        }
        }
        stage('Build'){
            steps {
            sh 'mvn clean install -DskipTests'
        }
        post {
              success{
               echo "Archiving..."
               archiveArtifacts artifacts:'**/*.war'
              }
        }
        }
        stage ('Test'){
            steps{
            sh 'mvn test'
        }
    }

        stage ('Checkstyle analysis'){
        steps{
        sh 'mvn checkstyle:checkstyle'
        }}

        stage('Code Analysis'){
        environment {
                scannerHome = tool 'sonar5.0'
                }
                steps {
                withSonarQubeEnv('sonar'){
                    sh '''${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectKey=java-console-store \
                    -Dsonar.projectName=java-console-store \
                    -Dsonar.projectVersion=1.0 \
                    -Dsonar.source=src/ \
                    -Dsonar.java.binaries=target/test-classes/facade \
                    -Dsonar.junit.reportsPath=target/surefire-reports/ \
                    -Dsonar.jacoco.reportsPath=target/jacoco.exec \
                    -Dsonar.java.checkstyle.reportPath=checkstyle-result.xml

                    '''
                } 
            }
        }
    }
}
