package com.example.easydelivery.service;

import com.example.easydelivery.service.entity.RouteStep;
import org.osmdroid.util.GeoPoint;
import okhttp3.*;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class RouteService {
    private static final String API_KEY = "5b3ce3597851110001cf624828d7a61eb5154f2980f8bd8470f30168";

    public void getRouteInfo(ArrayList<GeoPoint> points, RouteInfoCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        TreeMap<Integer, RouteStep> orderedSteps = new TreeMap<>();

        for (int i = 0; i < points.size() - 1; i++) {
            final int index = i;
            GeoPoint startPoint = points.get(i);
            GeoPoint endPoint = points.get(i + 1);

            String url = "https://api.openrouteservice.org/v2/directions/driving-car"
                    + "?api_key=" + API_KEY
                    + "&start=" + startPoint.getLongitude() + "," + startPoint.getLatitude()
                    + "&end=" + endPoint.getLongitude() + "," + endPoint.getLatitude();

            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure( Call call,  IOException e) {
                    callback.onFailure(e.getMessage());
                }

                @Override
                public void onResponse( Call call,  Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        callback.onFailure("Unexpected code " + response);
                        return;
                    }
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseData);
                        JSONArray features = jsonResponse.getJSONArray("features");
                        JSONObject properties = features.getJSONObject(0).getJSONObject("properties");
                        JSONObject summary = properties.getJSONObject("summary");

                        double distanceInKm = summary.getDouble("distance") / 1000;
                        double durationInMin = summary.getDouble("duration") / 60;

                        synchronized (orderedSteps) {
                            orderedSteps.put(index, new RouteStep(distanceInKm, durationInMin));
                            if (orderedSteps.size() == points.size() - 1) {
                                ArrayList<RouteStep> sortedSteps = new ArrayList<>(orderedSteps.values());
                                callback.onSuccess(sortedSteps);
                            }
                        }
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
