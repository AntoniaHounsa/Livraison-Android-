package com.example.easydelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydelivery.model.Mission;

import java.util.ArrayList;

public class MissionItemAdapter extends RecyclerView.Adapter<MissionItemAdapter.MyViewHolder> {

    Context context;
    ArrayList<Mission> missionArrayList;

    public MissionItemAdapter(Context context, ArrayList<Mission> missionArrayList) {
        this.context = context;
        this.missionArrayList = missionArrayList;
    }

    @NonNull
    @Override
    public MissionItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(context).inflate(R.layout.mission_item, parent, false);
         return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionItemAdapter.MyViewHolder holder, int position) {
        Mission mission = missionArrayList.get(position);
        holder.missionNumber.setText(String.valueOf(position+1));

        // Configurez ici le RecyclerView des commandes
        OrderItemAdapter orderAdapter = new OrderItemAdapter(context, mission.getRoute());
        holder.ordersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.ordersRecyclerView.setAdapter(orderAdapter);
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
