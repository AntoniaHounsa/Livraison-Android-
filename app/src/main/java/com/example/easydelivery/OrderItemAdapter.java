package com.example.easydelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydelivery.model.Order;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder> {
   Context context;
   ArrayList<Order> orderArrayList;

    public OrderItemAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.delivery_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.MyViewHolder holder, int position) {
        Order order = orderArrayList.get(position);
        //convertir le timestamp en string pour l'afficher
        Timestamp timestamp = order.getDeliveryDate();
        Date date = timestamp.toDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(date);
        holder.deliveryDate.setText(formattedDate);


        holder.deliveryAdress.setText(order.getDeliveryAddress());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView deliveryAdress, deliveryDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryAdress = itemView.findViewById(R.id.deliveryAdress);
            deliveryDate = itemView.findViewById(R.id.deliveryDate);
        }
    }
}
