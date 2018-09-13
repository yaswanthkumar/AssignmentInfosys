package com.infosys.myassignment.assignmentinfosys;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infosys.myassignment.assignmentinfosys.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Util.NetworkStatus;
import adapter.ItemAdapter;
import model.Item;
import model.ItemResponse;
import persistence.DBManager;
import persistence.DatabaseHelper;
import persistence.ItemDatabaseHelper;
import rest.Call;
import viewmodel.ItemViewModel;

//main activity
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Call actionCall;
    RecyclerView recycleview;
    SwipeRefreshLayout mswipeRefreshLayout;
    private String errorMessage;
    ArrayList<ItemDatabaseHelper> items;
    List<String> data;
    StringBuilder builder = new StringBuilder();

    //onRefresh of recycler view on pull down
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mswipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mswipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "On refresh", Toast.LENGTH_LONG).show();
                fetchTimelineAsync(0);
            }
        });
    }
    //this is the oncreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding view to binding calss
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));

        //calling service to fetch data
        actionCall = new Call(this, new Call.Delegate() {
            @Override
            public void onSuccess(ItemResponse itemResponse) {
                binding.recycleview.setAdapter(new ItemAdapter(itemResponse));
                getSupportActionBar().setTitle(itemResponse.getTitle());
                //calling the dbmanager to acces data base and getting the data from service and saving that in local storage
                DBManager dbManager;
                dbManager = new DBManager(getApplicationContext());
                dbManager.open();
                dbManager.delete();
                for (int i = 0; i < itemResponse.getRows().size(); i++) {
                    String dbtitle = itemResponse.getRows().get(i).getTitle();
                    String des = itemResponse.getRows().get(i).getDescription();
                    String url = (String) itemResponse.getRows().get(i).getImageHref();
                    dbManager.insert(dbtitle, des, url);
                    Log.v("Yaswanth", dbtitle + des + url);
                }
                dbManager.close();

                binding.userMessage.setVisibility(View.GONE);
                binding.recycleview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Object t) {
                //errorMessage = "Error while fetching data  = " + t.toString();
                Toast.makeText(getApplicationContext(), "App is in offline mode" , Toast.LENGTH_LONG).show();
                //showErrorMessage(errorMessage);
                ItemResponse response= getData();
                getSupportActionBar().setTitle(response.getTitle());
                binding.recycleview.setAdapter(new ItemAdapter(response));
                //Toast.makeText(getApplicationContext(), "data is = " + data, Toast.LENGTH_LONG).show();
                binding.userMessage.setVisibility(View.GONE);
                binding.recycleview.setVisibility(View.VISIBLE);

            }
        });
        //check the network connection and if network exists it will call the service
        if (NetworkStatus.getInstance(this).isOnline()) {
            actionCall.execute();

        } else {
            errorMessage = "Please check your internet connection";
        }
        showErrorMessage(errorMessage);


    }

    //retriving data and set that in response that are binding to view holder
    public ItemResponse getData() {
        items = new ArrayList<ItemDatabaseHelper>();
        DBManager dbManager;
        ItemResponse model= new ItemResponse();
        dbManager = new DBManager(getApplicationContext());
        dbManager.open();
        items = dbManager.readAllItems();
        List<Item.Row> rows= new ArrayList<Item.Row>();
        for (int i = 0; i < items.size(); i++) {
            Item.Row row= new Item.Row();
            row.setTitle(items.get(i).getTitle());
            row.setDescription(items.get(i).getDescription());
            row.setImageHref(items.get(i).getUrl());
            rows.add(row);
        }
        model.setTitle("About Canada");
        model.setRows(rows);
        dbManager.close();
        return model;
    }


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //int orientation = this.getResources().getConfiguration().orientation;
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("Portrait ", "PPPPPPPPPPPPPPPPP");
            Toast.makeText(getApplicationContext(), "YOU CHANGED TO POTRATE", Toast.LENGTH_LONG).show();


        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("Lanscape", "LLLLLLLLLLLLLLLLLLLL");
            Toast.makeText(getApplicationContext(), " YOU CHANGED TO LANDSCAPE", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save current view model state
        //savedInstanceState.putString(QUESTION, mTestViewModel.getQuestion());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }


    public void onRestoreInstantState(Bundle restoreInstnceState) {

        super.onRestoreInstanceState(restoreInstnceState);


    }

    public void showErrorMessage(String errorMessage) {
        if (errorMessage != null && !errorMessage.trim().isEmpty()) {
            binding.userMessage.setText(errorMessage.trim());
            binding.userMessage.setVisibility(View.VISIBLE);
            binding.recycleview.setVisibility(View.GONE);
        }
    }

    public void fetchTimelineAsync(int page) {
        actionCall.execute();
        mswipeRefreshLayout.setRefreshing(false);
    }
}

