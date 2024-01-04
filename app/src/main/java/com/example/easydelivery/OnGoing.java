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
import com.example.easydelivery.model.Mission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;


public class OnGoing extends AppCompatActivity  implements OnMissionItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Mission> missionArrayList;
    MissionOnGoingItemAdapter missionItemAdapter;
    FirebaseFirestore db;
    FirebaseAuth dbAuth;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Récupération des données ...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        missionArrayList = new ArrayList<Mission>();
        missionItemAdapter = new MissionOnGoingItemAdapter(OnGoing.this, missionArrayList,this);

        recyclerView.setAdapter(missionItemAdapter);
        EventChangeListener();



    }

    private void EventChangeListener(){
        String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        db.collection("missions")
                .whereEqualTo("driverEmail",userMail)
                .whereEqualTo("status", "ACCEPTEE")
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
                            if(dc.getType() == DocumentChange.Type.ADDED || dc.getType() == DocumentChange.Type.MODIFIED){
                                Mission mission = dc.getDocument().toObject(Mission.class);
                                mission.setMissionId(dc.getDocument().getId()); // Set the missionId
                                missionArrayList.add(mission);
                            }
                            missionItemAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

    @Override
    public void onMissionItemClick(int position) {
        // Handle the click event, start a new Activity
        Intent intent = new Intent(this, ShowRoute.class);
        intent.putExtra("MissionPosition", position); // Pass data
        startActivity(intent);
    }
}