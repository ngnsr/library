package com.ridenrite.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public List<Book> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.get(id);
    }

    @PostMapping("/book")
    public Book createBook(@RequestBody Book book){
        return bookService.create(book);
    }

    @PutMapping("/book/{id}")
    public Book editBook(@PathVariable Long id, @RequestBody Book book){
        return bookService.update(book);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id, @RequestBody Book book) {
        bookService.delete(book);
    }

}
