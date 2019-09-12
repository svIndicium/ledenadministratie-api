create table users
(
    id            bigint not null
        constraint users_pkey
            primary key,
    date_of_birth timestamp,
    email         varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    middle_name   varchar(255),
    phone_number  varchar(255),
    study_type_id bigint not null
        constraint fk_user_study_type
            references study_type
);