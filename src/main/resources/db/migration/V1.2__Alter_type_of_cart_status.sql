alter table carts
    add column status_2 varchar(255);

update carts
set status_2 = status
where 1 = 1;

alter table carts
drop column status;

alter table carts
    rename column status_2 to status;