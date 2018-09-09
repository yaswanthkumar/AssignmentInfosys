package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//Author:yaswanth
//this is the modal class to get the data and set the data
public class Item {

    private String title;
    private String description;
    private Object imageHref;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImageHref() {
        return imageHref;
    }

    public void setImageHref(Object imageHref) {
        this.imageHref = imageHref;
    }

    public static class Row {

       @SerializedName("title")
       @Expose
       private String title;
       @SerializedName("description")
       @Expose
       private String description;
       @SerializedName("imageHref")
       @Expose
       private Object imageHref;

       public String getTitle() {
           return title;
       }

       public void setTitle(String title) {
           this.title = title;
       }

       public String getDescription() {
           return description;
       }

       public void setDescription(String description) {
           this.description = description;
       }

       public Object getImageHref() {
           return imageHref;
       }

       public void setImageHref(Object imageHref) {
           this.imageHref = imageHref;
       }
    }
}
