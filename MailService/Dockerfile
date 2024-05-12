FROM openjdk
WORKDIR usr/lib
ADD ./target/MailService-0.0.1-SNAPSHOT.jar /usr/lib/MailService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","MailService-0.0.1-SNAPSHOT.jar"]