package com.example.easydelivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.easydelivery.model.Order;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssignRoute extends AppCompatActivity {
    List<String> emails ;
    Spinner driverSpinner;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<Order> orderArrayList;
    AssignRouteItemsAdapter orderItemAdapter;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_route);

        emails = new ArrayList<>();
        driverSpinner = findViewById(R.id.driverMail);

       db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("role","Chauffeur")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String email = document.getString("email");
                            if (email != null) {
                                emails.add(email);
                            }
                        }
                        // Mettez à jour le Spinner
                        updateSpinner(emails);
                    } else {
                        // Gérer l'erreur
                    }
                });

        // loader to improve user experience
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Récupération des données ...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        orderArrayList = new ArrayList<Order>();

        orderItemAdapter = new AssignRouteItemsAdapter(AssignRoute.this, orderArrayList);

        recyclerView.setAdapter(orderItemAdapter);

        EventChangeListener();


    }

    private void updateSpinner(List<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(adapter);
    }

    private void EventChangeListener(){
        db.collection("orders").orderBy("deliveryDate")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error !=null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("Firesore error", error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                orderArrayList.add(dc.getDocument().toObject(Order.class));
                            }
                            orderItemAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

}