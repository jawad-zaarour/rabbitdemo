# Java Spring Boot Demo Project with RabbitMQ

## Overview
This project is a demo application using Java Spring Boot and RabbitMQ to demonstrate how to send and receive messages to cluster of rabbit nodes.

## Endpoints
| Endpoint                                                                | Method | Description                                                               |
|-------------------------------------------------------------------------|--------|---------------------------------------------------------------------------|
| `http://localhost:8585/api/listeners?queueName={queueName}`             | POST   | Add a new queue for RabbitMQ container listener.                          |
| `http://localhost:8585/api/listeners?queueName={queueName}`             | DELETE | Remove a queue from RabbitMQ container listener.                          |
| `http://localhost:8585/api/listeners`                                   | GET    | Get all queues being listened by RabbitMQ container listener.             |
| `http://localhost:8585/api/listeners/stop`                              | POST   | Stop the RabbitMQ listener container.                                     |
| `http://localhost:8585/api/listeners/start`                             | POST   | Start the RabbitMQ listener container.                                    |
| `http://localhost:8585/api/messages?routingKey={RK}`                    | POST   | Generate a single message to specific queue using a specific routing key. |
| `http://localhost:8585/api/messages/{numberOfMessages}?routingKey={RK}` | POST   | Generate random messages to specific queue using a specific routing key.  |


## Prerequisites - Setting Up RabbitMQ Cluster with Docker
For the full implementation and detailed guide on how to create a RabbitMQ cluster with Docker, please refer to the following article:

[Creating RabbitMQ Cluster with Docker - Full Implementation Guide](https://medium.com/@zjawad333/rabbitmq-clusters-with-spring-boot-and-docker-da4623c9ad62)

The linked article provides a step-by-step walkthrough of setting up the RabbitMQ cluster using Docker containers, along with explanations and commands for each step.


## Setup
| Dependency  | Version  | Usage                 |
|-------------|----------|-----------------------|
| Java        | JDK 17   | Programming Language  |
| Spring boot | 3.1.1    | Web Framework         |
| Maven       | 3.9.1    | Dependency Management |
| RabbitMQ    | 3.12.8   | Message Broker        |

## Environment variables
| ENV variables            | properties default                            |
|--------------------------|-----------------------------------------------|
| RABBITMQ_NODES           | localhost:5682,localhost:5683,localhost:5684  |
| RABBITMQ_CONNECTION_NAME | MY_CONNECTION                                 |
| RABBIT_USERNAME          | guest                                         |
| RABBIT_PASSWORD          | guest                                         |
| RABBITMQ_VIRTUAL_HOST    | /                                             |
| RABBITMQ_EXCHANGE_NAME   | EX1                                           |

## How to run 

Build the project jar file then the Docker image:
```
mvn package
```

```
docker build -t spring-app-image .
```

Run the Docker Image
```
docker run -p 8585:8585 --hostname spring-app --name spring-app --ip 192.168.2.80 --network rabbitmq-net -e RABBITMQ_NODES="192.168.2.10:5672,192.168.2.15:5672,192.168.2.30:5672" spring-app-image
```
This command will run the spring docker image on port **8585** and connect it to the cluster of rabbitmq nodes.

## Or Using Docker Compose
#### Before you start
- Install Docker Compose.
- Change environment variable values in `.env` file for more security or leave it as it is.
- Build the project: `mvn package`

Start the RabbitMQ cluster and the Spring Boot application using Docker Compose:
```
docker-compose up
```

To stop the containers
```
docker-compose down
```

## Useful Links
* [Official Spring Boot Documentation for RabbitMQ](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#messaging.amqp)
* [Official RabbitMQ Clustering Documentation](https://www.rabbitmq.com/clustering.html)