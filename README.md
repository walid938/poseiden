# spring-boot
## Technical:

1. Framework: Spring Boot v2.6.3
2. Java 17
3. Thymeleaf
4. Bootstrap v.4.3.1


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers
4. Create view files and place in src/main/resource/templates

## Write Unit Test
1. Create unit test and place in package com.nnk.springboot in folder test > java

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config
3. Add Authorization: redirect based on Role

To start the project, add in `application.properties`:
```properties
logging.level.com.nnk.springboot=Debug
logging.level.web=debug
logging.level.org.hibernate=error
logging.file=log/poseidon-app.log

spring.security.oauth2.client.registration.github.client-id={github_client_ID}
spring.security.oauth2.client.registration.github.client-secret={github_Secret}

logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS}  === [%thread] === %-5level === %logger{50} ===  %msg%n

################### DataSource Configuration ##########################
spring.jpa.database=mysql
spring.jpa.database-platform=mysql
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/pojo
spring.datasource.username=root
spring.datasource.password=root

################### Hibernate Configuration ##########################

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

## Test Coverage
![Capture d'écran 2024-03-06 210248](https://github.com/walid938/poseiden/assets/60928838/d43c5e89-b416-450f-8bba-27b1b52096fe)

