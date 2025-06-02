# build concrete image
FROM eclipse-temurin:21

# set the same workdir
WORKDIR /app

# copy jar from previous stage
COPY /target/*.jar app.jar

# eXpose port
# EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
