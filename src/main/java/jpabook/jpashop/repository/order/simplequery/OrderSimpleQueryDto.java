package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

// API 스펙이 이렇게 협의가 된 상태라고 가정
@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name; // 주문자 이름
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address; // 배송지

    // DTO가 Entity를 파라미터로 받는것은 문제가 되지않음
    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}