package com.example.easydelivery.service;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.maps.android.PolyUtil;

import org.osmdroid.util.GeoPoint;
import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItineraryService {
    private static final String API_KEY = "5b3ce3597851110001cf624828d7a61eb5154f2980f8bd8470f30168";

    public void getRouteDetails(ArrayList<GeoPoint> points, RouteDetailsCallback callback) throws JSONException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        JSONArray coordsJsonArray = new JSONArray();
        for (GeoPoint point : points) {
            coordsJsonArray.put(new JSONArray(new double[]{point.getLongitude(), point.getLatitude()}));
        }

        JSONObject payloadJson = new JSONObject();
        try {
            payloadJson.put("coordinates", coordsJsonArray);
        } catch (JSONException e) {
            callback.onFailure("Error constructing JSON payload: " + e.getMessage());
            return;
        }

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(payloadJson.toString(), mediaType);

        Request request = new Request.Builder()
                .url("https://api.openrouteservice.org/v2/directions/driving-car")
                .header("Authorization", API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure("Unexpected code " + response);
                    return;
                }
                try {
                    String responseData = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseData);
                    JSONArray routes = jsonResponse.getJSONArray("routes");

                    if (routes.length() > 0) {
                        JSONObject route = routes.getJSONObject(0);
                        String encodedPolyline = route.getString("geometry"); // Obtention de la chaîne encodée

                        // Utilisation de PolyUtil pour décoder la polyline
                        List<com.google.android.gms.maps.model.LatLng> latLngList = PolyUtil.decode(encodedPolyline);

                        // Conversion des LatLng en GeoPoint
                        List<GeoPoint> routePoints = new ArrayList<>();
                        for (LatLng latLng : latLngList) {
                            routePoints.add(new GeoPoint(latLng.latitude, latLng.longitude));
                        }
                        System.out.print("steps : "+ routePoints);
                        callback.onSuccess(new ArrayList<>(routePoints));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                }
            }

        });
    }


    public interface RouteDetailsCallback {
        void onSuccess(ArrayList<GeoPoint> routePoints);
        void onFailure(String errorMessage);
    }
}
