package com.groceryBooking.booking.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groceryBooking.booking.Pojo.GroceryItem;
import com.groceryBooking.booking.Pojo.Order;
import com.groceryBooking.booking.Pojo.Request.InventoryUpdateRequest;
import com.groceryBooking.booking.Pojo.Request.OrderRequest;
import com.groceryBooking.booking.Service.GroceryService;

@RestController
@RequestMapping("/api")
public class GroceryController {

	@Autowired
	private GroceryService groceryService;

	@PostMapping("/admin/grocery-items")
	public ResponseEntity<?> addGroceryItem(@RequestBody GroceryItem groceryItem) {
		try {
			GroceryItem addedItem = groceryService.addGroceryItem(groceryItem);
			return new ResponseEntity<>(new ResponseMessage<>("Grocery item added successfully", addedItem),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage<>("Failed to add grocery item: " + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/admin/grocery-items")
	public ResponseEntity<?> getAllGroceryItems() {
		try {
			List<GroceryItem> items = groceryService.getAllGroceryItems();
			return new ResponseEntity<>(new ResponseMessage<>("Fetched all grocery items successfully", items),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage<>("Failed to fetch grocery items: " + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/admin/grocery-items/{id}")
	public ResponseEntity<?> deleteGroceryItem(@PathVariable int id) {
		try {
			groceryService.deleteGroceryItem(id);
			return new ResponseEntity<>(new ResponseMessage<>("Grocery item deleted successfully", null),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage<>("Failed to delete grocery item: " + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/admin/grocery-items/{id}")
	public ResponseEntity<?> updateGroceryItem(@PathVariable int id, @RequestBody GroceryItem groceryItem) {
		try {
			GroceryItem updatedItem = groceryService.updateGroceryItem(id, groceryItem);
			return new ResponseEntity<>(new ResponseMessage<>("Grocery item updated successfully", updatedItem),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage<>("Failed to update grocery item: " + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/admin/grocery-items/{id}/inventory")
	public ResponseEntity<?> updateInventory(@PathVariable int id, @RequestBody InventoryUpdateRequest request) {
		try {
			GroceryItem updatedItem = groceryService.updateInventory(id, request.getQuantity());
			return new ResponseEntity<>(new ResponseMessage<>("Inventory updated successfully", updatedItem),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage<>("Failed to update inventory: " + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/grocery-items")
	public ResponseEntity<?> getAvailableGroceryItems() {
		try {
			List<GroceryItem> items = groceryService.getAvailableGroceryItems();
			return new ResponseEntity<>(new ResponseMessage<>("Fetched available grocery items successfully", items),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ResponseMessage<>("Failed to fetch available grocery items: " + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/orders")
	public ResponseEntity<?> bookItems(@RequestBody OrderRequest orderRequest) {
		try {
			Order order = groceryService.bookItems(orderRequest.getItems());
			return new ResponseEntity<>(new ResponseMessage<>("Order placed successfully", order), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseMessage<>("Failed to book items: " + e.getMessage(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Custom response class
	private static class ResponseMessage<T> {
		private String message;
		private T data;

		public ResponseMessage(String message, T data) {
			this.message = message;
			this.data = data;
		}

		public String getMessage() {
			return message;
		}

		public T getData() {
			return data;
		}
	}
}
