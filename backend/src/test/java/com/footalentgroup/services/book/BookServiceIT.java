package com.footalentgroup.services.book;

import com.footalentgroup.TestConfig;
import com.footalentgroup.models.dtos.request.BookRequestDTO;
import com.footalentgroup.models.dtos.response.BookResponseDTO;
import com.footalentgroup.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class BookServiceIT {

    @Autowired
    private BookService bookService;

    @Test
    void testUpdate() {
        BookRequestDTO book = new BookRequestDTO("978-123456789", "George Orwell, 1984", "George Orwell", LocalDate.of(1949, 6, 8));

        BookResponseDTO updatedBook = this.bookService.update("978-123456789", book);

        assertEquals("George Orwell, 1984", updatedBook.getTitle());
        assertEquals("George Orwell", updatedBook.getAuthor());
        assertEquals(LocalDate.of(1949, 6, 8), updatedBook.getPublished());

        // Restore the original state of the book in the database
        book = new BookRequestDTO("978-123456789", "1984", "George Orwell", LocalDate.of(1949, 6, 8));
        this.bookService.update("978-123456789", book);
    }

    @Test
    void testDelete() {
        this.bookService.delete("978-192837465");

        BookResponseDTO book = this.bookService.read("978-192837465");
        assertTrue(book.getDeleted());
    }
}
