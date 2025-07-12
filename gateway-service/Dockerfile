# Используем официальный JDK образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем `.jar` файл в контейнер
COPY target/gateway-service-0.0.1-SNAPSHOT.jar gateway-service-0.0.1-SNAPSHOT.jar

# Экспонируем порт 8085, на котором будет работать Gateway
EXPOSE 8085

# Запускаем приложение
ENTRYPOINT ["java","-jar","gateway-service-0.0.1-SNAPSHOT.jar"]
