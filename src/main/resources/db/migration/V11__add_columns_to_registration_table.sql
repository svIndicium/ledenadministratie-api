alter table registrations
    drop constraint unique_registration_email;

alter table registrations
    rename column email to mail_address;

alter table registrations
    add column verification_requested_at timestamp;

alter table registrations
    add column verification_token varchar(255);

alter table registrations
    add column verified_at timestamp;
