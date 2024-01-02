package com.example.easydelivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easydelivery.model.Mission;
import com.example.easydelivery.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
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
    ProgressDialog progressDialog;
    Button confirmRouteAssignment;
    Mission mission;
    ArrayList<Order> selectedOrders;

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

        confirmRouteAssignment = findViewById(R.id.confirm_assign_route);
        confirmRouteAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDriverEmail = driverSpinner.getSelectedItem().toString();
                selectedOrders = orderItemAdapter.getSelectedOrders();
                mission = new Mission(selectedDriverEmail,selectedOrders);
                saveMissionInDB(mission);
            }
        });

    }

    private void updateSpinner(List<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(adapter);
    }

    private void EventChangeListener() {
        db.collection("orders")
                .whereEqualTo("isAllocated", false)
                .orderBy("deliveryDate")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing()) progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Order order = dc.getDocument().toObject(Order.class);
                                order.setOrderId(dc.getDocument().getId()); // Set the orderId
                                orderArrayList.add(order);
                            }
                        }
                        orderItemAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing()) progressDialog.dismiss();
                    }
                });
    }


    private void saveMissionInDB(Mission mission){

        db.collection("missions").add(mission)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Itinéraire affecté avec succès", Toast.LENGTH_SHORT).show();
                        updateOrdersStatus();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de l'enregistrement de l'affectation", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateOrdersStatus(){
        // Utilisez un compteur pour suivre le nombre de mises à jour réussies
        int[] updateCount = {0};

        for (Order order : selectedOrders) {
            db.collection("orders").document(order.getOrderId())
                    .update("isAllocated", true)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firestore", "Order updated successfully.");
                        orderArrayList.remove(order);

                        // Incrémentez le compteur
                        updateCount[0]++;

                        // Vérifiez si toutes les mises à jour sont terminées
                        if (updateCount[0] == selectedOrders.size()) {
                            runOnUiThread(() -> {
                                // Notifiez l'adaptateur du changement sur l'UI thread
                                orderItemAdapter.notifyDataSetChanged();
                            });
                        }
                    })
                    .addOnFailureListener(e -> Log.e("Firestore", "Error updating order", e));
        }
    }

}