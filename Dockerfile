FROM maven:3.8.3-openjdk-17 as build
#берем базовй образ

# рабочая диреткория для файлов приложения
WORKDIR /workspace/app

# копирую нужніе фалй в корневую папку(.)
COPY pom.xml .
#копирую нужніе фалй из src в src
COPY src src

# отключаем при сборке тесты чтобы не увеличивать нагрузку  на проде
RUN mvn -DskipTests=true clean package

#создаем директорию и копируем все нужнве зависимости (jar файлы)
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# для запуска используем образ без доп зависимостей- мавен , jdk/ alpine -минимальнй образ
FROM eclipse-temurin:17-jre-alpine

#созадаем переменную с путем
ARG DEPENDENCY=/workspace/app/target/dependency

#копируем файлы из первого образа во второй только нужніе нам файлі
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF/lib /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

#коанда для запуспук приложения в нужніми атрибутами

ENTRYPOINT ["java", "-cp",
#копируем заваисимости
 "app:app/lib/*",
# главный класс
  "de.aittr.g_38_jp_shop.ShopApplication"]

