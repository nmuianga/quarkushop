-- Enums --
create type cart_status as enum ('NEW', 'CONFIRMED', 'CANCELED');
create type payment_status as enum ('ACCEPTED', 'PENDING', 'REFUSED', 'ERROR');
create type product_status as enum ('AVAILABLE', 'DISCONTINUED');
create type order_status as enum ('CREATION', 'PAID');

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

create table orders
(
    id                 bigserial      not null,
    total_price        decimal(10, 2) not null,
    status             order_status   not null,
    shipped            timestamp,
    address_1          varchar(200),
    address_2          varchar(200),
    city               varchar(30),
    postcode           varchar(10)    not null,
    country            varchar(2)     not null,
    payment_id         bigint,
    cart_id            bigint,
    created_date       timestamp      not null,
    last_modified_date timestamp      not null
);

create table order_items
(
    id                 bigserial not null,
    quantity           bigint    not null,
    product_id         bigint,
    order_id           bigint,
    created_date       timestamp not null,
    last_modified_date timestamp not null
);

create table reviews
(
    id                 bigserial    not null,
    title              varchar(100) not null,
    description        text         not null,
    rating             integer      not null,
    created_date       timestamp    not null,
    last_modified_date timestamp    not null
);

create table products_reviews
(
    product_id bigint not null,
    reviews_id bigint not null
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
        foreign key (customer_id)
            references customers (id);

alter table payments
    add constraint pk_payments
        primary key (id);

alter table products
    add constraint pk_products
        primary key (id);

alter table products
    add constraint fk_products_category
        foreign key (category_id)
            references categories (id);

alter table orders
    add constraint pk_orders
        primary key (id);

alter table orders
    add constraint fk_orders_payment
        foreign key (payment_id)
            references payments (id);

alter table orders
    add constraint fk_orders_cart
        foreign key (cart_id)
            references carts (id);

alter table order_items
    add constraint pk_order_items
        primary key (id);

alter table order_items
    add constraint fk_order_items_product
        foreign key (product_id)
            references products (id);

alter table order_items
    add constraint fk_order_items_cart
        foreign key (order_id)
            references orders (id);

alter table reviews
    add constraint pk_reviews
        primary key (id);

alter table products_reviews
    add constraint pk_product_reviews
        primary key (product_id, reviews_id);

alter table products_reviews
    add constraint unq_products_reviews
        unique (reviews_id);

alter table orders
    add constraint unq_payment
        unique (payment_id);