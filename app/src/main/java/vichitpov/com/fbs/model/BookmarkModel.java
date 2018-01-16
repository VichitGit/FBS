package vichitpov.com.fbs.model;

/**
 * Created by Goldenware on 1/15/2018.
 */

public class BookmarkModel {
    private int icon;
    private String title;

    public BookmarkModel(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
