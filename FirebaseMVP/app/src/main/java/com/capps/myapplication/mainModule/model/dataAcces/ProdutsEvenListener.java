package com.capps.myapplication.mainModule.model.dataAcces;

import com.capps.myapplication.mainModule.common.pojo.Product;

public interface ProdutsEvenListener {
    void onChildAdded(Product product);
    void onChildUpdate(Product product);
    void onChildRemoved(Product product);
    void onError(int restM);
}
