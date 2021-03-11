package com.capps.myapplication.mainModule;

import com.capps.myapplication.mainModule.common.pojo.Product;
import com.capps.myapplication.mainModule.events.MainEven;

public interface MainPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void remove(Product product);
    void onEventListener(MainEven event);

}
