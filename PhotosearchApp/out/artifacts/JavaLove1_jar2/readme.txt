Сигнатура запуска:
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar

------------СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ------------

Создание пользователя
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 customer Moscow
Создание события
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db DB CREATE_NEW_EVENT auto_race competition 1 14-02-2021 150 2.5 
Получение пользователя
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db GET_PROFILE 1
Получение события
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db GET_EVENT 1

------------ФОТО-------------

Добавление фото к портфолио пользователя с id 1
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db ADD_PHOTO 1 "./testPhotos/test3.jpg"

Просмотр портфолио id пользователя с id 1
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db GET_PORTFOLIO 1 

Просмотр изображения по id
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db SHOW_PHOTO 1 

------------ФОТОГРАФ------------
Создание фотографа
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db CREATE_NEW_PROFILE Sergey Esenin 12-10-1999 photographer Moscow

------------ПОИСК------------
Поиск пользователя по имени
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db SEARCH_USERS name Sergey
Поиск пользователя по городу
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db SEARCH_USERS town Moscow


Поиск фотографа по имени
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db SEARCH_PHOTOGRAPHERS name Sergey
Поиск фотографа по городу
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db SEARCH_PHOTOGRAPHERS town Moscow