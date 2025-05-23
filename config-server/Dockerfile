# Используем официальный JDK образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Установка curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Копируем `.jar` файл в контейнер
COPY target/config-server-0.0.1-SNAPSHOT.jar config-server-0.0.1-SNAPSHOT.jar

# Экспонируем порт 8888, на котором будет работать Config Server
EXPOSE 8888

# Запускаем приложение
ENTRYPOINT ["java","-jar","config-server-0.0.1-SNAPSHOT.jar"]