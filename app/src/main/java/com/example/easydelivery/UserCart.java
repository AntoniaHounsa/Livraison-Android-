package com.example.easydelivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.easydelivery.model.Cart;
import com.example.easydelivery.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserCart extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Product> productArrayList;
    CartItemAdapter cartItemAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);

        // loader to improve user experience
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Récupération des données ...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productArrayList = new ArrayList<Product>();
        cartItemAdapter = new CartItemAdapter(UserCart.this, productArrayList);

        recyclerView.setAdapter(cartItemAdapter);
        // to retrieve data from firestore:
        EventChangeListener();
    }

    private void EventChangeListener() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference cartRef = db.collection("carts").document(currentUserId);

        cartRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("Firestore error:", error.getMessage());
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Suppose que Cart a un constructeur qui accepte une Map<String, Object> ou une méthode pour définir les produits
                    Cart cart = documentSnapshot.toObject(Cart.class);
                    if (cart != null) {
                        // Mettez à jour votre interface utilisateur avec la liste des produits du panier
                        updateUIWithCartProducts(cart.getProducts());
                    }
                } else {
                    Log.d("Cart data", "Current data: null");
                }

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void updateUIWithCartProducts(ArrayList<Product> products) {
        Log.d("UserCart", "Updating UI with products: " + products.size());
        // Mettez à jour l'interface utilisateur ici, par exemple en actualisant un RecyclerView
        //productArrayList.clear();
        productArrayList.addAll(products);
        cartItemAdapter.notifyDataSetChanged();
    }

}