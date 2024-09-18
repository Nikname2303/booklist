package com.example.booklist.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

@Getter
@Setter
public class BookRequestDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    @ISBN
    private String isbn;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationYear;
    private String genre;
}
