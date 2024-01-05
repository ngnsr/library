package com.ridenrite.library;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private String description;
    private String publisher;
    private String ISBN;
    private int releaseYear;

    public Book(BookDto dto) {
        name = dto.getName();
        author = dto.getAuthor();
        description = dto.getDescription();
        publisher = dto.getPublisher();
        ISBN = dto.getISBN();
        releaseYear = dto.getReleaseYear();
    }
}
