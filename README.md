Система Факультатив
===================

* Существует перечень Курсов, за каждым из которых закреплен один Преподаватель.
* Студент записывается на один или несколько Курсов.
* По окончании обучения Преподаватель выставляет Студенту и добавляет отзыв.


*Схема БД* - dbDiagram.png


*Инструкция развертывания окружения:*
* запустить Apache Tomcat 8.5, выполнив apache-tomcat-8.5.8\bin\startup.bat;
* запустить H2 Console, настроить соединение с БД (URL - jdbc:h2:tcp://localhost/~/elective; логин, пароль - sa);
* заполнить БД тестовыми данными, выполнив скрипт \resources\init.sql. 


