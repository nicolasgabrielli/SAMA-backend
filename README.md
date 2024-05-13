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
