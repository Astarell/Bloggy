# Bloggy application

This is a simple pet-project that demonstrates basic skills in working with several major Java developer frameworks. 

It is a website where users can publish their own articles of small content, share any interesting information and find one that will be useful to them.

Although the project does not stand out for the special beauty of the user interface, it took a lot of time to make it at least look good, which Bootstrap helped a lot.

Technologies:
- Spring Framework: Spring Boot, Spring Core, Spring Data JPA, Spring Security
- Maven
- Hibernate ORM
- PostgreSQL
- Web template engine ‘Thymeleaf’
- Bootstrap


## How to run locally
Create the 'application.properties' file and set the following configuration.

Common settings:
- server.port

For the OAuth2 should set (there are still shortcomings, fixes are required): 
- GitHub client-id
- GitHub client-secret
- GitHub redirect-uri
- Change the part of the custom reference in templates/auth-related/user-login.html and the 'authorizationEndpoint' in SecurityConfig

For the Spring Data JPA set :
- spring.datasource.driver-class-name
- spring.datasource.url
- spring.datasource.username
- spring.datasource.password
- spring.jpa.hibernate.ddl-auto=update (or any other needed option)

## Short overview

### The basics of the website

![BloggyMain](https://github.com/Astarell/Bloggy/assets/111741095/c8ead753-c43f-47e3-9ac7-400b60f6c500)

![BloggyMain](https://github.com/Astarell/Bloggy/assets/111741095/c0bd3f99-938d-4a89-8285-f4ccde6f9e3c)

![BloggyMain](https://github.com/Astarell/Bloggy/assets/111741095/c910b8c6-9c41-4f8a-b143-8b1cb829bc31)

### The database relationships schema
![The relationships schema](https://github.com/Astarell/Bloggy/assets/111741095/c2a29e86-a55c-4660-b056-0583040a1562)




