package com.groceryBooking.booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groceryBooking.booking.Pojo.GroceryItem;

@Repository
public interface GroceryRepository extends JpaRepository<GroceryItem, Integer> {
}
