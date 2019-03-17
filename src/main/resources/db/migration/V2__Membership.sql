create table memberships
(
  id         bigint    not null
    constraint memberships_pkey
      primary key,
  created    timestamp,
  end_date   timestamp not null,
  start_date timestamp not null,
  updated    timestamp,
  fk_user    bigint    not null
    constraint fk4ew2j47rh0gmhuqyq4ec52je
      references users
);

create sequence member_seq;
