package com.ridenrite.library;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Book> getAll(String query, Pageable pageable){
        if(query != null){
            return bookRepository.findByQuery("%" + query.toLowerCase() + "%", pageable);
        }
        return bookRepository.findAll(pageable);
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
