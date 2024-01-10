package com.example.easydelivery.repository.cart;

import com.example.easydelivery.model.Cart;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class CartRepository implements ICartRepository {
    private FirebaseFirestore db;

    public CartRepository() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public Task<Void> createCartForUser(String userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);

        return db.collection("carts").document(userId).set(cart);
    }
}