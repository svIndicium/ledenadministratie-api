create table group_membership
(
  id         bigint    not null
    constraint group_membership_pkey
      primary key,
  created    timestamp,
  end_date   timestamp not null,
  start_date timestamp not null,
  updated    timestamp,
  fk_group   bigint
    constraint fk_membership_group
      references groups,
  fk_user    bigint
    constraint fk_membership_user
      references users
);

create sequence group_membership_seq;