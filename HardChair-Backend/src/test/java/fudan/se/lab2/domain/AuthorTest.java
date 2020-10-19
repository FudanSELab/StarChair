package fudan.se.lab2.domain;

import fudan.se.lab2.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @Transactional
    void testAuthor() {
        Author author = new Author("author", "123@2.com", "fudan", "area");
        author.setName("name");
        assertThat(author.getName(), is("name"));
        author.setEmail("email");
        assertThat(author.getEmail(), is("email"));
        author.setCompany("company");
        assertThat(author.getCompany(), is("company"));
        author.setArea("area");
        assertThat(author.getArea(), is("area"));
        Article article = new Article();
        author.setArticle1(article);
        assertThat(author.getArticle1(), is(article));
        author.setOrderOfAuthor(1);
        assertThat(author.getOrderOfAuthor(), is(1));
    }

}