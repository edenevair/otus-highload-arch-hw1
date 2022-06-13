create table users
(
    id         bigint auto_increment
        primary key,
    first_name varchar(50) not null,
    last_name  varchar(50) null,
    age        int         null,
    gender     varchar(10) null,
    city_id        bigint,
    constraint users_cities_fk
        foreign key (city_id) references cities (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;