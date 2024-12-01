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
   1. use annotation for class Product from lombok:
      1. @Data
      2. @NoArgsConstructor
      3. @AllArgsConstructor
   2. create fields in class Product:
      1. name
      2. description
      3. price
      4. inStock
3. Commit - Create constraints for a class "Product":
   1. add dependency in pom.xml:
      1. spring-boot-starter-validation
   2. use annotation from jakarta.validation.constraints
      1. @NotBlank - field is not null
      2. @Size - min and max size for field
      3. @DecimalMin - min value for field
   3. price = 0 by default
   4. inStock = false by default
   5. add annotation @Builder
4. Create directory "repository" and Class "ProductRepository":
   1. create List collection
   2. create crud methods
5. 