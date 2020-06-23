package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class,id);
    }

//    // OrderSearch라는 파라미터를 넘겨줄거임
//    public List<Order> findAll(OrderSearch orderSearch) {
//        // OrderSearch라는 값이 있기 때문에 가능한 정적쿼리
//        String query = "select o from Order o join fetch o.member m" +
//                        " where o.status = :status " +
//                        " and m.name = :name";
//        List<Order> resultList = em.createQuery(query, Order.class)
//                .setParameter("status",orderSearch.getOrderStatus())
//                .setParameter("name",orderSearch.getMemberName())
//                .setMaxResults(1000)
//                .getResultList();
//        return resultList;
//    }

    // 동적쿼리 -> Querydsl을 사용할 예정
    // public List<Order> findAll(OrderSearch orderSearch) {}
}
