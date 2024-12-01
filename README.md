# Задание 2. Подключение базы данных

1. add dependencies in pom.xml, add config in application.properties and create migration for create table:
   1. pom.xml:
      1. add spring-boot-starter-data-jpa
      2. add postgresql
      3. add flyway-database-postgresql
      4. add plugin: flyway-database-postgresql
   2. application.properties - add config for connect with database
   3. create V1__init_DB.sql for create table products
2. 