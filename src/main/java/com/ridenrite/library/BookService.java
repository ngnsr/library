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
        throw new NotFoundException("Book not found");
    }

    public Page<Book> getAll(String query, Pageable pageable){
        if(query != null){
            return bookRepository.findByQuery("%" + query.toLowerCase() + "%", pageable);
        }
        return bookRepository.findAll(pageable);
    }

    public Book create(BookDto dto){
        Book book = new Book(dto);

        return bookRepository.save(book);
    }

    public Book update(Long id, BookDto dto){
        Book original = get(id);
        original.setName(dto.getName());
        original.setAuthor(dto.getAuthor());
        original.setDescription(dto.getDescription());
        original.setISBN(dto.getISBN());
        original.setPublisher(dto.getPublisher());
        original.setReleaseYear(dto.getReleaseYear());

        return bookRepository.save(original);
    }
    public void delete(Long id){
        bookRepository.deleteById(id);
    }
}
