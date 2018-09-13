package persistence;

/*
* yaswanth
* 14-09-2018
* this class is the helper class for local data base it acts as modal class
*
* */

public class ItemDatabaseHelper {
    String id;
    String Title;
    String Description;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
