# Use a imagem base do Maven com OpenJDK 17 como estágio de construção
FROM maven:3.8.1-openjdk-17 AS maven

#ARG JAR_FILE(RECEBE COMO ARGUMENTO O NOME DO JAR FILE DO POM)

# Copie o diretório "src" do projeto para dentro do contêiner em "/app/src"
COPY /src /app/src

# Copie o arquivo "pom.xml" do projeto para dentro do contêiner em "/app"
COPY /pom.xml /app

# Execute o comando Maven para compilar o projeto
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip

# Use a imagem base do OpenJDK 17 como estágio final
FROM openjdk:17-jdk-alpine

# Exponha a porta 8080 no contêiner
EXPOSE 8080

# Copie o arquivo JAR gerado a partir do estágio de construção "maven" para o contêiner
COPY --from=maven /app/target/*.jar app.jar

# Defina o comando de entrada para executar o aplicativo Java
ENTRYPOINT ["java", "-jar", "/app.jar"]

