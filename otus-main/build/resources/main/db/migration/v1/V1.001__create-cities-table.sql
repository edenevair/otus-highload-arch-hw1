create table cities
(
    id   bigint auto_increment
        primary key,
    name varchar(120) not null,
    constraint cities_id_uindex
        unique (id),
    constraint cities_name_uindex
        unique (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;