/*
 data class Car(
     val regNo: RegNo,
     val year: Int,
     val manufacturer: Manufacturer,
     val model: Model,
     val engine: Engine
 )
 */

create table car
(
    id   int
        constraint car_pk primary key,
    regno varchar(6) not null unique,
    year int not null
);

/*insert into manufacturer(id, name) values(1, 'Volvo');
insert into manufacturer(id, name) values(2, 'Ford');
insert into manufacturer(id, name) values(3, 'Peugeot');
*/