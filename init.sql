
create user azh with password 'azh';
create database emarket_db with template=DEFAULT owner=azh;
\connect emarket_db;
alter default privileges grant all on tables to azh;
alter default privileges grant all on sequences to azh;

create table seller(
    id INT PRIMARY KEY UNIQUE not null,
    name VARCHAR(128) not null
);
create sequence seller_seq increment 1 start 1;
alter table seller alter column id set default nextval('seller_seq');

create table category(
    id INT PRIMARY KEY UNIQUE not null,
    name VARCHAR(128) not null UNIQUE
);
create sequence category_seq increment 1 start 1;
alter table category alter column id set default nextval('category_seq');

create table payment(
    id INT PRIMARY KEY UNIQUE not null,
    name VARCHAR(128) not null
);
create sequence payment_seq increment 1 start 1;
alter table payment alter column id set default nextval('payment_seq');

create table customer_credentials(
    email VARCHAR(128)PRIMARY KEY UNIQUE not null,
    password TEXT not null
);

create table customer(
    id INT PRIMARY KEY UNIQUE not null,
    name VARCHAR(128) not null,
    email VARCHAR(128) UNIQUE not null
);
create sequence customer_seq increment 1 start 1;
alter table customer alter column id set default nextval('customer_seq');
alter table customer add constraint fk_customer_credentials FOREIGN KEY (email) references customer_credentials(email);

create table product(
    id INT PRIMARY KEY UNIQUE not null,
    name VARCHAR(128) not null,
    price INT not null,
    category_id INT not null,
    seller_id INT not null
);
create sequence product_seq increment 1 start 1;
alter table product alter column id set default nextval('product_seq');
alter table product add constraint fk_seller FOREIGN KEY (seller_id) references seller(id);
alter table product add constraint fk_category FOREIGN KEY (category_id) references category(id);

create table transaction(
    id INT PRIMARY KEY UNIQUE not null,
    timestamp TIMESTAMP not null,
    customer_id INT not null,
    total_pay BIGINT not null,
    payment_id INT not null
);
create sequence transaction_seq increment 1 start 1;
alter table transaction alter column id set default nextval('transaction_seq');
alter table transaction alter column timestamp set default NOW();
alter table transaction add constraint fk_customer FOREIGN KEY (customer_id) references customer(id);
alter table transaction add constraint fk_payment FOREIGN KEY (payment_id) references payment(id);

create table transaction_product(
    transaction_id INT not null,
    product_id INT not null,
    product_amount SMALLINT not null
);
alter table transaction_product add constraint fk_transaction FOREIGN KEY (transaction_id) references transaction(id);
alter table transaction_product add constraint fk_product FOREIGN KEY (product_id) references product(id);

create table cart(
    customer_id INT not null,
    product_id INT not null,
    product_amount SMALLINT not null
);
alter table cart add constraint fk_customer FOREIGN KEY (customer_id) references customer(id);
alter table cart add constraint fk_product FOREIGN KEY (product_id) references product(id);

create table checkout(
    customer_id INT not null,
    product_id INT not null,
    product_amount SMALLINT not null,
    timestamp TIMESTAMP not null
);
alter table checkout alter column timestamp set default NOW();
alter table checkout add constraint fk_customer FOREIGN KEY (customer_id) references customer(id);
alter table checkout add constraint fk_product FOREIGN KEY (product_id) references product(id);



insert into seller(name) values('admin');

insert into payment(name)
values ('gopay'), ('ovo'), ('bni'), ('bri'), ('paypal');

insert into category(name)
values ('laptop'), ('komputer'), ('handphone'), ('headset');

insert into product(name, price, seller_id, category_id)
values ('lenovo legion y530',15000000,1,1), ('acer nitro 5',13500000,1,1), ('apple macBook pro M1',15400000,1,1), ('hp spectre X360',23300000,1,1),
('galax rtx 3050',4100000,1,2), ('msi geforce rtx 4090',31500000,1,2), ('amd ryzen 9 7950x',9860000,1,2), ('intel core i9 13900',10200000,1,2),
('samsung galaxy s22',9490000,1,3), ('iphone 14 pro',17760000,1,3), ('xiomi 12',8499000,1,3), ('oppo reno 8 pro',9999000,1,3),
('audio-technica ath - r70x',3590000,1,4), ('sony wh-1000xm5',5499000,1,4), ('sennheiser hd 450',2199000,1,4), ('jbl tune 660nc',1999000,1,4);

insert into customer_credentials(email, password) values('test@email.com', 'test');

insert into customer(name, email) values('test', 'test@email.com');

insert into transaction(customer_id, total_pay, payment_id) values(1, 18590000, 1);

insert into transaction_product(transaction_id ,product_id, product_amount) values (1, 1, 1), (1, 13, 1);
