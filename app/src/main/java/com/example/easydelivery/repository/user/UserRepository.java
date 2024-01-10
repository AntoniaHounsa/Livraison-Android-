package com.example.easydelivery.repository.user;

import com.example.easydelivery.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class UserRepository implements IUserRepository {

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
    @Override
    public Task<AuthResult> createUser(String email, String password) {
        return mAuth.createUserWithEmailAndPassword(email, password);
    }
    @Override
    public Task<Void> saveUser(User user) {
        return firestore.collection("users").document(user.getId()).set(user);
    }

    @Override
    public String getUserUIDFromFauth(){
        return mAuth.getCurrentUser().getUid();
    }

    @Override
    public Task<Void> updateUser(String userId, Map<String, Object> updates) {
        return firestore.collection("users").document(userId).update(updates);
    }

    @Override
    public Task<DocumentSnapshot> getUser(String userId) {
        return firestore.collection("users").document(userId).get();
    }

    @Override
    public Task<Void> updateEmail(String newEmail) {
        return mAuth.getCurrentUser().updateEmail(newEmail);
    }

    @Override
    public Task<Void> updatePassword(String newPassword) {
        return mAuth.getCurrentUser().updatePassword(newPassword);
    }

}
