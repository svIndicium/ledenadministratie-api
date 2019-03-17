create table groups
(
  id   bigint       not null
    constraint groups_pkey
      primary key,
  name varchar(255) not null
);

create sequence group_seq;
