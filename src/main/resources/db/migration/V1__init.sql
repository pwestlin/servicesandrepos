create table car
(
    id    int
        constraint car_pk primary key,
    regno varchar(6) not null unique,
    year  int        not null
);

create table manufacturer
(
    id      int
        constraint manufacturer_pk primary key,
    name    varchar(20) not null unique,
    country varchar(20) not null/*,
    constraint fk_carid foreign key (carid) references car(id)*/
);

create table manufaturer_car
(
    manufacturerid int not null,
    carid int not null unique,
    constraint manufaturer_car_pk primary key (manufacturerid, carid)
);