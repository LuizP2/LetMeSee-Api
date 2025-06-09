FROM openjdk:17-jdk-slim
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /home/appuser
COPY snapshot.jar app.jar
USER appuser
ENTRYPOINT ["java", "-jar", "/home/appuser/app.jar"]
