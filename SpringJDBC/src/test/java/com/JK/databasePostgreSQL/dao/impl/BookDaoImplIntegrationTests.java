package com.JK.databasePostgreSQL.dao.impl;

import com.JK.databasePostgreSQL.TestDataUtil;
import com.JK.databasePostgreSQL.dao.AuthorDao;
import com.JK.databasePostgreSQL.domain.Author;
import com.JK.databasePostgreSQL.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private AuthorDao authorDao;
    private BookDaoImpl underTest;

    @Autowired
    public BookDaoImplIntegrationTests(AuthorDao authorDao, BookDaoImpl underTest) {
        this.authorDao = authorDao;
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);
        Book book = TestDataUtil.createTestBook();
        book.setAuthor_id(author.getId());
        underTest.create(book);
        underTest.readOne(book.getIsbn());
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthor_id(author.getId());
        underTest.create(book);

        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthor_id(author.getId());
        underTest.create(bookB);

        Book bookC = TestDataUtil.createTestBookC();
        bookC.setAuthor_id(author.getId());
        underTest.create(bookC);

        List<Book> result = underTest.readAll();

        assertThat(result).containsExactly(book, bookB, bookC);
    }

    @Test
    public void testThatMultipleBooksCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthor_id(author.getId());
        underTest.create(book);

        Book bookC = TestDataUtil.createTestBookC();

        //Updating the book's title to bookC's title...
        book.setTitle(bookC.getTitle());
        underTest.update(book.getIsbn(), book);

        Optional<Book> result = underTest.readOne(book.getIsbn());
        assertThat(result.get()).isEqualTo(book);
    }


    @Test
    public void testThatBooksCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBook();
        book.setAuthor_id(author.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());

        Optional<Book> result = underTest.readOne(book.getIsbn());
        assertThat(result).isEmpty();
    }
}
