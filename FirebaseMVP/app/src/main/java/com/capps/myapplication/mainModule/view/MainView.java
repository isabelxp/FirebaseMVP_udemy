package com.capps.myapplication.mainModule.view;

import com.capps.myapplication.mainModule.common.pojo.Product;

public interface MainView {
    void showProgress();
    void hideProgress();

    void add(Product product);
    void update(Product product);
    void remove(Product product);

    void removeFail();
    void onShowError(int reaMag);
}
