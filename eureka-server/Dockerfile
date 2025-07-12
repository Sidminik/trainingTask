# Используем официальный JDK образ
FROM openjdk:17-jdk-slim

# Устанавливаем метаданные о образе
LABEL name="my-eureka-server" \
      version="0.1" \
      description="Eureka server for service discovery"

# Устанавливаем рабочую директорию
WORKDIR /app

# Установка curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Копируем `.jar` файл в контейнер
COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server-0.0.1-SNAPSHOT.jar

# Открываем порт, используемый вашим приложением
EXPOSE 8761

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar"]