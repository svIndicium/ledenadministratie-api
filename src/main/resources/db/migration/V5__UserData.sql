create table user_data
(
  id            bigint       not null
    constraint user_data_pkey
      primary key
    constraint fk_user_id
      references users,
  first_name    varchar(255) not null,
  last_name     varchar(255) not null,
  gender        integer      not null,
  date_of_birth timestamp    not null,
  street        varchar(255) not null,
  house_number  varchar(255) not null,
  zip_code      varchar(255) not null,
  city          varchar(255) not null,
  country       varchar(255) not null,
  email         varchar(255) not null,
  phone_number  varchar(255) not null,
  student_id    integer      not null,
  created       timestamp,
  updated       timestamp
);