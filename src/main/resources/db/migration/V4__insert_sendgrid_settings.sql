insert into setting(key, value, description, permission, title)
values ('SENDGRID_API_KEY', '', 'De Sendgrid api key', 'sendgrid', 'Sendgrid API key');
insert into setting(key, value, description, permission, title)
values ('SENDGRID_VERIFICATION_FROM_MAIL', '', 'Het mailadres waar de verificatiemail vandaan komt',
        'sendgrid', 'Bevestigingsmailadres zendadres');
insert into setting(key, value, description, permission, title)
values ('SENDGRID_VERIFICATION_FROM_NAME', '', 'De naam van wie de verificatiemail komt', 'sendgrid',
        'Bevestigingsmail zendersnaam');
insert into setting(key, value, description, permission, title)
values ('SENDGRID_VERIFICATION_TEMPLATE', '', 'De Sendgrid template die gebruikt wordt', 'sendgrid',
        'Bevestigingsmail sendgrid template ID');