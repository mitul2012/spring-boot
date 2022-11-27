FROM openjdk:latest
WORKDIR /app
COPY target/demo-1.jar /app
EXPOSE 8090
CMD ["java","-jar","/app/demo-1.jar"]
