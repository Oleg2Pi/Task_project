# Задание 3. Использование Docker

1. Create DockerFile and docker-compose.yml:
   1. in DockerFile:
      1. use maven 3.8, jdk-17
      2. create workdir /app and copy dir src and file pom.xml
      3. use command for clean and package project
      4. use jdk-17
      5. create workdir /app and copy file .jar from build and name this file app.jar
      6. set commands for start project
   2. in docker-compose:
      1. create containers: database and task_project