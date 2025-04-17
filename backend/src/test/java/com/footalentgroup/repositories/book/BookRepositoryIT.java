package com.footalentgroup.repositories.book;

import com.footalentgroup.TestConfig;
import com.footalentgroup.entities.BookEntity;
import com.footalentgroup.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    void testCreateAndRead() {
        assertTrue(this.bookRepository.findByIsbn("978-987654321").isPresent());
        BookEntity book = this.bookRepository.findByIsbn("978-987654321").get();
        assertEquals("Brave New World", book.getTitle());
        assertEquals("Aldous Huxley", book.getAuthor());
        assertEquals(LocalDate.of(1932, 12, 1), book.getPublished());
    }
}
