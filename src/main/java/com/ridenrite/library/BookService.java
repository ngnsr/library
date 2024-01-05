package com.ridenrite.library;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book get(Long id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            return book.get();
        }
        throw new RuntimeException("Book not found");
    }

    public List<Book> getAll(){
        Iterator<Book> iterator = bookRepository.findAll().iterator();
        List<Book> books = new ArrayList<>();
        while(iterator.hasNext()) {
            books.add(iterator.next());
        }
        return books;
    }

    public Book create(Book book){
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book){
        Book original = get(id);
        original.setName(book.getName());
        original.setAuthor(book.getAuthor());
        original.setDescription(book.getDescription());
        original.setISBN(book.getISBN());
        original.setPublisher(book.getPublisher());
        original.setReleaseYear(book.getReleaseYear());

        return bookRepository.save(original);
    }
    public void delete(Long id){
        bookRepository.deleteById(id);
    }
}
