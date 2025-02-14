package com.groceryBooking.booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groceryBooking.booking.Pojo.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
