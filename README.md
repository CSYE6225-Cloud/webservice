# webservice

## Prerequisites for building and deploying
+ `java 17`
+ `Maven 3.8.4`

## Configuration
Do under application root directory
1. `cp ./src/main/resources/application.properties.example ./src/main/resources/application.properties`
2. config debug level if needed
3. configure your MySQL username and password (port and address)
4. configure your database name in your database
5. Create tables:
    + Create manually according to `MySQLDatabase.sql`
    + Run `MySQLDatabase.sql` in command line
      1. `mysql -u root -p`
      2. `CREATE DATABASE CSYE6225;` or use other names
      3. `exit`
      4. `mysql -u root -p CSYE6225 < MySQLDatabase.sql`
    + Run the `MySQLDatabase.sql` by other graphic software like `Navicat`

## Build and Deploy instructions
+ Compiling `mvn package`
+ Test `mvn test`
+ Package `mvn package`
+ Run `mvn spring-boot:run`
