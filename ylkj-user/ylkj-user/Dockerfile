FROM java:8
RUN mkdir -p /tmp
WORKDIR /tmp
COPY target/ylkj-user.jar ./app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","app.jar", "--spring.profiles.active=prod"]
