create table product(
    id int auto_increment primary key,
    name varchar(100) not null,
    description varchar(500)  not null
);

create table review(
    id int auto_increment primary key,
    title varchar(255) not null,
    review_text varchar(10000) null,
    created_time timestamp default CURRENT_TIMESTAMP null,
    product_id int not null,
    review_doc_id varchar(100) null
);

create table comment(
    id int auto_increment primary key,
    title varchar(255) not null,
    comment_text varchar(10000) null,
    created_time timestamp default CURRENT_TIMESTAMP null,
    review_id int not null
);
