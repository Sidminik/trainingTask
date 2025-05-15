# Используем официальный JDK образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем `.jar` файл в контейнер
COPY target/company-services-0.0.1-SNAPSHOT.jar company-services-0.0.1-SNAPSHOT.jar

# Открываем порт, используемый вашим приложением
EXPOSE 8082

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "company-services-0.0.1-SNAPSHOT.jar"]