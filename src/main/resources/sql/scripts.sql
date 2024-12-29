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
                          customer_id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
                          name varchar(100) NOT NULL,
                          email varchar(100)  NOT NULL,
                          mobile_number varchar(20) NOT NULL,
                          pwd varchar(500)  NOT NULL,
                          role varchar(100)  NOT NULL,
                          create_dt date default null
);

insert into customer (name,email,mobile_number,pwd,role,create_dt) VALUES ('Happy','happy@example.com','5334122365', '{bcrypt}$2a$12$CIAvuhpGkJnS3BaiQ7Lomu9snma9xE8E4i2dTlsP1bIYbOmQwVn2W', 'admin',GETDATE());

drop  table if exists accounts
CREATE TABLE accounts (
                          account_number int PRIMARY KEY NOT NULL,
                          account_type varchar(100) NOT NULL,
                          branch_address varchar(200) NOT NULL,
                          create_dt date DEFAULT NULL,
                          customer_id int not null,
                          FOREIGN KEY(customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

INSERT INTO accounts (customer_id, account_number, account_type, branch_address, create_dt)
VALUES (1, 1865764534, 'Savings', '123 Main Street, New York', GETDATE());

CREATE TABLE account_transactions (
                                      transaction_id varchar(200) PRIMARY KEY NOT NULL,
                                      transaction_dt date NOT NULL,
                                      transaction_summary varchar(200) NOT NULL,
                                      transaction_type varchar(100) NOT NULL,
                                      transaction_amt int NOT NULL,
                                      closing_balance int NOT NULL,
                                      create_dt date DEFAULT NULL,
                                      account_number int NOT NULL,
                                      customer_id int NOT NULL,
                                      FOREIGN KEY (account_number) REFERENCES accounts (account_number) ON DELETE CASCADE,
                                      FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE NO ACTION
);

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt,
                                  closing_balance, create_dt)
VALUES (NEWID(), 1865764534, 1, DATEADD(DAY, -7, GETDATE()), 'Coffee Shop', 'Withdrawal', 30, 34500, DATEADD(DAY, -7, GETDATE()));

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt,
                                  closing_balance, create_dt)
VALUES (NEWID(), 1865764534, 1, DATEADD(DAY, -6, GETDATE()), 'Uber', 'Withdrawal', 100, 34400, DATEADD(DAY, -6, GETDATE()));

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt,
                                  closing_balance, create_dt)
VALUES (NEWID(), 1865764534, 1, DATEADD(DAY, -5, GETDATE()), 'Self Deposit', 'Deposit', 500, 34900, DATEADD(DAY, -5, GETDATE()));

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt,
                                  closing_balance, create_dt)
VALUES (NEWID(), 1865764534, 1, DATEADD(DAY, -4, GETDATE()), 'Ebay', 'Withdrawal', 600, 34300, DATEADD(DAY, -4, GETDATE()));

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt,
                                  closing_balance, create_dt)
VALUES (NEWID(), 1865764534, 1, DATEADD(DAY, -2, GETDATE()), 'OnlineTransfer', 'Deposit', 700, 35000, DATEADD(DAY, -2, GETDATE()));

INSERT INTO account_transactions (transaction_id, account_number, customer_id, transaction_dt, transaction_summary, transaction_type, transaction_amt,
                                  closing_balance, create_dt)
VALUES (NEWID(), 1865764534, 1, DATEADD(DAY, -1, GETDATE()), 'Amazon.com', 'Withdrawal', 100, 34900, DATEADD(DAY, -1, GETDATE()));


CREATE TABLE loans (
                       loan_number int IDENTITY(1,1) PRIMARY KEY NOT NULL ,
                       customer_id int NOT NULL,
                       start_dt date NOT NULL,
                       loan_type varchar(100) NOT NULL,
                       total_loan int NOT NULL,
                       amount_paid int NOT NULL,
                       outstanding_amount int NOT NULL,
                       create_dt date DEFAULT NULL,
                       CONSTRAINT loan_customer_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES ( 1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES ( 1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES ( 1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

INSERT INTO loans ( customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES ( 1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

CREATE TABLE cards (
                       card_id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
                       card_number varchar(100) NOT NULL,
                       customer_id int NOT NULL,
                       card_type varchar(100) NOT NULL,
                       total_limit int NOT NULL,
                       amount_used int NOT NULL,
                       available_amount int NOT NULL,
                       create_dt date DEFAULT NULL,
                       CONSTRAINT card_customer_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
VALUES ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, GETDATE());

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
VALUES ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, GETDATE());

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
VALUES ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, GETDATE());

CREATE TABLE notice_details (
                                notice_id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
                                notice_summary varchar(200) NOT NULL,
                                notice_details varchar(500) NOT NULL,
                                notic_beg_dt date NOT NULL,
                                notic_end_dt date DEFAULT NULL,
                                create_dt date DEFAULT NULL,
                                update_dt date DEFAULT NULL,
);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately',
        DATEADD(DAY,-30,GETDATE()) , DATEADD(DAY,30,GETDATE()), GETDATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
        DATEADD(DAY,-30,GETDATE()) , DATEADD(DAY,30,GETDATE()), GETDATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
        DATEADD(DAY,-30,GETDATE()) , DATEADD(DAY,30,GETDATE()), GETDATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
        DATEADD(DAY,-30,GETDATE()) , DATEADD(DAY,30,GETDATE()), GETDATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
        DATEADD(DAY,-30,GETDATE()) , DATEADD(DAY,30,GETDATE()), GETDATE(), null);

INSERT INTO notice_details ( notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
        DATEADD(DAY,-30,GETDATE()) , DATEADD(DAY,30,GETDATE()), GETDATE(), null);

create table contact_messages(
                                 contact_id varchar(50) primary key not null,
                                 contact_name varchar(50) not null,
                                 contact_email varchar(100) not null,
                                 [subject] varchar(500) not null,
                                 [message] varchar(2000) not null,
                                 create_dt date default null
)

create table authorities
(
    id int identity(1,1) not null,
    customer_id int not null,
    name varchar(50) not null,
    constraint authorities_ibfk_1 foreign key (customer_id) references customer (customer_id)
)

insert into authorities (customer_id,[name])
values(1,'VIEWACCOUNT')
insert into authorities (customer_id,[name])
values(1,'VIEWCARDS')
insert into authorities (customer_id,[name])
values(1,'VIEWLOANS')
insert into authorities (customer_id,[name])
values(1,'VIEWBALANCE')

delete from authorities

insert into authorities(customer_id,name)
values(1,'ROLE_USER')

insert into authorities(customer_id,name)
values(1,'ROLE_ADMIN')