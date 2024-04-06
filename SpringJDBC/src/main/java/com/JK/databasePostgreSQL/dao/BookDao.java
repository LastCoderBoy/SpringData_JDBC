package com.JK.databasePostgreSQL.dao;

import com.JK.databasePostgreSQL.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> readOne(String isbn);

    List<Book> readAll();

    void update(String isbn, Book book);

    void delete(String isbn);
}
