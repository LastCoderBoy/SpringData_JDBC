package com.JK.databasePostgreSQL.dao.impl;

import com.JK.databasePostgreSQL.TestDataUtil;
import com.JK.databasePostgreSQL.dao.impl.AuthorDaoImpl.AuthorRowMapper;
import com.JK.databasePostgreSQL.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testTheAuthorCREATEMethod(){
        Author author = TestDataUtil.createTestAuthor();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L), eq("Thomas"), eq(20));

    }


    @Test
    public void testTheAuthorREADMethod(){
        underTest.readOne(1L);

        verify(jdbcTemplate).query(
                eq("SELECT * FROM authors WHERE id = ?"),
                ArgumentMatchers.<AuthorRowMapper>any(),
                eq(1L));
    }

    @Test
    public void testThatAuthorReadAllMethod(){
        underTest.readAll();

        verify(jdbcTemplate).query(
                eq("SELECT * FROM authors"),
                ArgumentMatchers.<AuthorRowMapper>any());
    }

    @Test
    public void testTheAuthorUpdateMethod(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.update(author.getId(), author);

        verify(jdbcTemplate).update(
                "UPDATE authors SET id=?, name=?, age=? WHERE id=?",
                1L, "Thomas", 20, author.getId()
        );
    }

    @Test
    public void testTheAuthorDeleteMethod(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.delete(author.getId());

        verify(jdbcTemplate).update(
                "DELETE FROM authors WHERE id=?",
                1L
        );
    }

}
