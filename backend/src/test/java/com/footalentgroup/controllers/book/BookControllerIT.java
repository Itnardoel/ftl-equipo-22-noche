package com.footalentgroup.controllers.book;

import com.footalentgroup.controllers.BookController;
import com.footalentgroup.controllers.RestTestConfig;
import com.footalentgroup.entities.BookEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RestTestConfig
class BookControllerIT {

    @Autowired
    private WebTestClient webClient;

    @Test
    @Order(1)
    void testRead() {
        this.webClient
                .get()
                .uri(BookController.BOOKS + BookController.ISBN, "978-123456789")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookEntity.class)
                .value(bookData -> assertEquals("1984", bookData.getTitle()));
    }

    @Test
    @Order(2)
    void testCreate() {
        BookEntity book = new BookEntity("978-000000001", "Effective Java", "Joshua Bloch", LocalDate.of(2018, 1, 1));

        this.webClient
                .post()
                .uri(BookController.BOOKS)
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookEntity.class)
                .value(Assertions::assertNotNull);
    }

    @Test
    @Order(3)
    void testCreateConflict() {
        BookEntity book = new BookEntity("978-987654321", "Title", "Author", LocalDate.of(2021, 1, 1));

        this.webClient
                .post()
                .uri(BookController.BOOKS)
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @Order(4)
    void testDelete() {
        this.webClient
                .delete()
                .uri(BookController.BOOKS + BookController.ISBN, "978-192837465")
                .exchange()
                .expectStatus().isOk();
    }
}
