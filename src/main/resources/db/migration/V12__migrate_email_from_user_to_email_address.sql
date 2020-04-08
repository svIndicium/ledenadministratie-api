insert into mail_address(mail_id, user_id, created_at, mail_address, receives_newsletter, updated_at) (
    SELECT 0, id, now(), email, is_to_receive_newsletter, now()
    from users);