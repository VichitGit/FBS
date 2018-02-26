package vichitpov.com.fbs.model;

/**
 * Created by VichitPov on 2/25/18.
 */

public class CategoryHeaderModel {

    private String name;
    private int icon;

    public CategoryHeaderModel(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
