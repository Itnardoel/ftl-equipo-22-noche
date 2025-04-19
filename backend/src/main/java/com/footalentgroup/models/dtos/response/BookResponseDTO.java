package com.footalentgroup.models.dtos.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponseDTO {
    private String id;
    private String isbn;
    private String title;
    private String author;
    private LocalDate published;
    private Boolean deleted;
}
