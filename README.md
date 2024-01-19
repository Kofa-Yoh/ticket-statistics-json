# Консольное приложение для расчета ряда статистических данных
Проект демонстрирует обработку списка авиабилетов из JSON-файла и расчет следующих параметров:
- минимальное время полета между городами Владивосток и Тель-Авив в минутах для каждого авиаперевозчика
- разница между средней ценой и медианой для полета между городами  Владивосток и Тель-Авив
  
Путь к файлу указывается через консоль.

## Стэк:
- Java 17
- Jackson
- Lombok
- Maven

## Запуск из командной строки
```
java -jar target/TicketStatistics.jar
```
