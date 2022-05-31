CREATE SCHEMA IF NOT EXISTS demo AUTHORIZATION postgres;

create table demo.product
(
    id    varchar(36) not null,
    name  varchar(256),
    price float8,
    primary key (id)
);

alter table demo.product replica identity FULL;

insert into demo.product(id, name, price) values (gen_random_uuid (), 'Coca Cola', 10);
insert into demo.product(id, name, price) values (gen_random_uuid (), 'Coca Cola Zero', 12);