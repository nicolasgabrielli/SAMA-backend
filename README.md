# SAMA
Sistema de Administración de Memoria Anual.

# Dependencias Utilizadas
En esta sección se pueden ver las distintas dependencias utilizadas para el desarrollo del proyecto.
## Spring Boot
- Spring Data MongoDB
- Lombok
- Spring Web
- Docker Compose Support 

## Nota
Para ejecutar el backend se debe tener en ejecución un contenedor de Docker que contenga una imagen de MongoDB. 
Para ello, instalar Docker y luego ejecutar en la consola:

docker pull mongo

docker run --name samaDB -d -p 27017:27017 mongo

Una vez realizado esto ya se puede ejecutar el backend y debería de contectarse a este contenedor sin problemas.


Para poder revisar la mantenibilidad del código a través de Sonarqube se deben ejecutar los siguientes comandos

docker pull jericor/sama_sonar

docker run --name sonar -p 9000:9000 -d jericor/sama_sonar

docker start sonar

Lo anterior iniciará el contenedor de SonarQube que podrá ser visto en http://localhost:9000.
Las credenciales son las siguientes.
Login: admin
Password: sama

Por último para realizar la revision de los test unitarios se debe utilizar el siguiente comando

./gradlew test jacocoTestReport sonar   -Dsonar.projectKey=SAMA   -Dsonar.projectName='SAMA'   -Dsonar.host.url=http://localhost:9000   -Dsonar.token=sqp_33d4bb47b775b8501d247f122b83f48a6fdd4737

Despues de unos segundos se mostrará el resultado del análisis en la pagina de SonarQube.
