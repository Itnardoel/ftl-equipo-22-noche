package com.footalentgroup.services;

import com.footalentgroup.models.dtos.request.BookRequestDTO;
import com.footalentgroup.models.dtos.response.BookResponseDTO;
import org.springframework.data.domain.Page;

public interface BookService {
    Page<BookResponseDTO> readAll(int page, int size, String search);
    BookResponseDTO read(String isbn);
    BookResponseDTO create(BookRequestDTO book);
    BookResponseDTO update(String isbn, BookRequestDTO book);
    void delete(String isbn);
}
