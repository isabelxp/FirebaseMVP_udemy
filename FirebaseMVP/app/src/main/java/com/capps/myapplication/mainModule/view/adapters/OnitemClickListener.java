package com.capps.myapplication.mainModule.view.adapters;

import com.capps.myapplication.mainModule.common.pojo.Product;

public interface OnitemClickListener {
 void OnitemClick(Product product);
 void onLongItemClick(Product product);
}
