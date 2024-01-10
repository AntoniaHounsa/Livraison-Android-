package com.example.easydelivery.uiContract;
import com.example.easydelivery.model.Product;

import java.util.ArrayList;

public interface IAfterLoginView {
    void onProductDataChanged(ArrayList<Product> products);
    void onProductDataError(String error);
    void showProgressDialog();
    void hideProgressDialog();
}
