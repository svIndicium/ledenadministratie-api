alter table users
    alter column date_of_birth set not null;

alter table users
    alter column email set not null;

alter table users
    alter column first_name set not null;

alter table users
    alter column last_name set not null;

alter table users
    alter column phone_number set not null;

alter table users
    alter column study_type_id set default 8;

create unique index users_email_uindex
    on users (email);

create unique index users_phone_number_uindex
    on users (phone_number);

