FROM openjdk:11

RUN mkdir -p /usr/server
COPY artifacts/investpess_bffweb_ativo_jar/investpess-bffweb-ativo.jar /usr/server/investpess-bffweb-ativo.jar
WORKDIR /usr/server
CMD ["java","-jar", "/usr/server/investpess-bffweb-ativo.jar"]
EXPOSE 8080