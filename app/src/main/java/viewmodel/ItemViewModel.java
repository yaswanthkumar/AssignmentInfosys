package viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.infosys.myassignment.assignmentinfosys.R;

import java.util.Observable;

import model.Item;
import model.ItemResponse;

public class ItemViewModel extends BaseObservable {



    private String title;
    private String description;
    private String imageUri;

    private Item.Row row;
    public ItemViewModel (Item.Row row){ this.row=row;

    }

    public String getTitle()
    {
        title = row.getTitle() == null ? "": row.getTitle().toString();
        return title;
    }
    public String getDescription()
    {
        description = row.getDescription() == null ? "": row.getDescription().toString();
        return  description;
    }
    public String getImageUri()
    {
        imageUri = row.getImageHref() == null ? "": row.getImageHref().toString();
        return imageUri;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(view);
    }
}
