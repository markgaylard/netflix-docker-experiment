FROM jeanblanchard/busybox-java:jdk8

COPY ./build/libs/experiment-service1-server-1.0-capsule.jar ~/

EXPOSE 8081
EXPOSE 8077
CMD ["java", "-Darchaius.deployment.environment=dev", "-jar", "~/experiment-service1-server-1.0-capsule.jar" ]
