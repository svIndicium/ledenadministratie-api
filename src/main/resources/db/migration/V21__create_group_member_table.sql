create table group_member
(
    id         bigint    not null
        constraint group_member_pkey
            primary key,
    created_at timestamp,
    end_date   timestamp not null,
    start_date timestamp not null,
    updated_at timestamp,
    group_id   bigint    not null
        constraint fk_group_member_group
            references groups,
    user_id    bigint    not null
        constraint fk_group_member_user
            references users
);


create sequence group_member_seq;


