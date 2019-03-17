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
    constraint fkrt63b9r9c6486w03mtmbxlao3
      references groups,
  fk_user    bigint
    constraint fk4nhclc1gdfebd16h62662j2q5
      references users
);

create sequence group_membership_seq;