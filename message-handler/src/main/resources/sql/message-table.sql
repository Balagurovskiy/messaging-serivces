drop table messages;
create table messages(id bigint auto_increment, message varchar(255), createDt TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
insert into messages(message) values('test 1');
select * from messages;