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

import com.example.easydelivery.callback.AdresseVerificationCallback;
import com.example.easydelivery.callback.CartUpdateListener;
import com.example.easydelivery.model.Cart;
import com.example.easydelivery.model.Order;
import com.example.easydelivery.model.Product;
import com.example.easydelivery.service.ApiGouvService;
import com.example.easydelivery.service.entity.AdresseResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderDialogFragment extends DialogFragment implements AdresseVerificationCallback {
    private TextInputEditText dateFieldTv, deliveryAdressTv;
    private Button validateOrderButton;
    private ArrayList<Product> products;
    private CartUpdateListener cartUpdateListener;
    private ApiGouvService apiGouvService = new ApiGouvService();
    private  Order order ;


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



                order = new Order(userId, products, convertDateTvToTimeStamp(date), address);
                apiGouvService.verifierAdresse(address, OrderDialogFragment.this);


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
                        if (cartUpdateListener != null) {
                            cartUpdateListener.onCartCleared();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Gérer l'erreur
                    }
                });
    }

    public void setCartUpdateListener(CartUpdateListener receivedCartUpdateListener){
        this.cartUpdateListener = receivedCartUpdateListener;
    }

    @Override
    public void onAdresseVerified(boolean isValid, AdresseResponse.Feature.Geometry geometry) {
        if (getActivity() == null) return;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isValid) {
                    // Ajouter le géocode à l'objet commande
                    order.setGeoCoordinates(new GeoPoint(geometry.getCoordinates().get(1), geometry.getCoordinates().get(0)));
                    saveOrderToFirestore(order);
                } else {
                    Toast.makeText(getActivity(), "Adresse invalide", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Timestamp convertDateTvToTimeStamp(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = formatter.parse(dateString);
            return new com.google.firebase.Timestamp(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Afficher un message d'erreur approprié si vous êtes dans un contexte où cela est possible
            // Sinon, vous pourriez vouloir renvoyer null ou lever une exception personnalisée
        }
        return null; // Renvoie null si le parsing de la date échoue
    }


}
