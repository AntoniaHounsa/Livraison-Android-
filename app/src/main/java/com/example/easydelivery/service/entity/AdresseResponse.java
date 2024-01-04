package com.example.easydelivery.service.entity;


import java.util.List;

public class AdresseResponse {
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public boolean isAdresseExistante(String adresseRecherchee) {
        if (features == null || features.isEmpty()) {
            return false;
        }

        String adresseRechercheeNormalisee = adresseRecherchee.toLowerCase().trim();

        for (Feature feature : features) {
            String labelNormalise = feature.getProperties().getLabel().toLowerCase().trim();
            if (labelNormalise.equals(adresseRechercheeNormalisee)) {
                return true;
            }
        }
        return false;
    }

    public static class Feature {
        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        private Geometry geometry;

        public Properties getProperties() {
            return properties;
        }

        private Properties properties;


        public static class Geometry {
            private String type;

            public List<Double> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates) {
                this.coordinates = coordinates;
            }

            private List<Double> coordinates;

        }

        public static class Properties {
            public String getLabel() {
                return label;
            }

            private String label;
            private String housenumber;
            private String street;

        }
    }
}

