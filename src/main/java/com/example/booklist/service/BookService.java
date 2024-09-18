package com.example.booklist.service;

import com.example.booklist.dto.request.BookRequestDto;
import com.example.booklist.dto.response.BookResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(BookRequestDto bookRequest);

    List<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto findById(Long id);

    BookResponseDto updateById(Long id, BookRequestDto createDto);

    void deleteById(Long id);

    List<BookResponseDto> findByParams(String author, String title, String genre);
}
