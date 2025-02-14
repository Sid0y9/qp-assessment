package com.groceryBooking.booking.Pojo.Request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.groceryBooking.booking.Pojo.Order;

@Entity
@Table(name = "order_item_request") 
public class OrderItemRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int groceryItemId;
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	@JsonBackReference
	private Order order;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroceryItemId() {
		return groceryItemId;
	}

	public void setGroceryItemId(int groceryItemId) {
		this.groceryItemId = groceryItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
