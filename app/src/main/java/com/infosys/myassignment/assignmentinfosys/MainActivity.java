package com.infosys.myassignment.assignmentinfosys;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.infosys.myassignment.assignmentinfosys.databinding.ActivityMainBinding;

import adapter.ItemAdapter;
import model.ItemResponse;
import rest.Call;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Call actionCall;
    RecyclerView recycleview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));

        actionCall = new Call(this,  new Call.Delegate() {
            @Override
            public void onSuccess(ItemResponse itemResponse) {
                binding.recycleview.setAdapter(new ItemAdapter(itemResponse));
                getSupportActionBar().setTitle(itemResponse.getTitle());
                //binding.recycleview.setAdapter(new ItemAdapter(itemResponse.getTitle()));
            }
            @Override
            public void onFailure(Object t) {

                Toast.makeText(getApplicationContext(), "Error = "+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        actionCall.execute();
    }
    }

