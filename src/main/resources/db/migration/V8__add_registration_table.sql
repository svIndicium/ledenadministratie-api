create table registrations
(
    id                       bigint       not null
        constraint registrations_pkey
            primary key,
    approved                 boolean,
    comment                  varchar(255),
    created                  timestamp,
    date_of_birth            timestamp    not null,
    email                    varchar(255) not null
        constraint unique_registration_email
            unique,
    finalized_at             timestamp,
    finalized_by             varchar(255),
    first_name               varchar(255) not null,
    is_to_receive_newsletter boolean      not null,
    last_name                varchar(255) not null,
    middle_name              varchar(255),
    phone_number             varchar(255) not null
        constraint unique_registration_phone_number
            unique,
    updated                  timestamp,
    study_type_id            bigint       not null
        constraint fk_registration_study_type
            references study_type
);