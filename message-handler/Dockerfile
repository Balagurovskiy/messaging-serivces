FROM openjdk:19
COPY target/message-handler.jar message-handler.jar
ENTRYPOINT ["java", "-jar", "/message-handler.jar", "--jasypt.encryptor.password=enc_key"]