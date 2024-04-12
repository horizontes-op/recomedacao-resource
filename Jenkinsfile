pipeline {
    agent any
    stages {
        stage('recomendacao interface') {
            steps {
                build job: 'recomendacao', wait: true
            }
        }

        stage("aluno interface"){
            steps{
                build job: 'aluno', wait: true
            }
        }

        stage("instituicao interface"){
            steps{
                build job: 'instituicao', wait: true
            }
        }

        stage('build recomendacao') { 
            steps {
                sh 'mvn clean package'
            }
        }      
        stage('build image recomendacao') {
            steps {
                script {
                    account = docker.build("fernandowi55/recomendacao:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }
        stage('push image recomendacao') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credential') {
                        account.push("latest")
                        account.push("${env.BUILD_ID}")
                       
                    }
                }
            }
        }

        
    }
}