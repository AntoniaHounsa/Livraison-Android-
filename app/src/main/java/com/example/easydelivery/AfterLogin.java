package com.example.easydelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.app.ProgressDialog;

import com.example.easydelivery.model.Product;
import com.example.easydelivery.presenter.client.AfterLoginPresenter;
import com.example.easydelivery.presenter.client.IAfterLoginPresenter;
import com.example.easydelivery.uiContract.IAfterLoginView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AfterLogin extends AppCompatActivity implements IAfterLoginView {
    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    MyAdapter myAdapter;
    ProgressDialog progressDialog;
    IAfterLoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        // Initialisation de ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Récupération des données ...");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(AfterLogin.this, productArrayList);

        recyclerView.setAdapter(myAdapter);

        presenter = new AfterLoginPresenter(this);
        presenter.loadProducts();

    }

    @Override
    public void onProductDataChanged(ArrayList<Product> products) {
        productArrayList.clear();
        productArrayList.addAll(products);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProductDataError(String error) {
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
