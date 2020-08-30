insert into membership(id, start_date, end_date, user_id) (
    select nextval('membership_seq'), date('2019-09-01'), date('2020-08-31'), users.id from users
)