create table manufacturer
(
    id      int
        constraint manufacturer_pk primary key,
    name    varchar(20) not null unique,
    country varchar(20) not null
);
