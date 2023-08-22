create table manufacturer
(
    id   int
        constraint manufacturer_pk primary key,
    name varchar(20) not null unique
);

/*insert into manufacturer(id, name) values(1, 'Volvo');
insert into manufacturer(id, name) values(2, 'Ford');
insert into manufacturer(id, name) values(3, 'Peugeot');
*/