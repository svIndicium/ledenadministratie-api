create table study_type
(
  id         bigint       not null
    constraint study_type_pkey
      primary key,
  long_name  varchar(255) not null,
  short_name varchar(255) not null
);


create sequence study_type_seq;

create table study
(
  id         bigint    not null
    constraint study_pkey
      primary key,
  start_date timestamp not null,
  fk_study   bigint    not null
    constraint fk_study_type
      references study_type,
  user_id    bigint    not null
    constraint fk_study_user
      references users
);

create sequence study_seq;