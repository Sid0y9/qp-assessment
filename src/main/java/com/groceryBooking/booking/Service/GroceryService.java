package com.groceryBooking.booking.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groceryBooking.booking.Others.ResourceNotFoundException;
import com.groceryBooking.booking.Pojo.GroceryItem;
import com.groceryBooking.booking.Pojo.Order;
import com.groceryBooking.booking.Pojo.Request.OrderItemRequest;
import com.groceryBooking.booking.Repository.GroceryRepository;
import com.groceryBooking.booking.Repository.OrderRepository;
import com.groceryBooking.booking.Repository.OrderItemRequestRepository;

@Service
public class GroceryService {

  @Autowired
  private GroceryRepository groceryRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderItemRequestRepository orderItemRequestRepository;

  public GroceryItem addGroceryItem(GroceryItem groceryItem) {
    return groceryRepository.save(groceryItem);
  }

  public List<GroceryItem> getAllGroceryItems() {
    return groceryRepository.findAll();
  }

  public void deleteGroceryItem(int id) {
    groceryRepository.deleteById(id);
  }

  public GroceryItem updateGroceryItem(int id, GroceryItem groceryItem) {
    GroceryItem existingItem = groceryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("GroceryItem", "id", id));
    existingItem.setName(groceryItem.getName());
    existingItem.setPrice(groceryItem.getPrice());
    existingItem.setQuantity(groceryItem.getQuantity());
    return groceryRepository.save(existingItem);
  }

  public GroceryItem updateInventory(int id, int quantity) {
    GroceryItem existingItem = groceryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("GroceryItem", "id", id));
    existingItem.setQuantity(quantity);
    return groceryRepository.save(existingItem);
  }

  public List<GroceryItem> getAvailableGroceryItems() {
    return groceryRepository.findAll();
  }

  public Order bookItems(List<OrderItemRequest> items) {
    Order order = new Order();
    List<OrderItemRequest> orderItems = new ArrayList<>();

    for (OrderItemRequest itemRequest : items) {
      GroceryItem groceryItem = groceryRepository.findById(itemRequest.getId())
          .orElseThrow(() -> new ResourceNotFoundException("GroceryItem", "id", itemRequest.getId()));

      if (groceryItem.getQuantity() < itemRequest.getQuantity()) {
        throw new RuntimeException("Not enough inventory for item: " + groceryItem.getName());
      }

      groceryItem.setQuantity(groceryItem.getQuantity() - itemRequest.getQuantity());
      groceryRepository.save(groceryItem);

      OrderItemRequest orderItem = new OrderItemRequest();
      orderItem.setGroceryItemId(itemRequest.getId());
      orderItem.setQuantity(itemRequest.getQuantity());
      orderItem.setOrder(order);
      orderItems.add(orderItem);
    }

    order.setItems(orderItems);
    Order savedOrder = orderRepository.save(order);

    for (OrderItemRequest orderItem : orderItems) {
      orderItem.setOrder(savedOrder);
      orderItemRequestRepository.save(orderItem);
    }

    return savedOrder;
  }
}
