create table locations (id bigint not null auto_increment, lat double precision not null, lon double precision not null, location_name varchar(255), constraint pk_locations primary key (id));