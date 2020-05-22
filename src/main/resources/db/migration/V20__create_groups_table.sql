create table groups
(
    id          bigint       not null
        constraint groups_pkey
            primary key,
    created_at  timestamp,
    description varchar(255),
    name        varchar(255) not null,
    updated_at  timestamp
);

create unique index groups_name_uindex
    on groups (name);