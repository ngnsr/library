package com.ridenrite.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class BookDto {
    @NotBlank(message = "is mandatory")
    private String name;
    @NotBlank(message = "is mandatory")
    private String author;
    private String description;

    @NotBlank(message = "is mandatory")
    private String publisher;
    @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$")
    private String ISBN;
    @Range(min=1900, max = 3000)
    private int releaseYear;
}
