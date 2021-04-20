alter table mail_address drop column verification_requested_at;

alter table mail_address drop column verified_at;

alter table mail_address drop constraint uk_verification_token;

alter table mail_address drop column verification_token;