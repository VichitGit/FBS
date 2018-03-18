package vichitpov.com.fbs.model;

/**
 * Created by VichitDeveloper on 3/18/18.
 */

public class ImageModel {

    public static final String URL = "URL";
    public static final String URI = "URI";

    private String imagePath;
    private String imageType;


    public ImageModel(String imagePath, String imageType) {
        this.imagePath = imagePath;
        this.imageType = imageType;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

//    public enum IMAGETYPE {
//        URI,URL
//    }
}
