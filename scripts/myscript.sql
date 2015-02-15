CREATE TABLE TEXT(post_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), postname VARCHAR(140))
SELECT * FROM TEXT order  by post_id desc
INSERT INTO TEXT(postname) VALUES ('dfsfsdfds')
values IDENTITY_VAL_LOCAL()
VALUES CURRENT_TIMESTAMP
create table test(tstamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, text varchar(140))
insert into test(text) values('other text');
select * from test
CREATE TABLE USERS(user_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), popularity integer, password varchar(20), pic varchar(50), nickname varchar(20), username varchar(20) unique, stalkers integer)
CREATE TABLE MENTIONS(mentioner INTEGER, mentionee integer)
CREATE TABLE STALKER(stalker INTEGER, stalkee integer)
CREATE TABLE TOPIC(topic varchar(20), mid integer)
CREATE TABLE POSTS(owner integer, mid INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,replyto integer default 0, times_republished int default 0,republish_of int default 0,text varchar(140))
drop table 
insert into USERS(username,password) values('test','123')
select USER_ID from USERS where USERNAME='test' and PASSWORD='123'
alter table POSTS add text varchar(140)
alter table POSTS drop column text
drop table posts

