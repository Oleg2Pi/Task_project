# Задание 1. Базовая реализация REST API

1. Commit - Add dependencies and plugins in pom:
   1. dependencies (artifactId):
      1. spring-boot-starter-parent (version 3.4.0)
      2. spring-boot-starter-web
      3. lombok
   2. plugins (artifactId):
      1. spring-boot-maven-plugin
      2. lombok
2. Commit - Create directory "entity" and class "Product": 
   1. add annotation in class Product from lombok:
      1. @Data
      2. @NoArgsConstructor
      3. @AllArgsConstructor
   2. add fields in class Product:
      1. name
      2. description
      3. price
      4. inStock
3. 