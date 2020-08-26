create table membership
(
    id bigint not null
        constraint membership_pkey
            primary key,
    end_date timestamp,
    start_date timestamp,
    user_id bigint not null
        constraint fk_membership_user
            references users
);

create sequence membership_seq;

