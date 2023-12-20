package com.example.easydelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydelivery.model.Cart;
import com.example.easydelivery.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Product> productArrayList;

    public MyAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));

        // la méthode setImageResource n'accepote que des int, donc je doit d'abord cherhcé l'id de l'image dans le projet
        int imageResId = context.getResources().getIdentifier(product.getPhoto(), "drawable", context.getPackageName());
        holder.photo.setImageResource(imageResId);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product selectedProduct = productArrayList.get(position);
                int productQuantity = holder.numberPicker.getValue();
                selectedProduct.setQuantity(productQuantity);

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                addToCart(selectedProduct, uid);
            }
        });

    }

    private void addToCart(Product product, String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference cartRef = db.collection("carts").document(userId);

        cartRef.get().addOnSuccessListener(documentSnapshot -> {
            Cart cart;
            if(documentSnapshot.exists()) {
                cart = documentSnapshot.toObject(Cart.class);
                if (cart.getProducts() == null) {
                    cart.setProducts(new ArrayList<>());
                }

                // Vérifier si le produit existe déjà dans le panier
                boolean productExists = false;
                for (Product p : cart.getProducts()) {
                    if (p.getName().equals(product.getName())) {
                        // Si le produit existe, incrémenter la quantité
                        long newQuantity = p.getQuantity() + product.getQuantity();
                        p.setQuantity(newQuantity);
                        productExists = true;
                        break;
                    }
                }

                if (!productExists) {
                    // Si le produit n'existe pas, le configurer et l'ajouter au panier

                    cart.getProducts().add(product);
                }

            }
            else {
                cart = new Cart();
                cart.setUserId(userId);

                ArrayList<Product> newProducts = new ArrayList<>();
                newProducts.add(product);
                cart.setProducts(newProducts); // Ajouter le produit dans le panier
            }

            // Sauvegarder le panier mis à jour dans Firestore
            cartRef.set(cart)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Erreur lors de l'ajout au panier", Toast.LENGTH_SHORT).show();
                    });
        }).addOnFailureListener(e -> {
            // Gérer l'échec
            Toast.makeText(context, "Erreur lors de l'accès au panier", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, price;
        ImageView photo;
        NumberPicker numberPicker;
        Button addButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvname);
            price = itemView.findViewById(R.id.tvprice);
            photo = itemView.findViewById(R.id.image);
            addButton = itemView.findViewById(R.id.addButton);


            numberPicker = (NumberPicker) itemView.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(0); // valeur minimum
            numberPicker.setMaxValue(20); // valeur maximum
            numberPicker.setValue(1); // valeur initiale
        }
    }
}
