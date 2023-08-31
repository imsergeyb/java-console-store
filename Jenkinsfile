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
            sh 'mvn install'
        }
        
        }
        stage ('Test'){
            steps{
            sh 'mvn test'
        }
    }

    }
}
