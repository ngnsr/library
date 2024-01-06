CREATE TABLE book (
  id bigserial NOT NULL ,
  name varchar(255) NOT NULL ,
  author varchar(50) NOT NULL ,
  description text ,
  publisher varchar(255) NOT NULL ,
  isbn varchar(25) NOT NULL ,
  release_year int NOT NULL
);