package com.azh.emarket.apicontrollers;

import com.azh.emarket.models.Cart;
import com.azh.emarket.models.Checkout;
import com.azh.emarket.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {
    @Autowired
    CartService cartService;
    @GetMapping
    public ResponseEntity<List<Cart>> getCart(HttpServletRequest request){
        List<Cart> carts = cartService.fetchCartCustomer((Integer) request.getAttribute("customerId"));
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> sendToCart(HttpServletRequest request, @RequestBody Map<String, Object> cartMap){
        int customerId = (int) request.getAttribute("customerId");
        int productId = (int) cartMap.get("productId");
        int productAmount = (int) cartMap.get("productAmount");
        Cart cart = cartService.addToCart(customerId, productId, productAmount);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Cart> deleteFromCart(HttpServletRequest request, @RequestBody Map<String, Object> cartMap){
        int customerId = (int) request.getAttribute("customerId");
        int productId = (int) cartMap.get("productId");
        Cart cart = cartService.deleteFromCart(customerId, productId);
        return new ResponseEntity<>(cart, HttpStatus.ACCEPTED);
    }

    @PostMapping("/checkout")
    public ResponseEntity<List<Checkout>> checkoutFromCart(HttpServletRequest request){
        int customerId = (int) request.getAttribute("customerId");
        List<Checkout> checkouts = cartService.checkoutFromCart(customerId);
        return new ResponseEntity<>(checkouts, HttpStatus.ACCEPTED);
    }

    @GetMapping("/checkout")
    public ResponseEntity<List<Checkout>> checkoutList(HttpServletRequest request){
        int customerId = (int) request.getAttribute("customerId");
        List<Checkout> checkouts = cartService.listCheckout(customerId);
        return new ResponseEntity<>(checkouts, HttpStatus.OK);
    }
}
