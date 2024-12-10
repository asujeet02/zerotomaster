create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled bit not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

IF NOT EXISTS (SELECT 1 FROM users WHERE username = 'SujeetK')
BEGIN
INSERT INTO users (username, password, enabled)
VALUES ('SujeetK', '{noop}zeroToone@12345', '1');
END

IF NOT EXISTS (SELECT 1 FROM authorities WHERE username = 'SujeetK')
BEGIN
INSERT INTO authorities VALUES ('SujeetK','read');
END

IF NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin')
BEGIN
INSERT INTO users VALUES ('admin','{bcrypt}$2a$12$zluYhpmOvA.cJyKXCbm/s.NoL6cSAgmstT44nE4jt7YH3pDBbvVZy','1');
END

IF NOT EXISTS (SELECT 1 FROM authorities WHERE username = 'admin')
BEGIN
INSERT INTO authorities VALUES ('admin','admin');
END

CREATE TABLE customer (
  id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
  email varchar(45)  NOT NULL,
  pwd varchar(200)  NOT NULL,
  role varchar(45)  NOT NULL
);

insert into customer (email,pwd,role) values('happy@example.com','{noop}zeroToone@12345','read')
insert into customer (email,pwd,role) values('admin@example.com','{bcrypt}$2a$12$zluYhpmOvA.cJyKXCbm/s.NoL6cSAgmstT44nE4jt7YH3pDBbvVZy','admin')
