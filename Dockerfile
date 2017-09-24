FROM anapsix/alpine-java
MAINTAINER Axvad
COPY ./target/githubsnapshot-0.0.1-SNAPSHOT.jar /gitsnap/testprj-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD java -Xmx300m -Dserver.port=$PORT $JAVA_OPTS -jar /gitsnap/testprj-1.0-SNAPSHOT.jar