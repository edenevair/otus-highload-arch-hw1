#!/bin/bash

docker rm -f otus-mysql-hw1

# запуск контейнера с БД
docker run --platform linux/x86_64/v8 --name otus-mysql-hw1 -d -p 33306:3306 \
  -e MYSQL_USER=otus_user \
  -e MYSQL_PASSWORD=12345678 \
  -e MYSQL_ROOT_PASSWORD=pa$$w0rd \
  -e MYSQL_DATABASE=hw1 \
   mysql:8.0.23

# иинциализация предварительными настройками
#docker exec -i otus-mysql-hw1 mysql -uroot -ppa$$w0rd < ./init-db.sql