package com.capps.myapplication.mainModule.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.capps.myapplication.mainModule.common.pojo.Product;
import com.capps.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;
    private OnitemClickListener listener;
    private Context context;

    public ProductAdapter(List<Product> products, OnitemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Product product = products.get(position);

       holder.setOnClickListener(product, listener);
       holder.tvData.setText(context.getString(R.string.item_product_data, product.getName(),
               product.getName()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context)
                .load(product.getPhotoUrl())
                .apply(options)
                .into(holder.imgPhoto);
    }

    public void add(Product product){
        if(!products.contains(product)){
            products.add(product);
            notifyItemInserted(products.size()-1);
        } else {
            updete(product);
        }
    }

    public void updete(Product product) {
    if(products.contains(product)){
        final int index = products.indexOf(product);
        products.set(index, product);
        notifyItemChanged(index);
      }
    }

    public void remove(Product product){
        if(products.contains(product)){
            final int index = products.indexOf(product);
            products.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View view;

        @BindView(R.id.tvData)
        TextView tvData;
        @BindView(R.id.imgPhoto)
        ImageView imgPhoto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.view=itemView;
        }

        void setOnClickListener(Product product, OnitemClickListener listener) {
          view.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                listener.OnitemClick(product);
              }
          });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(product);
                    return false;
                }
            });
        }
    }
}
