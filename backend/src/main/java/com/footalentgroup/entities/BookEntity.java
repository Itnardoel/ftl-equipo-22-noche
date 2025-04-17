package com.footalentgroup.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    public BookEntity() {
    }

    public BookEntity(String isbn, String title, String author, LocalDate published) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public BookEntity(String id, String isbn, String title, String author, LocalDate published) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published = published;
    }
}
