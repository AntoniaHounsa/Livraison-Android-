package com.example.easydelivery.callback;

import com.example.easydelivery.service.entity.AdresseResponse;

public interface AdresseVerificationCallback {
    void onAdresseVerified(boolean isValid, AdresseResponse.Feature.Geometry geometry);

}
