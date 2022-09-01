create table news
(
    id           bigint auto_increment primary key,
    content      text                               not null,
    created_at   datetime default CURRENT_TIMESTAMP null,
    user_from_id bigint                             not null,
    constraint news_user_from_fk
        foreign key (user_from_id) references users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;