package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // 처음에 생성한 item이 DB에 들어가기 전이라 id가 없기 때문에 JPA를 이용하여 영속성 컨텍스트에 저장한다.
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item); // 강제로 update. 자세한건 뒤에서 설명
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class,id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i")
                .getResultList();
    }

    public List<Item> findByName(String name) {
        return em.createQuery("select i from Item i where i.name =: name")
                .setParameter("name",name)
                .getResultList();
    }
}
