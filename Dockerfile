FROM tomcat:9.0-jdk8-corretto

COPY target/spring-batch-quartz-admin.war /usr/local/tomcat/webapps/

VOLUME ["/usr/local/batch-config"]

EXPOSE 8080

CMD ["catalina.sh", "run"]
