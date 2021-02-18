#FROM openjdk:11-jdk-slim
FROM adoptopenjdk/openjdk11:jre11u-ubuntu-nightly

#RUN apk --no-cache add openjdk11-jdk openjdk11-jmods

#ENV JAVA_MINIMAL="/opt/java-minimal"

# build minimal JRE
#RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
#    --verbose \
#    --add-modules \
#        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.net \
#    --compress 2 --strip-debug --no-header-files --no-man-pages \
#    --release-info="add:IMPLEMENTOR=radistao:IMPLEMENTOR_VERSION=radistao_JRE" \
#    --output "$JAVA_MINIMAL"

#FROM alpine:latest

#ENV JAVA_HOME=/opt/java-minimal
#ENV PATH="$PATH:$JAVA_HOME/bin"

#COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
COPY api/build/libs/msc-ais-weather-1.0.1.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]