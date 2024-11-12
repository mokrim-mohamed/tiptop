pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('id_token_prv')
        CLOUDSDK_CORE_PROJECT = 'g2-archi-o23'
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code source depuis le repository
                git url: 'https://github.com/mokrim-mohamed/tiptop', branch: 'main'
            }
        }

    

        stage('Check Docker') {
            steps {
                script {
                    // Vérifier que Docker est accessible et obtenir la version
                    sh 'docker --version'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerTag = "${env.BUILD_ID}"

                    sh 'mvn test'
                    sh 'mvn clean package'
                    sh "docker build -t mokrim/prod:${dockerTag} ."
                    echo "Image a été créée avec le tag: ${dockerTag}"
                    
                    env.DOCKER_TAG = dockerTag
                }
            }
        }

        stage('Login') {
            steps {
                script {
                    // Se connecter à Docker Hub
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    sh 'echo Login réussi'
                }
            }
        }

        stage('Push') {
            steps {
                // Pousser l'image Docker sur Docker Hub avec le tag dynamique
                sh "docker push mokrim/prod:${env.DOCKER_TAG}"
            }
        }

        stage('Deploy to GCP') {
            steps {
                script {
                    // Authentifier avec Google Cloud Platform et déployer l'image avec le tag dynamique
                    withCredentials([file(credentialsId: 'gcloud-creds', variable: 'GCP_KEY_FILE')]) {
                        sh """
                            gcloud auth activate-service-account --key-file="$GCP_KEY_FILE"
                            gcloud config set project "$CLOUDSDK_CORE_PROJECT"
                            gcloud compute instances list
                            gcloud compute ssh --zone="europe-west9-c" "env-prod" -- "
                            docker stop my_container || true
                            docker rm my_container || true
                            docker pull mokrim/pord:${env.DOCKER_TAG} && docker run -d -p 8080:8080 \
                                -e SPRING_DATASOURCE_URL=jdbc:mysql://34.1.13.155/test \
                                -e SPRING_DATASOURCE_USERNAME=admin \
                                -e SPRING_DATASOURCE_PASSWORD=Admin123! \
                                --name my_container mokrim/prod:${env.DOCKER_TAG}"
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Le pipeline s\'est terminé avec succès.'
            sh 'docker logout'
        }

        failure {
            echo 'Le pipeline a échoué.'
            sh 'docker logout'
        }
    }
}
