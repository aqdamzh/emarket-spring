FROM maven:latest

COPY . /app

EXPOSE 8080

CMD [ "mvn", "-f", "/app/pom.xml", "spring-boot:run"]

# docker build --tag emarket-spring:1.0 .