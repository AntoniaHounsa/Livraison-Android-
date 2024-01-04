package com.example.easydelivery.service;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.easydelivery.callback.AdresseVerificationCallback;
import com.example.easydelivery.service.entity.AdresseResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiGouvService {
    public void verifierAdresse(String adresse, AdresseVerificationCallback adresseVerificationCallback) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api-adresse.data.gouv.fr/search/").newBuilder();
        urlBuilder.addQueryParameter("q", adresse);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                adresseVerificationCallback.onAdresseVerified(false, null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    adresseVerificationCallback.onAdresseVerified(false, null);
                } else {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    AdresseResponse adresseResponse = gson.fromJson(responseData, AdresseResponse.class);

                    boolean isValid = adresseResponse.isAdresseExistante(adresse);
                    if (isValid && !adresseResponse.getFeatures().isEmpty()) {
                        // Supposons que le premier élément est l'adresse correcte
                        AdresseResponse.Feature.Geometry geometry = adresseResponse.getFeatures().get(0).getGeometry();
                        adresseVerificationCallback.onAdresseVerified(true, geometry);
                    } else {
                        adresseVerificationCallback.onAdresseVerified(false, null);
                    }                }
            }
        });
    }

}
