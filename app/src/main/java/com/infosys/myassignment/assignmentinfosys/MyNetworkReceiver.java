package com.infosys.myassignment.assignmentinfosys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.infosys.myassignment.assignmentinfosys.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.ItemAdapter;
import model.ItemResponse;
import persistence.DBManager;
import persistence.ItemDatabaseHelper;
import rest.Call;

public class MyNetworkReceiver extends BroadcastReceiver {

    private ActivityMainBinding binding;
    private Call actionCall;

    public MyNetworkReceiver() {
        this.binding = binding;
    }

    public MyNetworkReceiver(ActivityMainBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MY RECEIVER ", "" + intent.getAction());
        if (intent.getAction().equals("com.journaldev.broadcastreceiver.SOME_ACTION")) {
            Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();
        else {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                try {
                    Toast.makeText(context, "Network connected", Toast.LENGTH_LONG).show();
                    //calling service to fetch data
                    actionCall = new Call(context, new Call.Delegate() {
                        @Override
                        public void onSuccess(ItemResponse itemResponse) {
                            BindingHelper.getInstance().displayOnConneted(itemResponse);
                        }

                        @Override
                        public void onFailure(Object t) {
                            BindingHelper.getInstance().getOfflineData();

                        }
                    });
                    if (binding.recycleview.getVisibility() != View.VISIBLE) {
                        actionCall.execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                //Toast.makeText(context, "Network connection changed", Toast.LENGTH_LONG).show();

            }
        }

    }
}
