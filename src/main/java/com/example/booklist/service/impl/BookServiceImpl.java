package com.example.booklist.service.impl;

import com.example.booklist.dto.request.BookRequestDto;
import com.example.booklist.dto.response.BookResponseDto;
import com.example.booklist.exception.WrongDateException;
import com.example.booklist.mapper.BookMapper;
import com.example.booklist.model.Book;
import com.example.booklist.repository.BookRepository;
import com.example.booklist.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponseDto save(BookRequestDto bookRequest) {
        if (bookRequest.getPublicationYear().isAfter(LocalDate.now())) {
            throw new WrongDateException("The date has not yet occurred, please, check your date");
        }
        Book savedBook;
        try {
            savedBook = bookRepository.save(bookMapper.toModel(bookRequest));
        } catch (DataIntegrityViolationException e) {
            throw new WrongDateException("Book with this ISBN "
                    + bookRequest.getIsbn()
                    + " already exists");
        }

        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookResponseDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + id + "doesn't exist")
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookResponseDto updateById(Long id, BookRequestDto createDto) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Book with id " + id + "doesn't exist");
        }
        Book book = bookMapper.toModel(createDto);
        book.setId(id);
        Book updatedBook;
        try {
            updatedBook = bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    "Error occurred while updating the book with id " + id);
        }
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDto> findByParams(String author, String title, String genre) {
        return bookRepository.findByParams(author, title, genre).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
