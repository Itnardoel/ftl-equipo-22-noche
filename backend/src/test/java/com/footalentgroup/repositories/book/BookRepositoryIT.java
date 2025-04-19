package com.footalentgroup.repositories.book;

import com.footalentgroup.TestConfig;
import com.footalentgroup.models.entities.BookEntity;
import com.footalentgroup.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class BookRepositoryIT {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testExistsByIsbn() {
        assertTrue(this.bookRepository.existsByIsbn("978-987654321"));
    }

    @Test
    void testFindByTitle() {
        Page<BookEntity> result = this.bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                "Brave New World",
                null,
                PageRequest.of(0, 10)
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("Brave New World", result.getContent().get(0).getTitle());
    }

    @Test
    void testFindByAuthor() {
        Page<BookEntity> result = this.bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                null,
                "Aldous Huxley",
                PageRequest.of(0, 10)
        );

        assertEquals(1, result.getTotalElements());
        assertEquals("Aldous Huxley", result.getContent().get(0).getAuthor());
    }

    @Test
    void testCreateAndRead() {
        assertTrue(this.bookRepository.findByIsbn("978-987654321").isPresent());
        BookEntity book = this.bookRepository.findByIsbn("978-987654321").get();
        assertEquals("Brave New World", book.getTitle());
        assertEquals("Aldous Huxley", book.getAuthor());
        assertEquals(LocalDate.of(1932, 12, 1), book.getPublished());
    }
}
