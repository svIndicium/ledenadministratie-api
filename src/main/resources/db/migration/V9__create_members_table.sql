create table members
(
    auth_id varchar(255) not null
        constraint members_pkey
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
    updated_at timestamp,
    study_type_id uuid
        constraint fk_members_study_type
            references study_type,
    registration_id uuid
        constraint fk_members_registration
            references registration
);
