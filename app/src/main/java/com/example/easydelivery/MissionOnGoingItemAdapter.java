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

    public MissionOnGoingItemAdapter(Context context, ArrayList<Mission> missionArrayList) {
        this.context = context;
        this.missionArrayList = missionArrayList;
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


    }
    private void updateMissionIndbwhenACCEPTED(Mission mission){
        // Mettre à jour la base de données Firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("missions").document(mission.getMissionId())
                .update("status", Mission.Status.ACCEPTEE.toString())
                .addOnSuccessListener(aVoid -> {
                    missionArrayList.remove(mission); // Mettre à jour la liste locale
                    notifyDataSetChanged(); // Notifier l'adapter du changement
                    Toast.makeText(context, "Mission acceptée", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Échec de la mise à jour de la mission", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return missionArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView missionNumber;
        RecyclerView ordersRecyclerView;
        Button acceptButton, rejectButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            missionNumber = itemView.findViewById(R.id.missionNumber);
            ordersRecyclerView = itemView.findViewById(R.id.ordersRecyclerView); // ID du RecyclerView des commandes
        }
    }
}
