package com.capps.myapplication.mainModule.model;

import com.capps.myapplication.mainModule.common.BasicErrorEventCallback;
import com.capps.myapplication.mainModule.common.pojo.Product;
import com.capps.myapplication.mainModule.events.MainEven;
import com.capps.myapplication.mainModule.model.dataAcces.ProdutsEvenListener;
import com.capps.myapplication.mainModule.model.dataAcces.RealtimeDatabase;

import org.greenrobot.eventbus.EventBus;

public class MainInteractorClass implements MainInteractor{

    private RealtimeDatabase mDatabase;

    public MainInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToProducts() {
        mDatabase.subscribeToProducts(new ProdutsEvenListener() {
            @Override
            public void onChildAdded(Product product) {
                post(product, MainEven.SUCCESS_ADD);
            }

            @Override
            public void onChildUpdate(Product product) {
                post(product, MainEven.SUCCESS_UPDATE);
            }

            @Override
            public void onChildRemoved(Product product) {
                post(product, MainEven.SUCCESS_REMOVE);

            }

            @Override
            public void onError(int restM) {
                post(MainEven.ERROR_SERVER, restM);
            }
        });
    }



    @Override
    public void unsubscribeToProducts() {
      mDatabase.unsubscribeToProducts();
    }

    @Override
    public void removeProduct(Product product) {
        mDatabase.removeProduct(product, new BasicErrorEventCallback() {
    @Override
    public void onSuccess() {
        post(MainEven.SUCCESS_REMOVE);
    }

    @Override
    public void onError(int typeEvent, int restMsg) {
         post(typeEvent, restMsg);
    }
});
    }

    private void post(Product product, int typeEvent, int resMsg) {
     MainEven event = new MainEven();
     event.setProduct(product);
     event.setTyoeEvent(typeEvent);
     event.setResMsg(resMsg);
     EventBus.getDefault().post(event);
    }

    private void post(int typeEvent){
        post(null, typeEvent, 0);
    }
    private void post(int typeEvent, int resMsg){
        post(null, typeEvent, 0);
    }

    private void post(Product product, int typeEvent){
       post(product, typeEvent, 0);
    }

}
