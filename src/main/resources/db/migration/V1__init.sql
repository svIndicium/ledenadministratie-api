create table users
(
  id bigint not null
    constraint users_pkey
      primary key
);

alter table users
  owner to postgres;

create sequence user_seq;

alter sequence user_seq owner to postgres;

