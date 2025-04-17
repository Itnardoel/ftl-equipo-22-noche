package com.footalentgroup.services.impl;

import com.footalentgroup.entities.BookEntity;
import com.footalentgroup.repositories.BookRepository;
import com.footalentgroup.services.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookEntity read(String isbn) {
        return this.bookRepository.findByIsbn(isbn).orElseThrow(() -> new RuntimeException("Book: " + isbn));
    }

    @Override
    @Transactional
    public BookEntity create(BookEntity book) {
        this.assertBookNotExist(book.getIsbn());
        return this.bookRepository.save(book);
    }

    @Override
    @Transactional
    public BookEntity update(String isbn, BookEntity book) {
        BookEntity bookDB = this.bookRepository.findByIsbn(isbn).orElseThrow(() -> new RuntimeException("Book: " + isbn));
        bookDB.setTitle(book.getTitle());
        bookDB.setAuthor(book.getAuthor());
        bookDB.setPublished(book.getPublished());

        return this.bookRepository.save(bookDB);
    }

    @Override
    @Transactional
    public void delete(String isbn) {
        this.bookRepository.deleteByIsbn(isbn);
    }

    private void assertBookNotExist(String isbn) {
        if (this.bookRepository.existsByIsbn(isbn)) {
            throw new RuntimeException("Book exists: " + isbn);
        }
    }
}
