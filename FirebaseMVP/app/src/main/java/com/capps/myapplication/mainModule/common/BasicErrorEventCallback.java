package com.capps.myapplication.mainModule.common;

public interface BasicErrorEventCallback {
    void onSuccess();
    void onError(int typeEvent, int restMsg);
}
