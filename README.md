URL Shortener
A modern URL shortener application built with Spring Boot, MySQL, and Redis. The application provides a simple web interface for shortening links and a REST API for programmatic access, complete with a caching layer for high performance.

Table of Contents
Project Overview

Features

Technology Stack

Getting Started

Prerequisites

Local Development

Docker with Docker Compose

API Endpoints

CI/CD Pipeline

Acknowledgements

Project Overview
This project implements a complete URL shortener service. It uses a database for long-term persistence of link data and a Redis cache for fast retrieval of frequently accessed URLs. The application is containerized using Docker, and the development environment is managed with Docker Compose.

The key functionalities are:

A user-friendly web interface for shortening URLs.

A REST API endpoint for creating new short links.

A redirection endpoint that resolves a short hash to the original URL.

Features
URL Shortening: Converts long URLs into short, easy-to-share hashes.

Data Persistence: Stores all link data in a MySQL database.

High-Performance Caching: Uses Redis as a cache-aside layer to speed up lookups for popular links.

Idempotent Shortening: Prevents duplicate entries by returning an existing hash if a URL has already been shortened.

Redirection: Redirects users from the short URL to the original long URL.

Dark/Light Theme: A professional and sleek user interface with a theme toggle.

API Validation: Secures API endpoints with server-side validation to prevent malicious input.

Technology Stack
Backend:

Spring Boot: The core framework for the application.

Spring Data JPA: For seamless interaction with the MySQL database.

Spring Data Redis: For managing the Redis cache.

Maven: The build automation tool.

Database:

MySQL: The primary relational database for data persistence.

Redis: An in-memory data store used for caching.

Containerization:

Docker: For packaging the application and its dependencies.

Docker Compose: For orchestrating the multi-container development environment.

Frontend:

Thymeleaf: For rendering the server-side web pages.

HTML/CSS/JavaScript: For the user interface and client-side logic.

Getting Started
Prerequisites
To run this project locally, you need the following installed on your machine:

Java 17 or higher

Maven

Docker and Docker Compose

Local Development
Clone the repository:

git clone [https://github.com/](https://github.com/)<your-username>/url-shortener.git
cd url-shortener

Build the application:
Run the Maven package command to compile the code and create the executable JAR file.

mvn clean package

Run with Docker Compose:
Start the application and its dependencies (MySQL and Redis) using Docker Compose.

docker-compose up

This command will build the Docker image and start all three services.

Docker with Docker Compose
This project uses Docker Compose to manage its services. The docker-compose.yml file defines the following services:

spring-app: Your Spring Boot application.

mysql-db: The MySQL database container.

redis: The Redis cache container.

The services communicate over a private Docker network. You can access the application from your host machine at http://localhost:8080.

API Endpoints
The API is served at http://localhost:8080/api.

Shorten a URL
URL: /api/shorten

Method: POST

Request Body:

{
    "url": "[https://example.com](https://example.com)"
}

Response:

{
    "hash": "42e611"
}

Redirect to Original URL
URL: /<hash>

Method: GET

Example: http://localhost:8080/42e611

Response: A 301 Moved Permanently redirect to the original URL.

CI/CD Pipeline
This project is configured with a Continuous Integration and Continuous Deployment (CI/CD) pipeline using GitHub Actions.

The workflow is defined in .github/workflows/ci-cd.yml and is triggered on every push to the main branch. The pipeline automatically:

Builds the Spring Boot application using Maven.

Runs all unit tests.

Builds a new Docker image for the application.

Pushes the new image to a private repository on Docker Hub.

This ensures that a production-ready, containerized version of the application is always available for deployment.