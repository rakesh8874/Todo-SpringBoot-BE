FROM openjdk
WORKDIR usr/lib
ADD ./target/TodoService-0.0.1-SNAPSHOT.jar /usr/lib/TodoService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","TodoService-0.0.1-SNAPSHOT.jar"]