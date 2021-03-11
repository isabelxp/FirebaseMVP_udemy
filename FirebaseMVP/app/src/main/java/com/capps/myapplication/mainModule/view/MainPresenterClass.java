package com.capps.myapplication.mainModule.view;

import com.capps.myapplication.mainModule.MainPresenter;
import com.capps.myapplication.mainModule.common.pojo.Product;
import com.capps.myapplication.mainModule.events.MainEven;
import com.capps.myapplication.mainModule.model.MainInteractor;
import com.capps.myapplication.mainModule.model.MainInteractorClass;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainPresenterClass implements MainPresenter {

    private MainView mView;
    private MainInteractor minInteractor;

    public MainPresenterClass(MainView mView) {
        this.mView = mView;
        this.minInteractor = new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        minInteractor.unsubscribeToProducts();
    }

    @Override
    public void onResume() {
        minInteractor.subscribeToProducts();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void remove(Product product) {
        if(setProgress()){
            minInteractor.removeProduct(product);
        }
    }

    private boolean setProgress() {
        if(mView != null){
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(MainEven event) {
        if(mView != null){
            mView.hideProgress();
            switch (event.getTyoeEvent()) {
                case MainEven.SUCCESS_ADD:
                    mView.add(event.getProduct());
                    break;
                case MainEven.SUCCESS_UPDATE:
                    mView.update(event.getProduct());
                    break;
                case MainEven.SUCCESS_REMOVE:
                    mView.remove(event.getProduct());
                    break;
                case MainEven.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());
                    break;
                case MainEven.ERROR_TO_REMOVE:
                    mView.removeFail();
                    break;
            }
        }

    }
}
