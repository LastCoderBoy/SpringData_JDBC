package com.JK.databasePostgreSQL.dao.impl;

import com.JK.databasePostgreSQL.TestDataUtil;
import com.JK.databasePostgreSQL.dao.impl.BookDaoImpl;
import com.JK.databasePostgreSQL.domain.Author;
import com.JK.databasePostgreSQL.domain.Book;
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
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testTheBookCreateMethod(){
        Book book = TestDataUtil.createTestBook();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),

                eq("99921-58-10-7"),
                eq("Walk in humble"),
                eq(1L)
        );
    }

    @Test
    public void testTheBookReadMethod(){
        underTest.readOne("99921-58-10-7");

        verify(jdbcTemplate).query(
                eq("SELECT * FROM books WHERE isbn = ?"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("99921-58-10-7")
        );
    }

    @Test
    public void testTheBooksReadAllMethod(){
        underTest.readAll();

        verify(jdbcTemplate).query(
                eq("SELECT * FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void testTheBookUpdateMethod(){
        Book book = TestDataUtil.createTestBook();
        book.setTitle("Academia Ghost");
        underTest.update(book.getIsbn(), book);
    }

    @Test
    public void testTheBookDeleteMethod(){
        Book book = TestDataUtil.createTestBook();

        underTest.delete(book.getIsbn());
        verify(jdbcTemplate).update(
                "DELETE FROM books WHERE isbn=?",
                book.getIsbn());
    }

}
