package com.footalentgroup.repositories.book;

import com.footalentgroup.models.entities.BookEntity;
import com.footalentgroup.repositories.BookRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class BookSeederService {

    @Autowired
    private BookRepository bookRepository;

    public void seedDatabase() {
        LogManager.getLogger(this.getClass()).warn("----- Book Initial Load -----");

        BookEntity[] books = {
            new BookEntity("978-123456789", "1984", "George Orwell", LocalDate.of(1949, 6, 8)),
            new BookEntity("978-987654321", "Brave New World", "Aldous Huxley", LocalDate.of(1932, 12, 1)),
            new BookEntity("978-192837465", "Fahrenheit 451", "Ray Bradbury", LocalDate.of(1953, 10, 19))
        };

        this.bookRepository.saveAll(Arrays.asList(books));
    }

    public void deleteAll() {
        this.bookRepository.deleteAll();
    }
}
