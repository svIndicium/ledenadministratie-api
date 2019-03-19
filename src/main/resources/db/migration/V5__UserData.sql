create table user_data
(
  id               bigint       not null
    constraint user_data_pkey
      primary key
    constraint fkr485y1dylmvsgld351w5r3m08
      references users,
  city             varchar(255) not null,
  country          varchar(255) not null,
  created          timestamp,
  date_of_birth    timestamp    not null,
  date_of_register timestamp    not null,
  email            varchar(255) not null,
  first_name       varchar(255) not null,
  gender           integer      not null,
  house_number     varchar(255) not null,
  last_name        varchar(255) not null,
  phone_number     varchar(255) not null,
  street           varchar(255) not null,
  student_id       integer      not null,
  updated          timestamp,
  zip_code         varchar(255) not null
);