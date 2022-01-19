FROM openjdk:11

RUN apt-get update && \
 apt-get install -y netcat;

RUN mkdir -p /usr/server
COPY artifacts/investpess_bffweb_ativo_jar/investpess-bffweb-ativo.jar /usr/server/investpess-bffweb-ativo.jar
COPY wait-for-mysql.sh /user/server/wait-for-mysql.sh
WORKDIR /usr/server
#CMD ["java","-jar", "/usr/server/investpess-bffweb-ativo.jar"]
EXPOSE 8080