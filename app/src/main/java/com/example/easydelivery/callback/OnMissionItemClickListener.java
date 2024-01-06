package com.example.easydelivery.callback;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public interface OnMissionItemClickListener {
    //TODO : AJOUTER PLUTOT L'ID de la mission ppur la MAJ de son statut au lieu de la position qui est peu utile
    void onMissionItemClick(String missionId, ArrayList<GeoPoint> arrayList);
}
