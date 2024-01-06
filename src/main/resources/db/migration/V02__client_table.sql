CREATE TABLE client (
    id bigserial NOT NULL ,
    first_name varchar(50) NOT NULL ,
    last_name varchar(50) NOT NULL ,
    email varchar(255) NOT NULL,
    phone varchar(15) NOT NULL
);

create unique index client_email_uindex
    on client(email);

create unique index client_phone_uindex
    on client(phone);
