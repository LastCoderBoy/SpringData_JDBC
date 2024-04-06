package com.JK.databasePostgreSQL.dao.impl;

import com.JK.databasePostgreSQL.dao.AuthorDao;
import com.JK.databasePostgreSQL.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;
    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
                    author.getId(), author.getName(), author.getAge());
    }

    @Override
    public Optional<Author> readOne(Long authorID) {
        List<Author> authorList = jdbcTemplate.query(
                "SELECT * FROM authors WHERE id = ?",
                new AuthorRowMapper(), authorID);
        return authorList.stream().findFirst();
    }

    @Override
    public List<Author> readAll() {
        List<Author> authorList = jdbcTemplate.query(
                "SELECT * FROM authors",
                new AuthorRowMapper());
        return authorList;
    }

    @Override
    public void update(Long id, Author author) {
        jdbcTemplate.update(
                "UPDATE authors SET id=?, name=?, age=? WHERE id=?",
                author.getId(), author.getName(), author.getAge(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE FROM authors WHERE id=?",
                id
        );
    }

    public static class AuthorRowMapper implements RowMapper<Author>{

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return
                    Author.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .age(rs.getInt("age"))
                            .build();
        }
    }
}
