package com.footalentgroup.services.impl;

import com.footalentgroup.models.dtos.request.BookRequestDTO;
import com.footalentgroup.models.dtos.response.BookResponseDTO;
import com.footalentgroup.models.entities.BookEntity;
import com.footalentgroup.repositories.BookRepository;
import com.footalentgroup.services.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Page<BookResponseDTO> readAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> bookPage;

        if (search.trim().isEmpty()) {
            bookPage = this.bookRepository.findAll(pageable);
        } else {
            bookPage = this.bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search, pageable);
        }

        return bookPage.map(BookEntity::toDTO);
    }

    @Override
    public BookResponseDTO read(String isbn) {
        return this.bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book: " + isbn))
                .toDTO();
    }

    @Override
    @Transactional
    public BookResponseDTO create(BookRequestDTO book) {
        this.assertBookNotExist(book.getIsbn());
        return this.bookRepository.save(new BookEntity(book)).toDTO();
    }

    @Override
    @Transactional
    public BookResponseDTO update(String isbn, BookRequestDTO book) {
        BookEntity bookDB = this.bookRepository.findByIsbn(isbn).orElseThrow(() -> new RuntimeException("Book: " + isbn));
        bookDB.setTitle(book.getTitle());
        bookDB.setAuthor(book.getAuthor());
        bookDB.setPublished(book.getPublished());

        return this.bookRepository.save(bookDB).toDTO();
    }

    @Override
    @Transactional
    public void delete(String isbn) {
        BookEntity bookDB = this.bookRepository.findByIsbn(isbn).orElseThrow(() -> new RuntimeException("Book: " + isbn));
        bookDB.setDeleted(true);
        this.bookRepository.save(bookDB);
    }

    private void assertBookNotExist(String isbn) {
        if (this.bookRepository.existsByIsbn(isbn)) {
            throw new RuntimeException("Book exists: " + isbn);
        }
    }
}
