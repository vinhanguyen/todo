
drop database if exists todo;

create database todo;

grant all on todo.* to 'todo'@'localhost' identified by 'todo';

use todo;

create table users (
  user_name varchar(15) not null primary key,
  user_pass varchar(15) not null
);

create table user_roles (
  user_name varchar(15) not null,
  role_name varchar(15) not null,
  primary key (user_name, role_name)
);

create table tasks (
  id int not null auto_increment,
  description varchar(100) not null,
  user_name varchar(15) not null,
  primary key (id),
  foreign key (user_name) references users(user_name)
);

insert into users (user_name, user_pass) values ('batman', 'password');
insert into user_roles (user_name, role_name) values ('batman', 'user');

insert into users (user_name, user_pass) values ('robin', 'password');
insert into user_roles (user_name, role_name) values ('robin', 'user');
