# TO-DO: A REST API using Java

A complete TO-DO project using Java 17 with Spring (Boot, MVC and Data JPA) to create a REST Server, Maven to manage the dependencies, Lombok and BCrypt to help with the development, H2 Database to persist data and a Dockerfile to manage the deployment in a free [Render](https://render.com/) server.

## Table of Contents

- [Installation](#installation)
  - [Requirements](#requirements)
  - [Clone](#clone)
  - [Development](#development)
  - [Build and Run](#build-and-run)
- [Usage](#usage)
  - [Endpoints](#endpoints)
    - [User](#user)
    - [Task](#task)
  - [Authentication](#authentication)
  - [API Client](#api-client)
    - [Localhost](#localhost)
    - [Production](#production)
- [Explanations](#explanations)
  - [Files](#files)
  - [Folders](#folders)
- [Tests](#tests)
- [License](#license)
- [Contact](#contact)

## Installation

### Requirements

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven](https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.zip)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Docker](https://www.docker.com/get-started)

### Clone

Clone this repo to your local machine using:

```shell
git clone https://github.com/lucas-kaminski/todo.git
```

### Development

If you want to run the application locally, you can use the following command:

Create a local server with Maven

```shell
mvn spring-boot:run
```

### Build and Run

But if you want to build and deploy the application in a local Docker container, you can use the following commands:

Create the JAR file with Maven

```shell
mvn clean package
```

Create the Docker image exposing the port 8080 and naming it as `todo`

```shell
docker build -t todo .
docker run -p 8080:8080 todo
```

## Usage

To use and test the API, you can configure your favorite REST Client to send requests to the endpoints described below.

### Endpoints

#### User

| Method | Endpoint | Description                  | Authenticated      |
| ------ | -------- | ---------------------------- | ------------------ |
| POST   | /user    | Create a new user            | :x:                |
| GET    | /user    | Get user data                | :heavy_check_mark: |
| PUT    | /user    | Update specific data of user | :heavy_check_mark: |
| DELETE | /user    | Delete user account          | :heavy_check_mark: |

#### Task

| Method | Endpoint   | Description                    | Authenticated      |
| ------ | ---------- | ------------------------------ | ------------------ |
| POST   | /task      | Create a new task to user      | :heavy_check_mark: |
| GET    | /task      | Get all tasks from user        | :heavy_check_mark: |
| PUT    | /task/{id} | Update specific data of a task | :heavy_check_mark: |
| DELETE | /task/{id} | Delete a specific task         | :heavy_check_mark: |

### Authentication

The method used to authenticate the user is the Basic Authentication with Base64 encoding. To authenticate, you need to send the username and password in the header of the request. For example, if you want to create a new user, you can send a POST request to `/user/` to create a new user with the following payload:

```json
{
  "name": "Test",
  "username": "test",
  "password": "test"
}
```

And, to authenticate, you need to send the username and password in the header of the request, encoded in Base64. In this case, the username and password are `test` and `test`, respectively. So, the encoded string is `dGVzdDp0ZXN0`. You can use the following command to encode the string:

```shell
echo -n "test:test" | base64
```

And, finally, you can send requests to protected endpoints by adding the following header to the request:

```json
{
  "Authorization": "Basic dGVzdDp0ZXN0"
}
```

### API Client

You can use the following [Apidog Project](https://3a7v3bxt1w.apidog.io) to call the API. It is a free API Client that I've been using to help me with the development/testing of this project.

#### Localhost

If you are running the application locally, you can use your local IP address to send requests to the API. For example, if your local IP address is `http://localhost`, you can send the requests to `http://localhost:8080/`. Following the example above, you can send a POST request to `http://localhost:8080/user` to create a new user.

#### Production

If you want to test the API in a production environment, you don't need to install anything. You can use the following URL to send requests to the API: `https://todo-qazz.onrender.com/`. Following the example above, you can send a POST request to `https://todo-qazz.onrender.com/user` to create your user, and then you can send a POST request to `https://todo-qazz.onrender.com/task` to create your first task.

## Personal explanations

Here's my annotation of the repository structure. Those explanations are based on my personal understanding of the project, so it may not be 100% accurate or complete, but it should give you a general idea of the purpose of each file and folder.

<details>
  <summary>Files</summary>

| File       | Description                                                                                 |
| ---------- | ------------------------------------------------------------------------------------------- |
| .gitignore | Git configuration file used to ignore files and folders that should not be versioned.       |
| Dockerfile | Docker configuration file used during application deployment to create the container image. |
| LICENSE    | Project license file, in this case, the MIT license generated by GitHub.                    |
| mvnw       | Maven configuration file for Linux, used to run Maven without the need for installation.    |
| mvnw.cmd   | Maven configuration file for Windows, used to run Maven without the need for installation.  |
| pom.xml    | Maven configuration file containing information, dependencies, and plugins for the project. |
| README.md  | Project documentation file, in this case, the current file.                                 |

</details>

<details>
  <summary>Folders</summary>

### .mvn/wrapper

| File                     | Description                        |
| ------------------------ | ---------------------------------- |
| maven-wrapper.jar        | Maven executable for Windows       |
| maven-wrapper.properties | Maven configuration file for Linux |

### .vscode

| File            | Description                          |
| --------------- | ------------------------------------ |
| extensions.json | Suggestions of extensions for VSCode |

### src/main/java/me/lucaskaminski/todolist

| File                     | Description                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| errors                   | Package with error classes, uses @ExceptionHandler to register handler classes in Spring and are called when you instantiate an error object                                                                                                                                                                                                                                                                                                |
| filter                   | Package with filter classes, uses @Component to register classes in Spring, @Autowired to link JpaRepository interfaces and @Override of the doFilterInternal method to intercept requests, using the path string to validate its use                                                                                                                                                                                                       |
| task & user              | Package with model classes, always has a Repository, a Controller, and a Model. The Repository is the interface that extends JpaRepository, the Controller is the class that receives the requests, registered by @RestController, @Autowired for the repos and mappings for the routes, and the Model is the class that represents the object in the database, uses JBA to map the database and Lombak to generate the getters and setters |
| utils                    | Package with classes that bring useful methods to the project                                                                                                                                                                                                                                                                                                                                                                               |
| TodolistApplication.java | Main class of the project.                                                                                                                                                                                                                                                                                                                                                                                                                  |

### src/main/resources

| File                   | Description                    |
| ---------------------- | ------------------------------ |
| static                 | Folder with static files       |
| templates              | Folder with HTML templates     |
| application.properties | Spring Boot configuration file |

### src/test/java/me/lucaskaminski/todolist

...

</details>

## Tests

The implementation of the tests is still in progress. When it is done, this section will be updated.

## License

Distributed under the MIT License. See [LICENSE](https://github.com/lucas-kaminski/todo/blob/main/LICENSE) for more information.

## Contact

~ Lucas Kaminski | [LinkedIn](https://www.linkedin.com/in/lucas-kaminski/) | [GitHub](https://github.com/lucas-kaminski)
