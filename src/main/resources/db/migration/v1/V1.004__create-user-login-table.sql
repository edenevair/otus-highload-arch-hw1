create table user_login
(
    id         bigint auto_increment
        primary key,
    login varchar(50) not null,
    password  varchar(50) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;