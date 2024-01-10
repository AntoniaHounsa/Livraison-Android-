package com.example.easydelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easydelivery.service.ItineraryService;
import com.example.easydelivery.service.RouteService;
import com.example.easydelivery.service.entity.RouteStep;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.io.IOException;
import java.util.ArrayList;

public class ShowRoute extends AppCompatActivity {
    private MapView map;
    private IMapController mapController;
    Button finishMission;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);

        // Initialize the configuration
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(14.0);

        // Récupérer la liste des GeoPoints de l'intent
        ArrayList<GeoPoint> geoPoints = (ArrayList<GeoPoint>) getIntent().getSerializableExtra("adresseGeocodeList");
        geoPoints.add(geoPoints.get(0));

        // Créer et ajouter la polyline à la carte
       /* Polyline line = new Polyline();
        line.setPoints(geoPoints);
        map.getOverlayManager().add(line);*/

        // Ajouter un marqueur spécial pour le premier point (entrepôt)
        Marker startMarker = new Marker(map);
        startMarker.setPosition(geoPoints.get(0));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_warehouse));
        startMarker.setTitle("Départ (entrepot)");
        map.getOverlays().add(startMarker);



        // Ajouter des marqueurs pour les autres points (lieux de livraison)
        for (int i = 1; i < (geoPoints.size()-1); i++) {
            Marker deliveryMarker = new Marker(map);
            deliveryMarker.setPosition(geoPoints.get(i));
            deliveryMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            deliveryMarker.setIcon(getResources().getDrawable(R.drawable.ic_delivery));
            deliveryMarker.setTitle("Livraison "+ i);
            map.getOverlays().add(deliveryMarker);
        }

        // show route steps



        ItineraryService itineraryService = new ItineraryService();

        try {
            itineraryService.getRouteDetails(geoPoints, new ItineraryService.RouteDetailsCallback() {
                @Override
                public void onSuccess(ArrayList<GeoPoint> routePoints) {
                    runOnUiThread(() -> {
                        Polyline line = new Polyline();
                        line.setPoints(routePoints);
                        map.getOverlayManager().add(line);
                        map.invalidate();
                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        //centrer la carte sur le premier point
        GeoPoint esigelc = new GeoPoint(49.383430, 1.0773341);
        mapController.setCenter(esigelc);



        // Récupérer les GeoPoints et initialiser le service
        RouteService routeService = new RouteService();
        TextView textViewDurations = findViewById(R.id.textViewDurations); // votre TextView

        // Appeler le service et traiter la réponse
        routeService.getRouteInfo(geoPoints, new RouteService.RouteInfoCallback() {
            @Override
            public void onSuccess(ArrayList<RouteStep> routeSteps) {
                StringBuilder infoText = new StringBuilder();

                for (int i=0; i< routeSteps.size(); i++) {
                    infoText.append("Étape ").append(i+1).append(" : ")
                            .append("Durée: ").append(routeSteps.get(i).getDuration()).append(" min, ")
                            .append("Distance: ").append(routeSteps.get(i).getDistance()).append(" km\n");
                }
                runOnUiThread(() -> textViewDurations.setText(infoText.toString()));
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> textViewDurations.setText("Erreur: " + errorMessage));
            }
        });

        finishMission = findViewById(R.id.finishMission);
        String missionId = getIntent().getStringExtra("missionId");
        db = FirebaseFirestore.getInstance();
        finishMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("missions").document(missionId)
                        .update("status", "TERMINE")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Mission terminée avec succès", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), OnGoing.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
