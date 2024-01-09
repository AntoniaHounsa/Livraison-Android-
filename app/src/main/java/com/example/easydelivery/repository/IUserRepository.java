package com.example.easydelivery.repository;

import com.example.easydelivery.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;

public interface IUserRepository {
    Task<AuthResult> createUser(String email, String password);
    Task<Void> saveUser(User user);
     Task<DocumentSnapshot> getUserRole(String userId);
     Task<AuthResult> login(String email, String password);
     String getUserUIDFromFauth();
}
