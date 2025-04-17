package com.footalentgroup.repositories;

import com.footalentgroup.repositories.book.BookSeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseSeederService {
    private final BookSeederService bookSeederService;

    @Autowired
    public DatabaseSeederService(BookSeederService bookSeederService) {
        this.bookSeederService = bookSeederService;
        this.seedDatabase();
    }

    public void seedDatabase() {
        this.bookSeederService.seedDatabase();
    }

    public void deleteAll() {
        this.bookSeederService.deleteAll();
    }

    public void reSeedDatabase() {
        this.deleteAll();
        this.seedDatabase();
    }
}
