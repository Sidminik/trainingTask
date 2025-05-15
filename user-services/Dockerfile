# Используем официальный JDK образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем `.jar` файл в контейнер
COPY target/user-services-0.0.1-SNAPSHOT.jar user-services-0.0.1-SNAPSHOT.jar

# Открываем порт, используемый вашим приложением
EXPOSE 8081

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "user-services-0.0.1-SNAPSHOT.jar"]