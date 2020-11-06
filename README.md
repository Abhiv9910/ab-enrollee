# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

# Enrollee Tracking System #
Using Spring Boot and MySQL a microservice was developed for tracking the status of enrollees in a health care program.
  Each Enrollee have an id, name, and activation status (true or false), and a birth date
  Enrollee optionally can add a phone number.
  Enrollee can have zero or more dependents.
  Each of an enrollee's dependents possess an id, name, and birth date.

The application provides following features:
  1. Add a new enrollee
  2. Modify an existing enrollee
  3. Remove an enrollee entirely
  4. Add dependents to an enrollee
  5. Remove dependents from an enrollee
  6. Modify existing dependents

  Swagger Url: http://localhost:8080/swagger-ui.html

  Technologies used: Spring Boot, MySQL, docker-compose, JUnit .
