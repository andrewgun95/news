### Environment
PostgresSQL, Java 17 or Higher, Docker

## How to Run Apps
### Prerequisites
1. Docker


### Step 1 : Build application using Docker Compose
Make sure **docker already installed** and check using this command.
```shell
$ docker version
```
Navigate to project directory / workspace and have the docker-compose.yml in the following directory
```shell
$ cd "/news/"
```
Run docker compose to start and build the application
```shell
$ docker-compose up --build
```

### Step 2 : Check status of the application
```shell
$ docker-compose ps
```

### Step 3 : Access using Browser in this URL
http://localhost:9000/news/swagger-ui.html
