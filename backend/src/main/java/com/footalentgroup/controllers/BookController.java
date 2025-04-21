package com.footalentgroup.controllers;

import com.footalentgroup.models.dtos.request.BookRequestDTO;
import com.footalentgroup.models.dtos.response.BookResponseDTO;
import com.footalentgroup.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BookController.BOOKS)
@RequiredArgsConstructor
public class BookController {
    public static final String BOOKS = "/poc/books";
    public static final String ISBN = "/{isbn}";

    private final BookService bookService;

    @GetMapping
    public Page<BookResponseDTO> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String q
    ) {
        return this.bookService.readAll(page, size, q);
    }

    @GetMapping(ISBN)
    public BookResponseDTO read(@PathVariable String isbn) {
        return this.bookService.read(isbn);
    }

    @PostMapping
    public BookResponseDTO create(@Valid @RequestBody BookRequestDTO book) {
        return this.bookService.create(book);
    }

    @PutMapping(ISBN)
    public BookResponseDTO update(@PathVariable String isbn, @Valid @RequestBody BookRequestDTO book) {
        return this.bookService.update(isbn, book);
    }

    @DeleteMapping(ISBN)
    public void delete(@PathVariable String isbn) {
        this.bookService.delete(isbn);
    }
}
