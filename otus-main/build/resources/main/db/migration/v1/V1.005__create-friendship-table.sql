create table friendship
(
    id        bigint auto_increment primary key,
    user1_id  bigint                             not null,
    user2_id  bigint                             not null,
    createdAt datetime default CURRENT_TIMESTAMP not null,
    direction varchar(50)                        null,
    status    varchar(50)                        null,
    constraint friendship_user1_fk
        foreign key (user1_id) references users (id),
    constraint friendship_users_uc unique (user1_id, user2_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;