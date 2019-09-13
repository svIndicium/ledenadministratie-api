FROM openjdk:11
VOLUME /tmp
ADD build/libs/ledenadministratie-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=,suspend=n"
EXPOSE 8080 8787
CMD java $JAVA_OPTS $JAVA_TOOL_OPTIONS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=main -jar -Dserver.port=$PORT /app.jar