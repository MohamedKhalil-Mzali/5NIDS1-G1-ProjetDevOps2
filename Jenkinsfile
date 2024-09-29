pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                git branch: 'MohamedKhalilMzali-5NIDS1-G1', url: 'https://github.com/MohamedKhalil-Mzali/5NIDS1-G1-ProjetDevOps2.git'
            }
        }
        stage('Maven') {
            steps {
                sh "mvn clean"
            }
        }
        
    }
}
