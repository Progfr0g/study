Сигнатура запуска:
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar

Создание пользователя
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db CREATE_NEW_PROFILE boris zmaylov 40 customer rostov
Создание события
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db CREATE_NEW_EVENT firstevent "this is first event ever created" 1 150 2.5 
Получение пользователя
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db GET_PROFILE 1
Получение события
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db GET_EVENT 1

Добавление фото к портфолио пользователя с id 1
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db ADD_PHOTO 1 "./testPhotos/1.jpg"
Просмотр портфолио id пользователя с id 1
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db GET_POrTFOLIO 1 
Просмотр изображения по id
java -D'config.path=./environment.properties' -D'log4j.configurationFile=./log4j2.properties' -jar JavaLove1.jar db SHOW_PHOTO 1 
