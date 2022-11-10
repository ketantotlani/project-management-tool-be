# define base docker image
FROM openjdk:8
LABEL maintainer="ketantotlani"
ADD target/ppmtool-0.0.1-SNAPSHOT.jar ppm-tool-docker.jar
ENTRYPOINT ["java", "-jar", "ppm-tool-docker.jar"]