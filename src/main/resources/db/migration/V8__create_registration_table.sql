create table registration
(
    registration_id uuid not null
        constraint registration_pkey
            primary key,
    created_at timestamp,
    date_of_birth timestamp not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    middle_name varchar(255),
    phone_number varchar(255) not null,
    comment varchar(255),
    reviewed_at timestamp,
    reviewed_by varchar(255),
    review_status integer,
    study_type_id uuid
        constraint fk_registration_study_type
            references study_type
);
