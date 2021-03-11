package com.capps.myapplication.mainModule.model.dataAcces;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.capps.myapplication.R;
import com.capps.myapplication.mainModule.common.BasicErrorEventCallback;
import com.capps.myapplication.mainModule.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.capps.myapplication.mainModule.common.pojo.Product;
import com.capps.myapplication.mainModule.events.MainEven;
import com.capps.myapplication.mainModule.model.dataAcces.ProdutsEvenListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class RealtimeDatabase {
    private static final String PATH_PRODUCTS = "products";

    private FirebaseRealtimeDatabaseAPI mDatabaseApi;
    private ChildEventListener mProductsChildEventListener;

    public RealtimeDatabase() {
        mDatabaseApi = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    private DatabaseReference getProductosReferencia(){
        return mDatabaseApi.getmReference().child(PATH_PRODUCTS);
    }
    public void subscribeToProducts(ProdutsEvenListener listener){
       if(mProductsChildEventListener == null){
           mProductsChildEventListener = new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     listener.onChildAdded(getProduct(dataSnapshot));
               }

               @Override
               public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                     listener.onChildUpdate(getProduct(dataSnapshot));
               }

               @Override
               public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                     listener.onChildRemoved(getProduct(dataSnapshot));
               }

               @Override
               public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                           switch (databaseError.getCode()){
                               case DatabaseError.PERMISSION_DENIED:
                                   listener.onError(R.string.main_error_permission_denied);
                                   break;
                               default:
                                   listener.onError(R.string.main_error_server);
                           }
               }
           };
       }
       getProductosReferencia().addChildEventListener(mProductsChildEventListener);

    }


    private Product getProduct(DataSnapshot dataSnapshot) {
        Product product = dataSnapshot.getValue(Product.class);
        if(product != null){
            product.setId(dataSnapshot.getKey());
        }
        return product;
    }

    public void unsubscribeToProducts(){
        if(mProductsChildEventListener != null){
            getProductosReferencia().removeEventListener(mProductsChildEventListener);
        }
    }

    public void removeProduct(Product product, BasicErrorEventCallback callback){
        getProductosReferencia().child(product.getId())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError == null) {
                            callback.onSuccess();
                        } else {
                            switch (databaseError.getCode()){
                                case DatabaseError.PERMISSION_DENIED:
                                    callback.onError(MainEven.ERROR_TO_REMOVE, R.string.main_error_remove);
                                    break;
                                default:
                                    callback.onError(MainEven.ERROR_SERVER, R.string.main_error_server);
                            }
                        }
                    }
                });
    }
}
