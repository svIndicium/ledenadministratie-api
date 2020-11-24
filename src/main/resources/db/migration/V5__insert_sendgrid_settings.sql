insert into setting(key, value, description, permission, title)
values ('SENDGRID_PASSWORD_RESET_FROM_MAIL', '', 'Het mailadres waar de password reset vandaan komt',
        'sendgrid_settings', 'Wachtwoord resetmail zendadres');
insert into setting(key, value, description, permission, title)
values ('SENDGRID_PASSWORD_RESET_FROM_NAME', '', 'De naam van wie de wachtwoord resetmail komt', 'sendgrid_settings',
        'Wachtwoord resetmail zendersnaam');
insert into setting(key, value, description, permission, title)
values ('SENDGRID_PASSWORD_RESET_TEMPLATE', '', 'De Sendgrid template die gebruikt wordt', 'sendgrid_settings',
        'Wachtwoord resetmail sendgrid template ID');