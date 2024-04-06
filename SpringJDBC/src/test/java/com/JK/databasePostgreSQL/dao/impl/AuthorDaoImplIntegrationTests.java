package com.JK.databasePostgreSQL.dao.impl;

import com.JK.databasePostgreSQL.TestDataUtil;
import com.JK.databasePostgreSQL.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {
    private AuthorDaoImpl underTest;
    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        Optional<Author> result = underTest.readOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.create(author);
        underTest.create(authorB);
        underTest.create(authorC);
        List<Author> result = underTest.readAll();
        assertThat(result).containsExactly(author, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        author.setAge(15);
        underTest.update(author.getId(), author);
        Optional<Author> result = underTest.readOne(author.getId());

        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        underTest.delete(author.getId());

        Optional<Author> result = underTest.readOne(author.getId());
        assertThat(result).isEmpty();
    }
}
