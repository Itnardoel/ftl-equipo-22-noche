package com.footalentgroup.services;

import com.footalentgroup.models.entities.BookEntity;

public interface BookService {
    BookEntity read(String isbn);
    BookEntity create(BookEntity book);
    BookEntity update(String isbn, BookEntity book);
    void delete(String isbn);
}
