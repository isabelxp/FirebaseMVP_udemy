package com.capps.myapplication.mainModule.events;

import com.capps.myapplication.mainModule.common.pojo.Product;

public class MainEven {

    public static  final int SUCCESS_ADD = 0;
    public static  final int SUCCESS_UPDATE = 1;
    public static  final int SUCCESS_REMOVE = 2;
    public static  final int ERROR_SERVER = 100;
    public static final int ERROR_TO_REMOVE = 101;

    private Product product;
    private int tyoeEvent;
    private int resMsg;

    public MainEven () { }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTyoeEvent() {
        return tyoeEvent;
    }

    public void setTyoeEvent(int tyoeEvent) {
        this.tyoeEvent = tyoeEvent;
    }

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
}
