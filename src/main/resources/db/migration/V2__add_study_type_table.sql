create table study_type
(
    id   bigint not null
        constraint study_type_pkey
            primary key,
    name varchar(255)
);