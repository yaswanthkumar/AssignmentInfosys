package com.infosys.myassignment.assignmentinfosys;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.infosys.myassignment.assignmentinfosys.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.ItemAdapter;
import model.Item;
import model.ItemResponse;
import persistence.DBManager;
import persistence.ItemDatabaseHelper;
import rest.Call;

public class BindingHelper {

    private ActivityMainBinding binding;
    ArrayList<ItemDatabaseHelper> items;

    Context context;
    android.support.v7.app.ActionBar actionBar;
    public Call actionCall;
    MainActivity mainActivityObj;
    private static BindingHelper helperObj;

    private BindingHelper() {

    }

    public void setInstanceDependences(ActivityMainBinding binding, android.support.v7.app.ActionBar actionBar, Context context) {
        this.context = context;
        this.actionBar = actionBar;
        this.binding = binding;
    }

    public static BindingHelper getInstance() {
        if (helperObj == null)
            helperObj = new BindingHelper();

        return helperObj;
    }

    public void displayOnConneted(ItemResponse itemResponse) {
        binding.recycleview.setAdapter(new ItemAdapter(itemResponse));
        actionBar.setTitle(itemResponse.getTitle());
        //calling the dbmanager to acces data base and getting the data from service and saving that in local storage
        DBManager dbManager;
        dbManager = new DBManager(context);
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

    public void getOfflineData() {
        //errorMessage = "Error while fetching data  = " + t.toString();
        Toast.makeText(context, "App is in offline mode", Toast.LENGTH_LONG).show();
        //showErrorMessage(errorMessage);
        ItemResponse response = getData();
        actionBar.setTitle(response.getTitle());
        binding.recycleview.setAdapter(new ItemAdapter(response));


        //Toast.makeText(getApplicationContext(), "data is = " + data, Toast.LENGTH_LONG).show();

        if (response.getRows().size() <= 0) {
            BindingHelper.getInstance().showErrorMessage("No Data Found or No Active Internet Connection");
        } else {
            binding.userMessage.setVisibility(View.GONE);
            binding.recycleview.setVisibility(View.VISIBLE);
        }
    }


    //retriving data and set that in response that are binding to view holder
    public ItemResponse getData() {
        items = new ArrayList<ItemDatabaseHelper>();
        DBManager dbManager;
        ItemResponse model = new ItemResponse();
        dbManager = new DBManager(context);
        dbManager.open();
        items = dbManager.readAllItems();
        List<Item.Row> rows = new ArrayList<Item.Row>();

        for (int i = 0; i < items.size(); i++) {
            Item.Row row = new Item.Row();
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

    public void showErrorMessage(String errorMessage) {
        if (errorMessage != null && !errorMessage.trim().isEmpty()) {
            binding.userMessage.setText(errorMessage.trim());
            binding.userMessage.setVisibility(View.VISIBLE);
            binding.recycleview.setVisibility(View.GONE);
        }
    }


}
