package com.example.easydelivery.repository.product;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProductRepository implements IProductRepository {
    private FirebaseFirestore db;

    public ProductRepository() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public Task<QuerySnapshot> getProducts() {
        return db.collection("products").get();
    }
}
