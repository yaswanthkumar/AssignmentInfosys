package adapter;
// Author : Yaswanth
/*
 * This is the adapter class that will used to bind the data to holder that are getting
 * from web service or local database if the net connection is not avalable
 * */

import android.app.Application;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.infosys.myassignment.assignmentinfosys.databinding.RowLayoutBinding;
import com.infosys.myassignment.assignmentinfosys.R;

import java.io.File;
import java.util.List;

import model.Item;
import model.ItemResponse;
import persistence.DBManager;
import viewmodel.ItemViewModel;

//Custom Addapter to create ui
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ItemResponse itemResponse;
    private Context context;
    private List<Item.Row> rows;
    private LayoutInflater layoutInflater;


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());

        }

        //RowLayoutBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.row_layout, parent, false);
        View v = layoutInflater.inflate(R.layout.row_layout, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v);
        return vh;
    }

    //this is onBindViewHolder binding the data to view
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        RowLayoutBinding binding = holder.binding;
        binding.setItem(new ItemViewModel(rows.get(position)));
    }

    //this below method is used the get the count
    @Override
    public int getItemCount() {
        return rows.size();
    }

    public ItemAdapter(ItemResponse itemResponse) {
        this.itemResponse = itemResponse;
        rows = itemResponse.getRows();
    }

    //create viewholder to bind  and unbind view with data
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private RowLayoutBinding binding;


        public ItemViewHolder(View itemView) {


            super(itemView);
            bind();

        }

        /* package */ void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);


            }
        }

        /* package */ void unbind() {
            if (binding != null) {
                binding.unbind(); // Don't forget to unbind
            }
        }

    }
}
