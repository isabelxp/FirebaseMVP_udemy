package com.capps.myapplication.mainModule.model;

import com.capps.myapplication.mainModule.common.pojo.Product;

public interface MainInteractor {
    void subscribeToProducts();
    void unsubscribeToProducts();
    void removeProduct(Product product);
}
