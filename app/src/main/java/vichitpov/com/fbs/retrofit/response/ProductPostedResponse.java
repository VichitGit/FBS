package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VichitDeveloper on 3/1/18.
 */

public class ProductPostedResponse {

    @SerializedName("meta")
    private Meta meta;
    @SerializedName("data")
    private Data data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Meta {
        @SerializedName("link")
        private String link;
        @SerializedName("timestamp")
        private String timestamp;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class Data {
        @SerializedName("updatedDate")
        private Updateddate updateddate;
        @SerializedName("createdDate")
        private Createddate createddate;
        @SerializedName("favoriteCount")
        private int favoritecount;
        @SerializedName("contactMapCoordinate")
        private String contactmapcoordinate;
        @SerializedName("contactAddress")
        private String contactaddress;
        @SerializedName("contactEmail")
        private String contactemail;
        @SerializedName("contactPhone")
        private String contactphone;
        @SerializedName("contactName")
        private String contactname;
        @SerializedName("productImages")
        private List<String> productimages;
        @SerializedName("category")
        private Category category;
        @SerializedName("price")
        private List<Price> price;
        @SerializedName("description")
        private String description;
        @SerializedName("title")
        private String title;
        @SerializedName("id")
        private int id;
        @SerializedName("type")
        private String type;

        public Updateddate getUpdateddate() {
            return updateddate;
        }

        public void setUpdateddate(Updateddate updateddate) {
            this.updateddate = updateddate;
        }

        public Createddate getCreateddate() {
            return createddate;
        }

        public void setCreateddate(Createddate createddate) {
            this.createddate = createddate;
        }

        public int getFavoritecount() {
            return favoritecount;
        }

        public void setFavoritecount(int favoritecount) {
            this.favoritecount = favoritecount;
        }

        public String getContactmapcoordinate() {
            return contactmapcoordinate;
        }

        public void setContactmapcoordinate(String contactmapcoordinate) {
            this.contactmapcoordinate = contactmapcoordinate;
        }

        public String getContactaddress() {
            return contactaddress;
        }

        public void setContactaddress(String contactaddress) {
            this.contactaddress = contactaddress;
        }

        public String getContactemail() {
            return contactemail;
        }

        public void setContactemail(String contactemail) {
            this.contactemail = contactemail;
        }

        public String getContactphone() {
            return contactphone;
        }

        public void setContactphone(String contactphone) {
            this.contactphone = contactphone;
        }

        public String getContactname() {
            return contactname;
        }

        public void setContactname(String contactname) {
            this.contactname = contactname;
        }

        public List<String> getProductimages() {
            return productimages;
        }

        public void setProductimages(List<String> productimages) {
            this.productimages = productimages;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public List<Price> getPrice() {
            return price;
        }

        public void setPrice(List<Price> price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Updateddate {
        @SerializedName("timezone")
        private String timezone;
        @SerializedName("timezone_type")
        private int timezoneType;
        @SerializedName("date")
        private String date;

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public int getTimezoneType() {
            return timezoneType;
        }

        public void setTimezoneType(int timezoneType) {
            this.timezoneType = timezoneType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class Createddate {
        @SerializedName("timezone")
        private String timezone;
        @SerializedName("timezone_type")
        private int timezoneType;
        @SerializedName("date")
        private String date;

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public int getTimezoneType() {
            return timezoneType;
        }

        public void setTimezoneType(int timezoneType) {
            this.timezoneType = timezoneType;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class Category {
        @SerializedName("category_image")
        private String categoryImage;
        @SerializedName("category_parent")
        private int categoryParent;
        @SerializedName("category_name_khmer")
        private String categoryNameKhmer;
        @SerializedName("category_name")
        private String categoryName;
        @SerializedName("id")
        private int id;

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

        public int getCategoryParent() {
            return categoryParent;
        }

        public void setCategoryParent(int categoryParent) {
            this.categoryParent = categoryParent;
        }

        public String getCategoryNameKhmer() {
            return categoryNameKhmer;
        }

        public void setCategoryNameKhmer(String categoryNameKhmer) {
            this.categoryNameKhmer = categoryNameKhmer;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Price {
        @SerializedName("max")
        private int max;
        @SerializedName("min")
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }
}
