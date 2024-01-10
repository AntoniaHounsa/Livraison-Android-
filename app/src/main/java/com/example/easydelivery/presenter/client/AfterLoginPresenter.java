package com.example.easydelivery.presenter.client;

import com.example.easydelivery.model.Product;
import com.example.easydelivery.repository.product.IProductRepository;
import com.example.easydelivery.repository.product.ProductRepository;
import com.example.easydelivery.uiContract.IAfterLoginView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class AfterLoginPresenter implements IAfterLoginPresenter {
    private IAfterLoginView view;
    private IProductRepository productRepository;

    public AfterLoginPresenter(IAfterLoginView view) {
        this.view = view;
        this.productRepository = new ProductRepository();
    }

    @Override
    public void loadProducts() {
        view.showProgressDialog();
        productRepository.getProducts().addOnCompleteListener(task -> {
            view.hideProgressDialog();
            if (task.isSuccessful()) {
                ArrayList<Product> products = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    products.add(document.toObject(Product.class));
                }
                view.onProductDataChanged(products);
            } else {
                view.onProductDataError(task.getException().getMessage());
            }
        });
    }
}
