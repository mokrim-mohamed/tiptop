# Utiliser une image avec OpenJDK 17
FROM openjdk:17-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR de l'application dans le conteneur
COPY target/tiptopG2-0.0.1-SNAPSHOT.jar /app/app.jar

# Définir les variables d'environnement pour la connexion à la base de données
#ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/test
#ENV SPRING_DATASOURCE_USERNAME=root
#ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/test
ENV SPRING_DATASOURCE_USERNAME=root 
ENV SPRING_DATASOURCE_PASSWORD=
# Exposer le port 8080
EXPOSE 8080

# Commande pour exécuter le JAR
CMD ["java", "-jar", "app.jar"]
