package com.example.easydelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.easydelivery.service.RouteService;
import com.example.easydelivery.service.entity.RouteStep;

import org.json.JSONException;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public class ShowRoute extends AppCompatActivity {
    private MapView map;
    private IMapController mapController;
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
        Polyline line = new Polyline();
        line.setPoints(geoPoints);
        map.getOverlayManager().add(line);

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



        //centrer la carte sur le premier point
        GeoPoint esigelc = new GeoPoint(49.383430, 1.0773341);
        mapController.setCenter(esigelc);


        // Récupérer les GeoPoints et initialiser le service
        RouteService routeService = new RouteService();

                TextView textViewDurations = findViewById(R.id.textViewDurations); // votre TextView

        // Appeler le service et traiter la réponse
        try {
            routeService.getRouteInfo(geoPoints, new RouteService.RouteInfoCallback() {
                @Override
                public void onSuccess(ArrayList<RouteStep> routeSteps) {
                    StringBuilder infoText = new StringBuilder();
                    int stepNumber = 1; // Initialiser le compteur d'étapes

                    for (RouteStep step : routeSteps) {
                        infoText.append("Étape ").append(stepNumber).append(" : ")
                                .append("Durée: ").append(step.getDuration()).append(" min, ")
                                .append("Distance: ").append(step.getDistance()).append(" km\n");
                        stepNumber++; // Incrémenter le compteur pour la prochaine étape
                    }
                    runOnUiThread(() -> textViewDurations.setText(infoText.toString()));
                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(() -> textViewDurations.setText("Erreur: " + errorMessage));
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}