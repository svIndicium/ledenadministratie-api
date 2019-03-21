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
    constraint fk_membership_user
      references users
);

create sequence member_seq;
