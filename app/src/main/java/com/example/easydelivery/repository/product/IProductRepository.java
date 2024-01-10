package com.example.easydelivery.repository.product;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface IProductRepository {
    Task<QuerySnapshot> getProducts();
}
