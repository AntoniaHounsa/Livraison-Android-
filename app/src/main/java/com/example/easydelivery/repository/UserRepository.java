package com.example.easydelivery.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public Task<AuthResult> login(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<DocumentSnapshot> getUserRole(String userId) {
        return firestore.collection("users").document(userId).get();
    }
}
