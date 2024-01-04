package com.example.easydelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydelivery.model.Mission;
import com.example.easydelivery.model.Order;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MissionOnGoingItemAdapter extends RecyclerView.Adapter<MissionOnGoingItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<Mission> missionArrayList;
    private OnMissionItemClickListener listener;

    // Constructor
    public MissionOnGoingItemAdapter(Context context, ArrayList<Mission> missionArrayList, OnMissionItemClickListener listener) {
        this.context = context;
        this.missionArrayList = missionArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MissionOnGoingItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mission_ongoing_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionOnGoingItemAdapter.MyViewHolder holder, int position) {
        Mission mission = missionArrayList.get(position);
        holder.missionNumber.setText(String.valueOf(position+1));

        // Configurez ici le RecyclerView des commandes
        OrderItemAdapter orderAdapter = new OrderItemAdapter(context, mission.getRoute());
        holder.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.ordersRecyclerView.setAdapter(orderAdapter);

        // Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMissionItemClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return missionArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView missionNumber;
        RecyclerView ordersRecyclerView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            missionNumber = itemView.findViewById(R.id.missionNumber);
            ordersRecyclerView = itemView.findViewById(R.id.ordersRecyclerView); // ID du RecyclerView des commandes
        }
    }
}
