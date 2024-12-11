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

    

        stage('Build') {
            steps {
                script {
                    sh  'mvn clean package -DskipTests'
                }
            }
        }
        stage('Tests & Publish Report') {
            steps {
                script {
                    sh 'mvn clean verify sonar:sonar -P unit-tests'
                }
            }
        }
        stage('Build Image') {
            steps {
                script {
                    def dockerTag = "${env.BUILD_ID}"

                    
                   
                    sh "docker build -t mokrim/prod:${dockerTag} ."
                    echo "Image a été créée avec le tag: ${dockerTag}"
                    
                    env.DOCKER_TAG = dockerTag
                }
            }
        }



        stage('Push Image') {
            steps {
                // Se connecter à Docker Hub
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh 'echo Login réussi'
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
                            docker pull mokrim/prod:${env.DOCKER_TAG} && docker run -d -p 8080:8080 \
                                -e SPRING_DATASOURCE_URL=jdbc:mysql://34.155.47.166/prod \
                                -e SPRING_DATASOURCE_USERNAME=admin \
                                -e SPRING_DATASOURCE_PASSWORD=Admin123! \
                                --name my_container mokrim/prod:${env.DOCKER_TAG}"
                        """
                    }
                }
            }
        }
        stage('fonctionnel tests') {
            steps {
                script {
                    sleep(time: 40, unit: 'SECONDS')
                    sh 'mvn test -P fonctionnel-tests'
                }
            }
        }
    }

    post {
        success {
            echo 'Le pipeline s\'est terminé avec succès.'
            sh 'docker logout'
             emailext(
                        subject: "Test de Jenkins - Notification",
                        body: "Le pipeline Jenkins a été exécuté avec succès.",
                        to: "mokrimmohamed2016@gmail.com"
                    )
        }

        failure {
            echo 'Le pipeline a échoué.'
            sh 'docker logout'
             emailext(
                        subject: "Test de Jenkins - Notification",
                        body: "Le pipeline Jenkins a échoué. Voici l'érreur:\n\n${errorMessage}",
                        to: "mokrimmohamed2016@gmail.com"
                    )
        }
    }
}
