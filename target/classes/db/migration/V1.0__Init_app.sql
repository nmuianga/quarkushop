-- Enums --
create type cart_status as enum ('NEW', 'CONFIRMED', 'CANCELED');
create type payment_status as enum ('ACCEPTED', 'PENDING', 'REFUSED', 'ERROR');
create type product_status as enum ('AVAILABLE', 'DISCONTINUED');

-- Tables --
create table categories
(
    id                 bigserial   not null,
    name               varchar(50) not null,
    description        text        not null,
    created_date       timestamp   not null,
    last_modified_date timestamp   not null
);

create table customers
(
    id                 bigserial not null,
    first_name         varchar(50),
    last_name          varchar(50),
    email              varchar(100),
    telephone          varchar(30),
    enabled            boolean,
    created_date       timestamp not null,
    last_modified_date timestamp not null
);

create table carts
(
    id                 bigserial not null,
    customer_id        bigint    not null,
    status             cart_status,
    created_date       timestamp not null,
    last_modified_date timestamp not null
);

create table payments
(
    id                 bigserial      not null,
    paypal_payment_id  varchar(50),
    status             payment_status,
    amount             decimal(10, 2) not null,
    created_date       timestamp      not null,
    last_modified_date timestamp      not null
);

create table products
(
    id                 bigserial      not null,
    name               varchar(50)    not null,
    description        text           not null,
    price              decimal(10, 2) not null,
    status             product_status not null,
    sales_counter      integer,
    category_id        bigint,
    created_date       timestamp      not null,
    last_modified_date timestamp      not null
);

-- constraints --
alter table categories
    add constraint pk_categories
        primary key (id);

alter table customers
    add constraint pk_customers
        primary key (id);

alter table carts
    add constraint pk_carts
        primary key (id);

alter table carts
    add constraint fk_cart_customer
        foreign key (customer_id) references customers (id);

alter table payments
    add constraint pk_payments
        primary key (id);

alter table products
    add constraint pk_products
        primary key (id);

alter table products
    add constraint fk_products_category
        foreign key (category_id) references categories (id);