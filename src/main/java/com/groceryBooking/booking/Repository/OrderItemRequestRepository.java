package com.groceryBooking.booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groceryBooking.booking.Pojo.Request.OrderItemRequest;

@Repository
public interface OrderItemRequestRepository extends JpaRepository<OrderItemRequest, Integer> {
}
