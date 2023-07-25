FROM openjdk:17-alpine

WORKDIR /usr/gradle/

COPY gradle gradle
COPY settings.gradle build.gradle gradlew ./

COPY src src

RUN ./gradlew build
RUN mkdir /usr/app/ && cp ./build/libs/*.jar /usr/app/app.jar

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "app.jar"]