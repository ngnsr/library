package com.ridenrite.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    public Book get(Long id){
        Book test = new Book();
        test.setName("name");
        test.setAuthor("author");
        test.setYear(2000);
        return test;
    }

    public List<Book> getAll(){
        Book test = new Book();
        test.setName("name");
        test.setAuthor("author");
        test.setYear(2000);
        Book test1 = new Book();
        test1.setName("name1");
        test1.setAuthor("author1");
        test1.setYear(2000);
        return List.of(test, test1);
    }

    public Book create(Book book){
        return book;
    }

    public Book update(Book book){
        return book;
    }
    public void delete(Book book){
    }
}
