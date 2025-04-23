package com.footalentgroup.models.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor @AllArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "El isbn no puede estar vacío")
    private String isbn;

    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotBlank(message = "El autor no puede estar vacío")
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate published;
}
