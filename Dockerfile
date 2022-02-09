FROM maven:3.6.3-jdk-8 AS maven
USER root
COPY ./ /tmp/code
RUN cd /tmp/code && mvn clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true


FROM openjdk:8-jdk-oracle
COPY --from=maven /tmp/code/target/SparrowRecSys*.jar /SparrowRecSys.jar
EXPOSE 6010
ENV JAVA_OPTS="-Xmx512m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /SparrowRecSys.jar"]
