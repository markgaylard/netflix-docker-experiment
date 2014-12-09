FROM jeanblanchard/busybox-java:jdk8

ENV VERSION 1

COPY ./build/libs/experiment-service1-server-1.0-capsule.jar ~/

EXPOSE 8081
EXPOSE 8077
CMD ["java", "-Darchaius.deployment.environment=docker", "-Djava.security.egd=file:/dev/urandom", "-jar", "~/experiment-service1-server-1.0-capsule.jar" ]
