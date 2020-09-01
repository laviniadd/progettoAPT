# Progetto di Advanced Programming Techniques
[![Build Status](https://travis-ci.org/laviniadd/progettoAPT.svg?branch=master)](https://travis-ci.org/laviniadd/progettoAPT)
[![Coverage Status](https://coveralls.io/repos/github/laviniadd/progettoAPT/badge.svg?branch=master)](https://coveralls.io/github/laviniadd/progettoAPT?branch=master)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.myproject.app%3Aprogetto&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.myproject.app%3Aprogetto)

## Purpose:
The purpose of my work has been to create an application that allows the user to
- Create a shopping list and assign it the name
- Delete shopping list
- Add products
- Remove products
- Modify products

The project has been developed through the Test-Driven Development approach.

## Prerequisites:
* [Docker](https://docs.docker.com/engine/docker-overview/)
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.8.1](https://maven.apache.org/plugins/maven-compiler-plugin/download.cgi)

## Running the application:
* `git clone https://github.com/laviniadd/progettoAPT`
* `cd progettoAPT/progetto`
* Run: `mvn package`
* Start Docker and create the database using the following line. Run:
  
  `docker run --name mydbproject-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=mydbproject -p 3306:3306 -d mysql:8`

* When the container is created and started, run this command line to start the application: `java -jar target\progetto-0.0.1-SNAPSHOT-jar-with-dependencies.jar --mysql-host=jdbc:mysql://localhost:3306/mydbproject --db-password=root --db-username=root`

## Running tests:
* Start Docker
* (If not already done) `git clone https://github.com/laviniadd/progettoAPT`
* `cd progettoAPT/progetto`
* Running `mvn verify -Pdocker` will run all tests.
* Running `mvn test` will only run unit tests.
* Running `mvn test org.pitest:pitest-maven:mutationCoverage` will run mutation test. The web report from PIT can be found in the directory target/pit-reports.
