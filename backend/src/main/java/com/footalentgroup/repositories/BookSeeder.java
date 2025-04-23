package com.footalentgroup.repositories;

import com.footalentgroup.models.entities.BookEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;

@Log4j2
@Repository
@Profile("dev")
public class BookSeeder {
    private final BookRepository bookRepository;

    @Autowired
    public BookSeeder(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.deleteAllAndSeedDatabase();
    }

    private void deleteAllAndSeedDatabase() {
        this.deleteAll();
        this.seedDatabase();
    }

    private void deleteAll() {
        log.warn("----- Deleted All Books -----");
        this.bookRepository.deleteAll();
    }

    private void seedDatabase() {
        log.warn("----- Initial Load Books -----");
        BookEntity[] books = {
                new BookEntity("978-0439023528", "Los juegos del hambre", "Suzanne Collins", LocalDate.of(2008, 9, 14)),
                new BookEntity("978-0307743657", "El resplandor", "Stephen King", LocalDate.of(1977, 1, 28)),
                new BookEntity("978-0061120084", "Matar a un ruiseñor", "Harper Lee", LocalDate.of(1960, 7, 11)),
                new BookEntity("978-0747532743", "Harry Potter y la piedra filosofal", "J.K. Rowling", LocalDate.of(1997, 6, 26)),
        };
        this.bookRepository.saveAll(Arrays.asList(books));
    }
}
