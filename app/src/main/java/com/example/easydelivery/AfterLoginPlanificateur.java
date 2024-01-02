package com.example.easydelivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.easydelivery.model.Order;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AfterLoginPlanificateur extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Order> orderArrayList;
    OrderItemAdapter orderItemAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog ;
    Button assignRouteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_planificateur);

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

        orderItemAdapter = new OrderItemAdapter(AfterLoginPlanificateur.this, orderArrayList);

        recyclerView.setAdapter(orderItemAdapter);

        EventChangeListener();

        assignRouteButton = findViewById(R.id.assignRoute);
        assignRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), AssignRoute.class);
                startActivity(myIntent);
                finish();
            }
        });


    }

    private void EventChangeListener(){
        db.collection("orders")
                .whereEqualTo("isAllocated",false)
                .orderBy("deliveryDate")
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