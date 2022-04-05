From openjdk:8
copy ./target/openApiApp-portal-0.0.1-SNAPSHOT.jar openApiApp-portal-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","openApiApp-portal-0.0.1-SNAPSHOT.jar"]

