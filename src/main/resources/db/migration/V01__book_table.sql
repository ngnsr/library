CREATE TABLE book (
  id bigint primary key auto_increment ,
  name varchar(255) NOT NULL ,
  author varchar(255) NOT NULL ,
  description text NOT NULL ,
  publisher varchar(255) NOT NULL ,
  isbn varchar(25) NOT NULL ,
  release_year int NOT NULL
);