# Kafka Connect MessageBuilder for Windows-1251 and other encodings

Кастомный `MessageBuilder` для IBM MQ Sink Connector, позволяющий отправлять сообщения из Kafka в MQ в указанной кодировке (например, Windows-1251).

## Возможности

- Преобразование сообщений Kafka (`String`) в `javax.jms.BytesMessage`.
- Поддержка настройки кодировки через параметр конфигурации:  
  `mq.message.encoding` (например, `Windows-1251`, `UTF-8`, `KOI8-R`, и др.)
- Совместимость с IBM MQ Sink Connector версии `2.2.1`.

## Сборка

### 1. Установите IBM MQ Sink Connector в локальный Maven-репозиторий
```
mvn install:install-file \
-Dfile=lib\kafka-connect-mq-sink-2.2.1-jar-with-dependencies.jar \
-DgroupId=com.ibm.eventstreams \
-DartifactId=kafka-connect-mq-sink \
-Dversion=2.2.1  
-Dpackaging=jar
```

Windows:
```
mvn install:install-file ^
-Dfile=lib/kafka-connect-mq-sink-2.2.1-jar-with-dependencies.jar ^
-DgroupId=com.ibm.eventstreams ^
-DartifactId=kafka-connect-mq-sink ^
-Dversion=2.2.1 ^ 
-Dpackaging=jar
```

### 2. Соберите проект

```
mvn clean package
```
Готовый `.jar` появится в:
```
target/mq-sink-cp1251-1.0-SNAPSHOT-jar-with-dependencies.jar
```


## Использование в Kafka Connect

1. Скопируйте `.jar` в директорию плагинов:
```
/opt/kafka/connectors/cp1251-sink/
```

2. Убедитесь, что в `plugin.path` Kafka Connect указана эта директория:
```properties
plugin.path=/opt/kafka/connectors
```

3. В конфигурации Sink Connector укажите:
```
"mq.message.builder": "custom.builder.Cp1251MessageBuilder",
"mq.message.encoding": "Windows-1251"
```
> Если `mq.message.encoding` не задан, используется кодировка по умолчанию: `Windows-1251`.

## Требования
- Java 11+
- Maven 3.6+
- Kafka Connect
- IBM MQ Sink Connector (JAR включён в `lib/`)

## Авторские права и лицензия

Данный проект расширяет функциональность официального IBM MQ Sink Connector и использует компоненты Apache Kafka Connect.

### IBM MQ Sink Connector

- Репозиторий: [https://github.com/ibm-messaging/kafka-connect-mq-sink](https://github.com/ibm-messaging/kafka-connect-mq-sink)
- Лицензия: [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
- Автор: IBM Corporation

Файл `kafka-connect-mq-sink-2.2.1-jar-with-dependencies.jar` включён в каталог `lib/` **исключительно для удобства локальной сборки**. Вы можете загрузить его напрямую с [официальной страницы релизов IBM.](https://github.com/ibm-messaging/kafka-connect-mq-sink)

### Apache Kafka Connect API

- Репозиторий: [https://github.com/apache/kafka](https://github.com/apache/kafka)
- Лицензия: [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
- Автор: Apache Software Foundation

Библиотека `org.apache.kafka:connect-api` используется в качестве основной зависимости для интеграции с Kafka Connect.

> Использование производится в соответствии с условиями лицензии Apache 2.0, включая указание оригинального источника и сохранение авторства.

This project is licensed under the MIT License — see the [LICENSE](./LICENSE)  file for details.
