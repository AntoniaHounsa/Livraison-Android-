package com.example.easydelivery.service;

import com.example.easydelivery.service.entity.RouteStep;

import org.osmdroid.util.GeoPoint;
import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RouteService {

    private static final String API_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private static final String API_KEY = "5b3ce3597851110001cf624828d7a61eb5154f2980f8bd8470f30168";
    ArrayList<RouteStep> routeSteps = new ArrayList<>();


    public void getRouteInfo(ArrayList<GeoPoint> points, RouteInfoCallback callback) throws JSONException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        for (int i = 0; i < points.size() - 1; i++) {
            GeoPoint startPoint = points.get(i);
            GeoPoint endPoint = points.get(i + 1);

            // Construire le corps de la requête pour cette paire de points
            JSONObject requestJson = new JSONObject();
            JSONArray coordinates = new JSONArray();
            coordinates.put(new JSONArray(new double[]{startPoint.getLongitude(), startPoint.getLatitude()}));
            coordinates.put(new JSONArray(new double[]{endPoint.getLongitude(), endPoint.getLatitude()}));
            requestJson.put("coordinates", coordinates);

            RequestBody body = RequestBody.create(requestJson.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", API_KEY)
                    .build();

            // Envoyer la requête et traiter la réponse
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
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
                        System.out.println(responseData);
                        JSONObject jsonResponse = new JSONObject(responseData);
                        JSONArray routes = jsonResponse.getJSONArray("routes");


                        // Assumer qu'il y a une seule route dans la réponse
                        if (routes.length() > 0) {
                            JSONObject route = routes.getJSONObject(0); // Prendre la première route
                            JSONObject summary = route.getJSONObject("summary");
                            double totalDistance = summary.getDouble("distance") / 1000; // Convertir en kilomètres
                            double totalDuration = summary.getDouble("duration") / 60; // Convertir en minutes
                            routeSteps.add(new RouteStep(totalDistance, totalDuration));
                        }

                        callback.onSuccess(routeSteps);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }
                }

            });
        }
    }

    public interface RouteInfoCallback {
        void onSuccess(ArrayList<RouteStep> routeSteps);
        void onFailure(String errorMessage);
    }

}
