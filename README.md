
# Spring Blog Application

A Simple Blog Application made using Spring Boot. Users can create/edit/delete articles. Authentication done via JWT Tokens. Implemented basic Role based access control where in moderators can delete other users articles. Admins can create mods. Only admin and authors can edit articles.


## Installation

Clone the project and load the maven repositories. Advised to run on IntelliJ for easier setup.


## API Reference
Get the API Reference from [here](https://www.postman.com/soumil4561/blog-app-spring/collection/u9hqbho/spring-blog-application).
## Environment Variables

A .example file is provided in /src/resources/ for setting up the Environment Variables.
`ADMIN.USERNAME` and `ADMIN.PASSWORD` along with your `spring.datasource.url` is important to setup in your application.properties file.

