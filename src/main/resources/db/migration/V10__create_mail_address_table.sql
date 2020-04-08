create table mail_address
(
    mail_id                   bigint       not null,
    user_id                   bigint       not null
        constraint fk_mail_address_user
            references users,
    created_at                timestamp,
    mail_address              varchar(255) not null,
    receives_newsletter       boolean,
    updated_at                timestamp,
    verification_requested_at timestamp,
    verification_token        varchar(255),
    verified_at               timestamp,
    constraint mail_address_pkey
        primary key (mail_id, user_id)
);

