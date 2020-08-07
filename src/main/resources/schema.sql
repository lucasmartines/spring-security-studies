drop table if exists user;

create table if not exists  user 
( 
id integer not null primary key auto_increment,
nome varchar(60) not null unique, 
senha varchar(255) not null , 
role varchar(150) not null 
) ;
