package com.footalentgroup.models.entities;

import com.footalentgroup.models.dtos.request.BookRequestDTO;
import com.footalentgroup.models.dtos.response.BookResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Entity
@Getter @Setter @ToString
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String isbn;

    private String title;

    private String author;

    private LocalDate published;

    private Boolean deleted;

    public BookEntity() {
    }

    public BookEntity(String isbn, String title, String author, LocalDate published) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published = published;
        this.deleted = false;
    }

    public BookEntity(String id, String isbn, String title, String author, LocalDate published) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published = published;
        this.deleted = false;
    }

    public BookEntity(BookRequestDTO dto) {
        BeanUtils.copyProperties(dto, this);
    }

    public BookResponseDTO toDTO() {
        BookResponseDTO dto = new BookResponseDTO();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }
}
