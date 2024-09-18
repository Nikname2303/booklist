package com.example.booklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.booklist.dto.request.BookRequestDto;
import com.example.booklist.dto.response.BookResponseDto;
import com.example.booklist.exception.WrongDateException;
import com.example.booklist.mapper.BookMapper;
import com.example.booklist.model.Book;
import com.example.booklist.repository.BookRepository;
import com.example.booklist.service.impl.BookServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldThrowWrongDateException_WhenPublicationYearIsInFuture() {
        BookRequestDto bookRequest = new BookRequestDto();
        bookRequest.setPublicationYear(LocalDate.now().plusDays(1));

        WrongDateException exception = assertThrows(WrongDateException.class,
                () -> bookService.save(bookRequest));
        assertEquals("The date has not yet occurred, please, check your date",
                exception.getMessage());
    }

    @Test
    void save_ShouldReturnBookResponseDto_WhenSuccessful() {
        BookRequestDto bookRequest = new BookRequestDto();
        bookRequest.setTitle("Dune");
        bookRequest.setAuthor("F.Gerbert");
        bookRequest.setIsbn("9783127323207");
        bookRequest.setPublicationYear(LocalDate.of(1965, Month.AUGUST, 1));
        Book book = new Book();
        BookResponseDto bookResponseDto = new BookResponseDto();

        when(bookMapper.toModel(bookRequest)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        BookResponseDto result = bookService.save(bookRequest);

        assertEquals(bookResponseDto, result);
        verify(bookMapper).toModel(bookRequest);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void findAll_ShouldReturnListOfBookResponseDto() {
        Book book = new Book();
        BookResponseDto bookResponseDto = new BookResponseDto();
        List<Book> books = Collections.singletonList(book);
        Page<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        List<BookResponseDto> result = bookService.findAll(Pageable.unpaged());

        assertEquals(1, result.size());
        assertEquals(bookResponseDto, result.get(0));
        verify(bookRepository).findAll(any(Pageable.class));
    }

    @Test
    void findById_ShouldReturnBookResponseDto_WhenBookExists() {
        Long id = 1L;
        Book book = new Book();
        BookResponseDto bookResponseDto = new BookResponseDto();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        BookResponseDto result = bookService.findById(id);

        assertEquals(bookResponseDto, result);
        verify(bookRepository).findById(id);
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException_WhenBookDoesNotExist() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(id));
        assertEquals("Book with id " + id + "doesn't exist",
                exception.getMessage());
    }

    @Test
    void updateById_ShouldReturnUpdatedBookResponseDto_WhenBookExists() {
        Long id = 1L;
        BookRequestDto bookRequestDto = new BookRequestDto();
        Book book = new Book();
        BookResponseDto bookResponseDto = new BookResponseDto();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toModel(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        BookResponseDto result = bookService.updateById(id, bookRequestDto);

        assertEquals(bookResponseDto, result);
        verify(bookRepository).findById(id);
        verify(bookMapper).toModel(bookRequestDto);
        verify(bookRepository).save(book);
    }

    @Test
    void updateById_ShouldThrowEntityNotFoundException_WhenBookDoesNotExist() {
        Long id = 1L;
        BookRequestDto bookRequestDto = new BookRequestDto();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.updateById(id, bookRequestDto));
        assertEquals("Book with id " + id + "doesn't exist",
                exception.getMessage());
    }

    @Test
    void deleteById_ShouldDeleteBook() {
        Long id = 1L;

        bookService.deleteById(id);

        verify(bookRepository).deleteById(id);
    }

    @Test
    void findByParams_ShouldReturnListOfBookResponseDto() {
        String author = "author";
        String title = "title";
        String genre = "genre";
        Book book = new Book();
        BookResponseDto bookResponseDto = new BookResponseDto();
        List<Book> books = Collections.singletonList(book);

        when(bookRepository.findByParams(author, title, genre)).thenReturn(books);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        List<BookResponseDto> result = bookService.findByParams(author, title, genre);

        assertEquals(1, result.size());
        assertEquals(bookResponseDto, result.get(0));
        verify(bookRepository).findByParams(author, title, genre);
    }
}
