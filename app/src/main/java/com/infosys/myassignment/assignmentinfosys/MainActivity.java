package com.infosys.myassignment.assignmentinfosys;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.infosys.myassignment.assignmentinfosys.databinding.ActivityMainBinding;

import Util.NetworkStatus;
import adapter.ItemAdapter;
import model.ItemResponse;
import rest.Call;

//main activity
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Call actionCall;
    RecyclerView recycleview;
    SwipeRefreshLayout mswipeRefreshLayout;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding view to binding calss
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));

        actionCall = new Call(this, new Call.Delegate() {
            @Override
            public void onSuccess(ItemResponse itemResponse) {
                binding.recycleview.setAdapter(new ItemAdapter(itemResponse));
                getSupportActionBar().setTitle(itemResponse.getTitle());
                //binding.recycleview.setAdapter(new ItemAdapter(itemResponse.getTitle()));
            }
            @Override
            public void onFailure(Object t) {

                Toast.makeText(getApplicationContext(), "Error while fetching data  = " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        //check the network connection and if network exists it will call the service
        if (NetworkStatus.getInstance(this).isOnline()) {
            actionCall.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public void fetchTimelineAsync(int page) {
        actionCall.execute();
        mswipeRefreshLayout.setRefreshing(false);
    }
}

