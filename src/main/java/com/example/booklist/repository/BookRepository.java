package com.example.booklist.repository;

import com.example.booklist.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE "
            + "(:author IS NULL OR b.author = :author) AND "
            + "(:title IS NULL OR b.title = :title) AND "
            + "(:genre IS NULL OR b.genre = :genre)")
    List<Book> findByParams(@Param("author") String author,
                            @Param("title") String title,
                            @Param("genre") String genre);
}
