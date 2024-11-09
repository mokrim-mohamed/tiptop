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
<<<<<<< HEAD
                git url: 'https://github.com/mokrim-mohamed/tiptop', branch: 'main'
=======
                git url: 'https://github.com/mokrim-mohamed/tiptop', branch: 'mokrim'
>>>>>>> mokrim
            }
        }

        stage('Echo Message') {
            steps {
                // Exemple de commande pour afficher un message
                sh 'echo "Le code a été récupéré avec succès et le pipeline est en cours d\'exécution."'
            }
        }

        stage('Check Docker') {
            steps {
                script {
                    // Vérifier que Docker est accessible et obtenir la version
                    sh 'docker --version'
<<<<<<< HEAD

                    // Optionnel : Exécuter un conteneur Docker basique pour vérifier que Docker fonctionne correctement
                  

=======
>>>>>>> mokrim
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Générer un tag basé sur le timestamp
                    def dockerTag = "nano-${env.BUILD_ID}"

                    // Construire l'image Docker avec le tag dynamique
                    sh 'mvn clean package'
<<<<<<< HEAD
                   
                    sh 'docker build -t mokrim/test:nano .'
                    echo 'Image a été créée.'
=======
                    sh "docker build -t mokrim/test:${dockerTag} ."
                    echo "Image a été créée avec le tag: ${dockerTag}"
                    
                    // Stocker le tag dans une variable d'environnement pour le réutiliser
                    env.DOCKER_TAG = dockerTag
>>>>>>> mokrim
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
<<<<<<< HEAD
                // Pousser l'image Docker sur Docker Hub
                sh 'docker push mokrim/test:nano'
=======
                // Pousser l'image Docker sur Docker Hub avec le tag dynamique
                sh "docker push mokrim/test:${env.DOCKER_TAG}"
>>>>>>> mokrim
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
<<<<<<< HEAD
                             docker stop my_container || true
                                docker rm my_container || true
                            gcloud compute ssh --zone="europe-west9-c" "env-test" -- "
                            docker stop my_container || true 
                            docker rm my_container || true 
                            docker pull mokrim/test:nano && docker run -d -p 8080:8080 \
                -e SPRING_DATASOURCE_URL=jdbc:mysql://34.163.160.174/test \
                -e SPRING_DATASOURCE_USERNAME=mokrim \
                -e SPRING_DATASOURCE_PASSWORD=Mokrim123! \
                --name my_container mokrim/test:nano"

                        '''
=======
                            gcloud compute ssh --zone="europe-west9-c" "env-test" -- "
                            docker stop my_container || true
                            docker rm my_container || true
                            docker pull mokrim/test:${env.DOCKER_TAG} && docker run -d -p 8080:8080 \
                                -e SPRING_DATASOURCE_URL=jdbc:mysql://34.163.10.235/test \
                                -e SPRING_DATASOURCE_USERNAME=mokrim \
                                -e SPRING_DATASOURCE_PASSWORD=Mokrim123! \
                                --name my_container mokrim/test:${env.DOCKER_TAG}"
                        """
>>>>>>> mokrim
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
