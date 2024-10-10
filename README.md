# messaging-serivces
Нужно заимплементить отказоустойчивую систему, которая порционно будет записывать сообщения в базу данных (дополнительные улучшения будут плюсом)
1. Имплементация 2х сервисов: 1. Сервис1 -генерирует много сообщений,  
2. Сервис 2 - принимает, обрабатывает и записывает в Н2, сервис-2 - сервис с секьюрити - обязательно. 
логирование отдельным компонентом.
<br/>
<p align="center">
  <!-- <img src="https://github.com/Balagurovskiy/messaging-serivces/blob/7e1208cd0261b88d39befb6e51bfce953e1275e7/schema.jpg" title="hover text"> -->
</p>


# BUILD / DEPLOYMENT
### DEV
#### message-handler
1. Select **dev** profile in **application.properties**
2. Generate certificates for https (classpath:resources/certs/https):
``
   openssl genpkey -algorithm RSA -out private-key.pem -pkeyopt rsa_keygen_bits:2048
   sleep 5s
   openssl req -new -x509 -key private-key.pem -out public-cert.pem -days 365 \
   -subj "/C=US/ST=California/L=SanFrancisco/O=MyCompany/OU=ITDepartment/CN=localhost/emailAddress=admin@mycompany.com"
   sleep 5s
   openssl pkcs12 -export -in public-cert.pem -inkey private-key.pem -out service-keystore.p12 -name service-alias -passout pass:your_password
``
3. With created service-keystore.p12 and pass:your_password update dev properties to enable ssl for endpoints:
``
   server.ssl.key-store=classpath:certs/https/service-keystore.p12
   server.ssl.key-store-type=PKCS12
   server.ssl.key-store-password=DEC(your_password)
   server.ssl.key-alias=service-alias
``
4. Copy (to classpath:resources/certs/token) public certificate from token provider (message-generator) and update property:
``
   security.token.rsa.public-key=classpath:certs/token/public.pem
``
5. Encrypt sensitive information using maven plugin
   ``
   mvn jasypt:encrypt "-Djasypt.encryptor.password=ENC_KEY"
   ``
#### message-generator
5. Select **dev** profile in **application.properties**
6. Enable ssl requests; Copy (classpath:resources/certs/https) public-cert.pem from the secured service (message-generator) and 
generate truststore :
``
   keytool -importcert -file public-cert.pem -alias truststore_cert -keystore sending-truststore.p12 -storetype PKCS12 -storepass your_password
``
7. With created sending-truststore.p12 and pass:your_password update dev properties to enable ssl for requests:
  ``
   spring.ssl.bundle.jks.bundle-client.truststore.location=classpath:certs/https/sending-truststore.p12
   spring.ssl.bundle.jks.bundle-client.truststore.password=DEC(your_password)
``
8. Generate certificates for jwt:
``
   openssl genrsa -out keypair.pem 2048
   openssl rsa -in  keypair.pem -pubout -out public.pem
   openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
``
9. Update properties for token:
   ``
   security.token.rsa.private-key=classpath:certs/token/private.pem
   security.token.rsa.public-key=classpath:certs/token/public.pem
   security.token.username=message-generator
   security.token.expiration.minutes=10
   security.key.algorithm=RSA
   security.key.size=2048
   security.bundle.name=bundle-client
   ``
9. Encrypt sensitive information using maven plugin
   ``
   mvn jasypt:encrypt "-Djasypt.encryptor.password=ENC_KEY"
   ``
#### messages (root folder)
10. Build applications
   ``
   mvn clean install
   ``
11. Execute docker compose for dev (password have to be the same as in steps 5, 8)
   ``
   docker-compose up
   java -jar message-handler/target/message-handler.jar --jasypt.encryptor.password=ENC_KEY
   java -jar message-generator/target/message-generator.jar --jasypt.encryptor.password=ENC_KEY
``
### PROD
#### message-generator / message-handler
Properties should be updated with **prod** profile and all other steps should be similar to dev setup.
Each application should be prepared for release environment: prod properties have to be updated with db, host details (zipkin etc), certificate namings
(**CA subject** for release https certificate have to updated with host details for **message-handler** in **prod** env).
Update Docker file **ENTRYPOINT** : ... --jasypt.encryptor.password=ENC_KEY  (password have to be the same as during maven encryption process).
Update appender url in **logback-spring.xml** with host related to prod env.
#### messages (root folder)
1. Build applications
    ``
    mvn clean install
    ``
2. Execute docker compose for prod; separate image creation not required (docker build -t <name>)
``
    docker-compose -f docker-compose-prod.yml up
``

