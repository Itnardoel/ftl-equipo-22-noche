package com.footalentgroup.controllers.book;

import com.footalentgroup.controllers.BookController;
import com.footalentgroup.controllers.RestTestConfig;
import com.footalentgroup.models.dtos.request.BookRequestDTO;
import com.footalentgroup.models.dtos.response.BookResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RestTestConfig
class BookControllerIT {

    @Autowired
    private WebTestClient webClient;

    @Test
    void testReadAll() {
        this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(BookController.BOOKS)
                        .queryParam("page", "")
                        .queryParam("size", "")
                        .queryParam("q", "")
                        .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(3)
                .jsonPath("$.size").isEqualTo(10)
                .jsonPath("$.pageable.pageNumber").isEqualTo(0);
    }

    @Test
    void testReadAllSearch() {
        this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(BookController.BOOKS)
                        .queryParam("page", 0)
                        .queryParam("size", 1)
                        .queryParam("q", "Aldous Huxley")
                        .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.size()").isEqualTo(1)
                .jsonPath("$.content[0].author").isEqualTo("Aldous Huxley");
    }

    @Test
    void testRead() {
        this.webClient
                .get()
                .uri(BookController.BOOKS + BookController.ISBN, "978-123456789")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookResponseDTO.class)
                .value(bookData -> {
                    assertEquals("1984", bookData.getTitle());
                });
    }

    @Test
    void testCreate() {
        BookRequestDTO book = new BookRequestDTO("978-000000001", "Effective Java", "Joshua Bloch", LocalDate.of(2018, 1, 1));

        this.webClient
                .post()
                .uri(BookController.BOOKS)
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookResponseDTO.class)
                .value(Assertions::assertNotNull);
    }

    @Test
    void testCreateConflict() {
        BookRequestDTO book = new BookRequestDTO("978-987654321", "Title", "Author", LocalDate.of(2021, 1, 1));

        this.webClient
                .post()
                .uri(BookController.BOOKS)
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testUpdate() {
        BookRequestDTO book = new BookRequestDTO("978-123456789", "1984, George Orwell", "George Orwell", LocalDate.of(2021, 1, 1));

        this.webClient
                .put()
                .uri(BookController.BOOKS + BookController.ISBN, "978-123456789")
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookResponseDTO.class)
                .value(bookData -> assertEquals("1984, George Orwell", bookData.getTitle()));
    }

    @Test
    void testDelete() {
        this.webClient
                .delete()
                .uri(BookController.BOOKS + BookController.ISBN, "978-192837465")
                .exchange()
                .expectStatus().isOk();
    }
}
