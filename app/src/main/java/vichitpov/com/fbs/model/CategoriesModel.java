package vichitpov.com.fbs.model;

/**
 * Created by Goldenware on 12/27/2017.
 */

public class CategoriesModel {
    private String name;
    private String countPost;

    public CategoriesModel(String name, String countPost) {
        this.name = name;
        this.countPost = countPost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountPost() {
        return countPost;
    }

    public void setCountPost(String countPost) {
        this.countPost = countPost;
    }
}
