package com.example.easydelivery;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.easydelivery.model.Order;
import com.example.easydelivery.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderDialogFragment extends DialogFragment {
    private TextInputEditText dateFieldTv, deliveryAdressTv;
    private Button validateOrderButton;
    private ArrayList<Product> products;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_dialog_fragment, container, false);
        // Récupérez le bundle et les produits
        Bundle bundle = getArguments();
        if (bundle != null) {
            products = (ArrayList<Product>) bundle.getSerializable("products");
        }

        deliveryAdressTv = view.findViewById(R.id.adresse);
        dateFieldTv = view.findViewById(R.id.dateField);
        dateFieldTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                String date = dayOfMonth + "/" + monthOfYear + "/" + year;
                                dateFieldTv.setText(date);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        validateOrderButton = view.findViewById(R.id.validateOrder);
        validateOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer l'ID de l'utilisateur
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String date = dateFieldTv.getText().toString();

                String address = deliveryAdressTv.getText().toString();

                Order order = new Order(userId, products, date, address);

                saveOrderToFirestore(order);


            }
        });
        return view;
    }

    private void saveOrderToFirestore(Order order) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Vous pouvez générer un nouvel ID de document ou utiliser l'ID de l'utilisateur
        db.collection("orders").add(order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        clearUserCart(currentUserId);
                        Toast.makeText(getActivity(), "Commande enregistrée avec succès", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Erreur lors de l'enregistrement de la commande", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearUserCart(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Supposer que le panier de l'utilisateur est un document dans la collection 'carts'
        DocumentReference cartRef = db.collection("carts").document(userId);

        // Option 1: Supprimer le document du panier
        cartRef.update("products", new ArrayList<>())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Le panier a été vidé avec succès
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Gérer l'erreur
                    }
                });
    }

}
