CREATE TABLE TEXT(post_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), postname VARCHAR(140))
SELECT * FROM TEXT order  by post_id desc
INSERT INTO TEXT(postname) VALUES ('dfsfsdfds')
values IDENTITY_VAL_LOCAL()
VALUES CURRENT_TIMESTAMP
create table test(tstamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, text varchar(140))
insert into test(text) values('other text');
select * from test
CREATE TABLE USERS(user_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), popularity DOUBLE default 1.0, password varchar(20), pic varchar(50), nickname varchar(20), username varchar(20) unique, stalkers integer)
CREATE TABLE MENTIONS(mentioner INTEGER, mentionee integer)
CREATE TABLE STALKER(stalker varchar(20) NOT NULL, stalkee varchar(20) NOT NULL)
drop table stalker
CREATE TABLE TOPIC(topic varchar(20), mid integer)
CREATE TABLE POSTS(owner integer, mid INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,replyto integer default 0, times_republished int default 0,popularity  double default 0.0, republish_of int default 0,text varchar(140))
drop table 
insert into USERS(username,password) values('test','123')
select USER_ID from USERS where USERNAME='test' and PASSWORD='123'
alter table POSTS add text varchar(140)
alter table POSTS drop column text
drop table posts
alter table users add stalkees int default 0
alter table stalker add column stalkee_id int
delete from topic
alter table u add column mentionee varchar(140)
alter table users alter column pic set data type varchar(150)
insert into stalker(stalker,stalkee) values('nick','test')
select stalker.stalkee from stalker join users on stalker.stalkee=users.nickname where stalker.stalker='nick' order by users.popularity desc fetch first 10 rows only
