create table friendship
(
    user1_id  bigint                             not null,
    user2_id  bigint                             not null,
    createdAt datetime default CURRENT_TIMESTAMP not null,
    direction varchar(50)                        null,
    status    varchar(50)                        null,
    primary key (user1_id, user2_id),
    constraint friendship_user1_fk
        foreign key (user1_id) references users (id),
    constraint friendship_user2_fk
        foreign key (user2_id) references users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;