FROM openjdk:19
COPY target/message-generator.jar message-generator.jar
ENTRYPOINT ["java", "-jar", "/message-generator.jar", "--jasypt.encryptor.password=enc_key"]