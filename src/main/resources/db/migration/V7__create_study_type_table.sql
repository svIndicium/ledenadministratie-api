create table study_type
(
    id uuid not null
        constraint study_type_pkey
            primary key,
    name varchar(255),
    short_name varchar(255)
);