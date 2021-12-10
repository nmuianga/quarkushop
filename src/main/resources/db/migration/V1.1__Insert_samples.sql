insert into categories values (1, 'Mobile', 'Phones & Smartphones', current_timestamp, current_timestamp);
insert into categories values (2, 'PC', 'Computers and Laptops', current_timestamp, current_timestamp);
insert into categories values (3, 'Clothing', 'Men and Women', current_timestamp, current_timestamp);

insert into products values (1, 'iPhone 11 Pro', 'The latest powerful iPhone from Apple',  999.00, 'AVAILABLE', 0, 1, current_timestamp, current_timestamp);
insert into products values (2, 'iPhone XS', 'The most powerful iPhone from Apple',  759.00, 'AVAILABLE', 0, 1, current_timestamp, current_timestamp);
insert into products values (3, 'MacBook Pro 13', 'The most powerful MacBook from Apple',  1999.00, 'AVAILABLE', 0, 2, current_timestamp, current_timestamp);
insert into products values (4, 'Google Pixel 4', 'The phone that gets it done!',  450.00, 'AVAILABLE', 0, 1, current_timestamp, current_timestamp);

insert into reviews values (1, 'Good but not perfect', 'I like the product but I found that it''s not perfect', 4, current_timestamp, current_timestamp);
insert into reviews values (2, 'Excellent', 'Wonderful product', 5, current_timestamp, current_timestamp);
insert into reviews values (3, 'Good but very expensive', 'I like the product but not the price'',', 4, current_timestamp, current_timestamp);

insert into products_reviews values (1,1);
insert into products_reviews values (1,2);
insert into products_reviews values (2,3);

insert into customers values (1, 'Nilvandro', 'Muianga', 'nilvandro@muianga.co.mz', '875263419', TRUE, current_timestamp, current_timestamp);
insert into customers values (2, 'Alice', 'Macie', 'alice@macie.co.mz', '852147635', FALSE, current_timestamp, current_timestamp);
insert into customers values (3, 'Yuran', 'Muianga', 'yuran@muianga.co.mz', '823024876', TRUE, current_timestamp, current_timestamp);
insert into customers values (4, 'Clayton', 'Muianga', 'clayton@muianga.co.mz', '856024783', FALSE, current_timestamp, current_timestamp);
insert into customers values (5, 'Boaventura', 'Muianga', 'boaventura@muianga.co.mz', '856024783', TRUE, current_timestamp, current_timestamp);

insert into carts values (1, 1, 'NEW', current_timestamp, current_timestamp);
insert into carts values (2, 2, 'NEW', current_timestamp, current_timestamp);
insert into carts values (3, 3, 'NEW', current_timestamp, current_timestamp);
insert into carts values (4, 4, 'NEW', current_timestamp, current_timestamp);
insert into carts values (5, 5, 'NEW', current_timestamp, current_timestamp);

insert into payments values (1, 'somePaymentId', 'ACCEPTED', 999.00, current_timestamp, current_timestamp);
insert into payments values (2, 'paymentId', 'ACCEPTED', 759.00, current_timestamp, current_timestamp);
insert into payments values (3, 'paymentId', 'ACCEPTED', 759.00, current_timestamp, current_timestamp);

insert into orders values (1, 500.00, 'PAID', NULL, '170 Habel Jafar Marracuene', NULL, 'Maputo', '1110', 'MZ', 1, 1, current_timestamp, current_timestamp);
insert into orders values (2, 320.00, 'PAID', NULL, '30 Malhangalene Maputo', NULL, 'Maputo', '1110', 'MZ', 2, 2, current_timestamp, current_timestamp);
insert into orders values (3, 500.00, 'PAID', NULL, '15 Ramalde', NULL, 'Porto', '120', 'PT', 3, 3, current_timestamp, current_timestamp);
insert into orders values (4, 999.00, 'CREATION', NULL, '52 Brooklyn', NULL, 'New York', '718', 'US', NULL, 4, current_timestamp, current_timestamp);
insert into orders values (5, 530.00, 'CREATION', NULL, '45 Detroit', NULL, 'Detroit', '313', 'US', NULL, 5, current_timestamp, current_timestamp);

insert into order_items values (1, 2, 1, 1, current_timestamp, current_timestamp);
insert into order_items values (2, 1, 2, 2, current_timestamp, current_timestamp);
insert into order_items values (3, 1, 4, 5, current_timestamp, current_timestamp);