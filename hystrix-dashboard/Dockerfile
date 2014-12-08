FROM jeanblanchard/busybox-tomcat:8

RUN cd /tmp && curl -L -k -O https://search.maven.org/remotecontent?filepath=com/netflix/hystrix/hystrix-dashboard/1.3.18/hystrix-dashboard-1.3.18.war
RUN rm -rf /opt/tomcat/webapps/ &&\ 
    mkdir -p /opt/tomcat/webapps/ROOT &&\ 
    unzip /tmp/hystrix-dashboard-1.3.18.war -d /opt/tomcat/webapps/ROOT &&\
    rm -rf /tmp/hystrix-dashboard-1.3.18.war

ENV CATALINA_OPTS -Djava.security.egd=file:/dev/urandom -Darchaius.deployment.environment=dev