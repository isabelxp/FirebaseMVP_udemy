package com.capps.myapplication.mainModule.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.capps.myapplication.R;
import com.capps.myapplication.mainModule.MainPresenter;
import com.capps.myapplication.mainModule.common.pojo.Product;
import com.capps.myapplication.mainModule.view.adapters.OnitemClickListener;
import com.capps.myapplication.mainModule.view.adapters.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Vibrator;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnitemClickListener, MainView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.contentMain)
    ConstraintLayout contenMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private MainPresenter mPresenter;
    private ProductAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configAdapter();
        configRecyclerView();


        mPresenter = new MainPresenterClass(this);
        mPresenter.onCreate();
    }

    private void configAdapter(){
         mAdapter = new ProductAdapter(new ArrayList<Product>(), this);
    }

    private void configRecyclerView(){
        recyclerView.setAdapter(mAdapter);
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPresenter.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }
    /*
     * MainView
     *
     *
     * */

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void add(Product product) {
         mAdapter.add(product);
    }

    @Override
    public void update(Product product) {
        mAdapter.updete(product);

    }

    @Override
    public void remove(Product product) {
        mAdapter.remove(product);
    }

    @Override
    public void removeFail() {
      Snackbar.make(contenMain, R.string.main_error_remove, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onShowError(int reaMag) {
      Snackbar.make(contenMain, reaMag, Snackbar.LENGTH_LONG).show();
    }


    /*
    * OnitemClickListener
    *
    *
    * */

    @Override
    public void OnitemClick(Product product) {

    }

    @Override
    public void onLongItemClick(final Product product) {

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator!=null){
            vibrator.vibrate(60);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_remove_title)
                .setMessage(R.string.main_dialog_remove_message)
                .setPositiveButton(R.string.main_dialog_remove_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.remove(product);
                    }
                })
             .setNegativeButton(R.string.common_dialog_cancel, null)
             .show();

    }
}