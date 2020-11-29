create table mail_address
(
    id uuid not null
        constraint mail_address_pkey
            primary key,
    address varchar(255) not null,
    created_at timestamp,
    receives_newsletter boolean,
    updated_at timestamp,
    verification_requested_at timestamp,
    verification_token varchar(255)
        constraint uk_verification_token
            unique,
    verified_at timestamp,
    member_auth_id varchar(255)
        constraint fk_mail_address_members
            references members,
    registration_id uuid
        constraint fk_mail_address_registration
            references registration
);
