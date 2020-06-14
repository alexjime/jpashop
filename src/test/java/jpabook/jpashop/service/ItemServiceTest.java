package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    @Test
    public void 상품등록() throws Exception {
        // given
        Item book = new Book();
        book.setName("JPA ORM");

        Item movie = new Movie();
        movie.setName("Stay alive");

        // when
        Long bookId = itemService.saveItem(book);
        Long movieId = itemService.saveItem(movie);
        // then
        assertEquals(book,itemRepository.findOne(bookId));
        assertEquals(movie,itemRepository.findOne(movieId));
    }
}