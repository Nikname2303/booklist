package com.example.booklist.mapper;

import com.example.booklist.config.MapperConfig;
import com.example.booklist.dto.request.BookRequestDto;
import com.example.booklist.dto.response.BookResponseDto;
import com.example.booklist.model.Book;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class,
        componentModel = "spring"
)
public interface BookMapper {
    Book toModel(BookRequestDto createDto);

    BookResponseDto toDto(Book book);
}
