package com.example.easydelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydelivery.model.Product;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder>{
    Context context;
    ArrayList<Product> productArrayList;

    public CartItemAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public CartItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_item,parent, false);
        return  new CartItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.quantity.setText(String.valueOf(product.getQuantity()));


        // la méthode setImageResource n'accepote que des int, donc je doit d'abord cherhcé l'id de l'image dans le projet
        int imageResId = context.getResources().getIdentifier(product.getPhoto(), "drawable", context.getPackageName());
        holder.photo.setImageResource(imageResId);

    }

    @Override
    public int getItemCount() { return productArrayList.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, price, quantity;
        ImageView photo;
        NumberPicker numberPicker;
        Button retriveButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvname);
            price = itemView.findViewById(R.id.tvprice);
            photo = itemView.findViewById(R.id.image);
            retriveButton = itemView.findViewById(R.id.addButton);
            quantity = itemView.findViewById(R.id.tvqte);
        }
    }
}
