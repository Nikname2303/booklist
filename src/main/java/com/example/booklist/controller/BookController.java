package com.example.booklist.controller;

import com.example.booklist.dto.request.BookRequestDto;
import com.example.booklist.dto.response.BookResponseDto;
import com.example.booklist.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {
    public static final int DEFAULT_PAGE_SIZE = 20;
    private final BookService bookService;

    @GetMapping
    public List<BookResponseDto> getAll(@PageableDefault(size = DEFAULT_PAGE_SIZE)
                                            Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PostMapping
    public BookResponseDto createBook(@RequestBody @Valid BookRequestDto createDto) {
        return bookService.save(createDto);
    }

    @GetMapping("/{id}")
    public BookResponseDto getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    public BookResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid BookRequestDto updateDto) {
        return bookService.updateById(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/search")
    public List<BookResponseDto> getByParams(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {
        return bookService.findByParams(author, title, genre);
    }
}
