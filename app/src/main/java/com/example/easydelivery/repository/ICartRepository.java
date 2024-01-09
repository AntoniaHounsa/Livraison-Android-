package com.example.easydelivery.repository;

import com.google.android.gms.tasks.Task;

public interface ICartRepository {
    Task<Void> createCartForUser(String userId);
}
