package com.ridenrite.library.controller;

import com.ridenrite.library.service.BookService;
import com.ridenrite.library.dto.BookDto;
import com.ridenrite.library.entity.Book;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public Page<Book> getAllBooks(@RequestParam Integer page,
                                  @RequestParam Integer size,
                                  @RequestParam(required = false) String query){
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getAll(query, pageable);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.get(id);
    }

    @PostMapping("/book")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody BookDto dto){
        return bookService.create(dto);
    }

    @PutMapping("/book/{id}")
    public Book editBook(@PathVariable Long id, @Valid @RequestBody BookDto dto){
        return bookService.update(id, dto);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
