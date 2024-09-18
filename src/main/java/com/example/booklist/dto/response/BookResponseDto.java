package com.example.booklist.dto.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Date publicationYear;
    private String genre;
}
