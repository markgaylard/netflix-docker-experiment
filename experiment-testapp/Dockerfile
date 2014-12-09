FROM jeanblanchard/busybox-tomcat:8

ENV VERSION 5

COPY ./build/libs/experiment-testapp-1.0.war /tmp/
RUN rm -rf /opt/tomcat/webapps/ &&\ 
    mkdir -p /opt/tomcat/webapps/ROOT &&\ 
    unzip /tmp/experiment-testapp-1.0.war -d /opt/tomcat/webapps/ROOT &&\
    rm -rf /tmp/experiment-testapp-1.0.war

ENV CATALINA_OPTS -Djava.security.egd=file:/dev/urandom -Darchaius.deployment.environment=docker