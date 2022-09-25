create table user_login
(
    id         bigint auto_increment
        primary key,
    login varchar(50) not null,
    password  varchar(250) not null,
    user_id  bigint not null,
    constraint user_login_user_fk
        foreign key (user_id) references users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;