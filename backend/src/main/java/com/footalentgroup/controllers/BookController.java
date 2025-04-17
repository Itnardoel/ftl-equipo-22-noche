package com.footalentgroup.controllers;

import com.footalentgroup.models.entities.BookEntity;
import com.footalentgroup.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BookController.BOOKS)
@RequiredArgsConstructor
public class BookController {
    public static final String BOOKS = "/poc/books";
    public static final String ISBN = "/{isbn}";

    private final BookService bookService;

    @GetMapping(ISBN)
    public BookEntity read(@PathVariable String isbn) {
        return this.bookService.read(isbn);
    }

    @PostMapping
    public BookEntity create(@RequestBody BookEntity book) {
        return this.bookService.create(book);
    }

    @PutMapping(ISBN)
    public BookEntity update(@PathVariable String isbn, @RequestBody BookEntity book) {
        return this.bookService.update(isbn, book);
    }

    @DeleteMapping(ISBN)
    public void delete(@PathVariable String isbn) {
        this.bookService.delete(isbn);
    }
}
