FROM openjdk:8-jre-alpine

EXPOSE 8080

ARG SERVICENAME="unknown"
ARG VERSION="unknown"
ARG WORKDIR="/usr/lib/hercules/${SERVICENAME}"

COPY ${SERVICENAME}/target/${SERVICENAME}-${VERSION}.jar ${WORKDIR}/app.jar

WORKDIR ${WORKDIR}

ENTRYPOINT java $JAVA_OPTIONS -jar app.jar

