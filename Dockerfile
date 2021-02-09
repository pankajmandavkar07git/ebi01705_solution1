FROM openjdk:11

MAINTAINER Pankaj M Mandavkar "pankajmandavkar07@gmail.com"

EXPOSE 8000

ARG JAR_FILE

COPY ${JAR_FILE} /usr/local/webapps/solution1.jar

WORKDIR /usr/local/webapps/

CMD  ["java", "-DSpring.profiles.active=default", "-jar", "solution1.jar"]