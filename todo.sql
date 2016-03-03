
drop database if exists todo;

create database todo;

grant all on todo.* to 'todo'@'localhost' identified by 'todo';

use todo;

create table tasks (
  id int not null auto_increment,
  description varchar(100) not null,
  primary key (id)
);

insert into tasks (description) values ('eat lunch');
insert into tasks (description) values ('read a book');
insert into tasks (description) values ('watch tv');
