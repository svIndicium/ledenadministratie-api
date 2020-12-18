create table membership
(
    membership_id uuid not null
        constraint membership_pkey
            primary key,
    end_date timestamp,
    start_date timestamp,
    member_auth_id varchar(255) not null
        constraint fk_membership_members
            references members
);
