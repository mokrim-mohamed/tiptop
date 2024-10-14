pipeline {
    agent any  // Utiliser n'importe quel agent disponible

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

                    // Optionnel : Exécuter un conteneur Docker basique pour vérifier que Docker fonctionne correctement
                  

                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Construire l'image Docker
                    sh 'mvn clean package'
                   
                    sh 'docker build -t mokrim/test:nano .'
                    echo 'Image a été créée.'
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
                // Pousser l'image Docker sur Docker Hub
                sh 'docker push mokrim/test:nano'
            }
        }

        stage('Deploy to GCP') {
            steps {
                script {
                    // Authentifier avec Google Cloud Platform en utilisant le fichier de clé GCP
                    withCredentials([file(credentialsId: 'gcloud-creds', variable: 'GCP_KEY_FILE')]) {
                        sh '''
                            gcloud auth activate-service-account --key-file="$GCP_KEY_FILE"
                            gcloud config set project "$CLOUDSDK_CORE_PROJECT"
                            gcloud compute instances list
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
