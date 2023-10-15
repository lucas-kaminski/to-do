# TO-DO: An REST API using Java

Everyone has a TO-DO project in their portfolio. This is mine. :sunglasses:

A complete TO-DO project using Java 17 with Spring (Boot, MVC and Data JPA) to create a REST Server, Maven to manage the dependencies, Lombok and BCrypt to help with the development, H2 Database to persist data and a Dockerfile to manage the deployment in a free [Render](https://render.com/) server.

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Installation](#installation)
  - [Requirements](#requirements)
  - [Clone](#clone)
  - [Development](#development)
  - [Build](#build)
  - [Run](#run)
- [Usage](#usage)
  - [Endpoints](#endpoints)
  - [Localhost](#localhost)
  - [Production](#production)
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
git clone https://github.com/lucas-kaminski/todo.git`
```

### Development

If you want to run the application locally, you can use the following command:

Create a local server with Maven

```shell
mvn spring-boot:run
```

### Build

But if you want to build e deploy the application in a local Docker container, you can use the following commands:

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

| Authenticated      | Method | Endpoint    | Description                                 |
| ------------------ | ------ | ----------- | ------------------------------------------- |
| :x:                | POST   | /user       | Create a new user                           |
| :heavy_check_mark: | POST   | /tasks      | Create a new task to the authenticated user |
| :heavy_check_mark: | GET    | /tasks      | Get all tasks from the authenticated user   |
| :heavy_check_mark: | PUT    | /tasks/{id} | Update a task                               |

### Localhost

If you are running the application locally, you can use your local IP address to send requests to the API. If your local IP address is `http://localhost`, you can define the port and the endpoint to send requests to the API. For example, to create a new user, you can send a POST request to `http://localhost:8080/user` with the expected payload.

### Production

If you want to test the API in a production enviroment, you can use the following URL: `https://todo-qazz.onrender.com/`. Following the example above, you can send the same POST request to `https://todo-qazz.onrender.com/user`. Remember, it is a free server, so it can take a few seconds to wake up and respond to your request.

### API Client

You can use the following [Apidog Project](https://3a7v3bxt1w.apidog.io) to call the API. It is a free API Client that I've been using to help me with the development/testing of this project.

## Tests

The implementation of the tests is still in progress. When it is done, this section will be updated.

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Contact

Lucas Kaminski - [
![Linkedin](https://i.stack.imgur.com/gVE0j.png) LinkedIn](https://www.linkedin.com/in/lucas-kaminski/) - [![GitHub](https://i.stack.imgur.com/tskMh.png) GitHub](https://github.com/lucas-kaminski/)
