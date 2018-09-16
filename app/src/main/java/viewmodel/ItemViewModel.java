package viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.infosys.myassignment.assignmentinfosys.R;

import java.util.Observable;

import model.Item;
import model.ItemResponse;
import persistence.DBManager;


// this is the view model calls this act as the main responsible class to interact with view  class--->viewModel class--->modal class
public class ItemViewModel extends BaseObservable {

    //viewmodel class is used to get the data and bind to view it act as communicator for both view and model classes
    private String title;
    private String description;
    private String imageUri;
    private Item.Row row;



    public ItemViewModel(Item.Row row) {
        this.row = row;

    }

    public ItemViewModel() {

    }

    public String getMessage() {
        return "Sorry an Error occured";
    }

    public String getTitle() {
        title = row.getTitle() == null ? "" : row.getTitle().toString();
        return title;
    }

    public String getDescription() {
        description = row.getDescription() == null ? "" : row.getDescription().toString();
        return description;
    }

    public String getImageUri() {
        imageUri = row.getImageHref() == null ? "" : row.getImageHref().toString();
        return imageUri;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .override(100,100)
                .centerCrop()
                .into(view);
    }


}
