package com.ridenrite.library.repository;

import com.ridenrite.library.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Optional<Book> findById(Long id);

    Book save(Book book);

    void deleteById(Long id);

    @Query("select book from Book book " +
            "where lower(book.name) like :query " +
            "or lower(book.author) like :query " +
            "or lower(book.description) like :query " +
            "or lower(book.ISBN) like :query")
    Page<Book> findByQuery(String query, Pageable pageable);
}
