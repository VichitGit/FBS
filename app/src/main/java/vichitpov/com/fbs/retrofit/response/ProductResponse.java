package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by VichitPov on 2/25/18.
 */

public class ProductResponse implements Serializable {
    @SerializedName("meta")
    private Meta mMeta;
    @SerializedName("links")
    private Links mLinks;
    @SerializedName("data")
    private List<Data> mData;

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta mMeta) {
        this.mMeta = mMeta;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links mLinks) {
        this.mLinks = mLinks;
    }

    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> mData) {
        this.mData = mData;
    }

    public static class Meta implements Serializable {
        @SerializedName("timestamp")
        private String mTimestamp;
        @SerializedName("total")
        private int mTotal;
        @SerializedName("to")
        private int mTo;
        @SerializedName("per_page")
        private int mPerPage;
        @SerializedName("path")
        private String mPath;
        @SerializedName("last_page")
        private int mLastPage;
        @SerializedName("from")
        private int mFrom;
        @SerializedName("current_page")
        private int mCurrentPage;

        public String getTimestamp() {
            return mTimestamp;
        }

        public void setTimestamp(String mTimestamp) {
            this.mTimestamp = mTimestamp;
        }

        public int getTotal() {
            return mTotal;
        }

        public void setTotal(int mTotal) {
            this.mTotal = mTotal;
        }

        public int getTo() {
            return mTo;
        }

        public void setTo(int mTo) {
            this.mTo = mTo;
        }

        public int getPerPage() {
            return mPerPage;
        }

        public void setPerPage(int mPerPage) {
            this.mPerPage = mPerPage;
        }

        public String getPath() {
            return mPath;
        }

        public void setPath(String mPath) {
            this.mPath = mPath;
        }

        public int getLastPage() {
            return mLastPage;
        }

        public void setLastPage(int mLastPage) {
            this.mLastPage = mLastPage;
        }

        public int getFrom() {
            return mFrom;
        }

        public void setFrom(int mFrom) {
            this.mFrom = mFrom;
        }

        public int getCurrentPage() {
            return mCurrentPage;
        }

        public void setCurrentPage(int mCurrentPage) {
            this.mCurrentPage = mCurrentPage;
        }
    }

    public static class Links {
        @SerializedName("last")
        private String mLast;
        @SerializedName("first")
        private String mFirst;

        public String getLast() {
            return mLast;
        }

        public void setLast(String mLast) {
            this.mLast = mLast;
        }

        public String getFirst() {
            return mFirst;
        }

        public void setFirst(String mFirst) {
            this.mFirst = mFirst;
        }
    }

    public static class Price implements Serializable{
        @SerializedName("max")
        private String mMax;
        @SerializedName("min")
        private String mMin;

        public String getMax() {
            return mMax;
        }

        public void setMax(String mMax) {
            this.mMax = mMax;
        }

        public String getMin() {
            return mMin;
        }

        public void setMin(String mMin) {
            this.mMin = mMin;
        }
    }

    public static class Category implements Serializable {
        @SerializedName("category_image")
        private String mCategoryImage;
        @SerializedName("category_parent")
        private int mCategoryParent;
        @SerializedName("category_name_khmer")
        private String mCategoryNameKhmer;
        @SerializedName("category_name")
        private String mCategoryName;
        @SerializedName("id")
        private int mId;

        public String getCategoryImage() {
            return mCategoryImage;
        }

        public void setCategoryImage(String mCategoryImage) {
            this.mCategoryImage = mCategoryImage;
        }

        public int getCategoryParent() {
            return mCategoryParent;
        }

        public void setCategoryParent(int mCategoryParent) {
            this.mCategoryParent = mCategoryParent;
        }

        public String getCategoryNameKhmer() {
            return mCategoryNameKhmer;
        }

        public void setCategoryNameKhmer(String mCategoryNameKhmer) {
            this.mCategoryNameKhmer = mCategoryNameKhmer;
        }

        public String getCategoryName() {
            return mCategoryName;
        }

        public void setCategoryName(String mCategoryName) {
            this.mCategoryName = mCategoryName;
        }

        public int getId() {
            return mId;
        }

        public void setId(int mId) {
            this.mId = mId;
        }

        @Override
        public String toString() {
            return "Category{" +
                    "mCategoryImage='" + mCategoryImage + '\'' +
                    ", mCategoryParent=" + mCategoryParent +
                    ", mCategoryNameKhmer='" + mCategoryNameKhmer + '\'' +
                    ", mCategoryName='" + mCategoryName + '\'' +
                    ", mId=" + mId +
                    '}';
        }
    }

    public static class Createddate implements Serializable {
        @SerializedName("timezone")
        private String mTimezone;
        @SerializedName("timezone_type")
        private int mTimezoneType;
        @SerializedName("date")
        private String mDate;

        public String getTimezone() {
            return mTimezone;
        }

        public void setTimezone(String mTimezone) {
            this.mTimezone = mTimezone;
        }

        public int getTimezoneType() {
            return mTimezoneType;
        }

        public void setTimezoneType(int mTimezoneType) {
            this.mTimezoneType = mTimezoneType;
        }

        public String getDate() {
            return mDate;
        }

        public void setDate(String mDate) {
            this.mDate = mDate;
        }

        @Override
        public String toString() {
            return "Createddate{" +
                    "mTimezone='" + mTimezone + '\'' +
                    ", mTimezoneType=" + mTimezoneType +
                    ", mDate='" + mDate + '\'' +
                    '}';
        }
    }

    public static class Updateddate implements Serializable {
        @SerializedName("timezone")
        private String mTimezone;
        @SerializedName("timezone_type")
        private int mTimezoneType;
        @SerializedName("date")
        private String mDate;

        public String getTimezone() {
            return mTimezone;
        }

        public void setTimezone(String mTimezone) {
            this.mTimezone = mTimezone;
        }

        public int getTimezoneType() {
            return mTimezoneType;
        }

        public void setTimezoneType(int mTimezoneType) {
            this.mTimezoneType = mTimezoneType;
        }

        public String getDate() {
            return mDate;
        }

        public void setDate(String mDate) {
            this.mDate = mDate;
        }

        @Override
        public String toString() {
            return "Updateddate{" +
                    "mTimezone='" + mTimezone + '\'' +
                    ", mTimezoneType=" + mTimezoneType +
                    ", mDate='" + mDate + '\'' +
                    '}';
        }
    }

    public static class Data implements Serializable {
        @SerializedName("updatedDate")
        private Updateddate mUpdateddate;
        @SerializedName("createdDate")
        private Createddate mCreateddate;
        @SerializedName("status")
        private String mStatus;
        @SerializedName("favoriteCount")
        private int mFavoritecount;
        @SerializedName("contactMapCoordinate")
        private String mContactmapcoordinate;
        @SerializedName("contactAddress")
        private String mContactaddress;
        @SerializedName("contactEmail")
        private String mContactemail;
        @SerializedName("contactPhone")
        private String mContactphone;
        @SerializedName("contactName")
        private String mContactname;
        @SerializedName("productImages")
        private List<String> mProductimages;
        @SerializedName("category")
        private Category mCategory;
        @SerializedName("price")
        private List<Price> mPrice;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("id")
        private int mId;
        @SerializedName("type")
        private String mType;

        public Updateddate getUpdateddate() {
            return mUpdateddate;
        }

        public void setUpdateddate(Updateddate mUpdateddate) {
            this.mUpdateddate = mUpdateddate;
        }

        public Createddate getCreateddate() {
            return mCreateddate;
        }

        public void setCreateddate(Createddate mCreateddate) {
            this.mCreateddate = mCreateddate;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String mStatus) {
            this.mStatus = mStatus;
        }

        public int getFavoritecount() {
            return mFavoritecount;
        }

        public void setFavoritecount(int mFavoritecount) {
            this.mFavoritecount = mFavoritecount;
        }

        public String getContactmapcoordinate() {
            return mContactmapcoordinate;
        }

        public void setContactmapcoordinate(String mContactmapcoordinate) {
            this.mContactmapcoordinate = mContactmapcoordinate;
        }

        public String getContactaddress() {
            return mContactaddress;
        }

        public void setContactaddress(String mContactaddress) {
            this.mContactaddress = mContactaddress;
        }

        public String getContactemail() {
            return mContactemail;
        }

        public void setContactemail(String mContactemail) {
            this.mContactemail = mContactemail;
        }

        public String getContactphone() {
            return mContactphone;
        }

        public void setContactphone(String mContactphone) {
            this.mContactphone = mContactphone;
        }
        public String getContactname() {
            return mContactname;
        }

        public void setContactname(String mContactname) {
            this.mContactname = mContactname;
        }

        public List<String> getProductimages() {
            return mProductimages;
        }

        public void setProductimages(List<String> mProductimages) {
            this.mProductimages = mProductimages;
        }

        public Category getCategory() {
            return mCategory;
        }

        public void setCategory(Category mCategory) {
            this.mCategory = mCategory;
        }

        public List<Price> getPrice() {
            return mPrice;
        }

        public void setPrice(List<Price> mPrice) {
            this.mPrice = mPrice;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String mDescription) {
            this.mDescription = mDescription;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public int getId() {
            return mId;
        }

        public void setId(int mId) {
            this.mId = mId;
        }

        public String getType() {
            return mType;
        }

        public void setType(String mType) {
            this.mType = mType;
        }
    }


//    @SerializedName("meta")
//    private Meta meta;
//    @SerializedName("links")
//    private Links links;
//    @SerializedName("data")
//    private List<Data> data;
//
//    public Meta getMeta() {
//        return meta;
//    }
//
//    public void setMeta(Meta meta) {
//        this.meta = meta;
//    }
//
//    public Links getLinks() {
//        return links;
//    }
//
//    public void setLinks(Links links) {
//        this.links = links;
//    }
//
//    public List<Data> getData() {
//        return data;
//    }
//
//    public void setData(List<Data> data) {
//        this.data = data;
//    }
//
//    public static class Meta implements Serializable{
//        @SerializedName("timestamp")
//        private String timestamp;
//        @SerializedName("total")
//        private int total;
//        @SerializedName("to")
//        private int to;
//        @SerializedName("per_page")
//        private int perPage;
//        @SerializedName("path")
//        private String path;
//        @SerializedName("last_page")
//        private int lastPage;
//        @SerializedName("from")
//        private int from;
//        @SerializedName("current_page")
//        private int currentPage;
//
//        public String getTimestamp() {
//            return timestamp;
//        }
//
//        public void setTimestamp(String timestamp) {
//            this.timestamp = timestamp;
//        }
//
//        public int getTotal() {
//            return total;
//        }
//
//        public void setTotal(int total) {
//            this.total = total;
//        }
//
//        public int getTo() {
//            return to;
//        }
//
//        public void setTo(int to) {
//            this.to = to;
//        }
//
//        public int getPerPage() {
//            return perPage;
//        }
//
//        public void setPerPage(int perPage) {
//            this.perPage = perPage;
//        }
//
//        public String getPath() {
//            return path;
//        }
//
//        public void setPath(String path) {
//            this.path = path;
//        }
//
//        public int getLastPage() {
//            return lastPage;
//        }
//
//        public void setLastPage(int lastPage) {
//            this.lastPage = lastPage;
//        }
//
//        public int getFrom() {
//            return from;
//        }
//
//        public void setFrom(int from) {
//            this.from = from;
//        }
//
//        public int getCurrentPage() {
//            return currentPage;
//        }
//
//        public void setCurrentPage(int currentPage) {
//            this.currentPage = currentPage;
//        }
//    }
//
//    public static class Links implements Serializable{
//        @SerializedName("next")
//        private String next;
//        @SerializedName("last")
//        private String last;
//        @SerializedName("first")
//        private String first;
//
//        public String getNext() {
//            return next;
//        }
//
//        public void setNext(String next) {
//            this.next = next;
//        }
//
//        public String getLast() {
//            return last;
//        }
//
//        public void setLast(String last) {
//            this.last = last;
//        }
//
//        public String getFirst() {
//            return first;
//        }
//
//        public void setFirst(String first) {
//            this.first = first;
//        }
//    }
//
//    public static class Price implements Serializable {
//        @SerializedName("max")
//        private String max;
//        @SerializedName("min")
//        private String min;
//
//        public String getMax() {
//            return max;
//        }
//
//        public void setMax(String max) {
//            this.max = max;
//        }
//
//        public String getMin() {
//            return min;
//        }
//
//        public void setMin(String min) {
//            this.min = min;
//        }
//
//        @Override
//        public String toString() {
//            return "Price{" +
//                    "max='" + max + '\'' +
//                    ", min='" + min + '\'' +
//                    '}';
//        }
//    }
//
//    public static class Category implements Serializable {
//        @SerializedName("category_image")
//        private String categoryImage;
//        @SerializedName("category_parent")
//        private int categoryParent;
//        @SerializedName("category_name_khmer")
//        private String categoryNameKhmer;
//        @SerializedName("category_name")
//        private String categoryName;
//        @SerializedName("id")
//        private int id;
//
//        public String getCategoryImage() {
//            return categoryImage;
//        }
//
//        public void setCategoryImage(String categoryImage) {
//            this.categoryImage = categoryImage;
//        }
//
//        public int getCategoryParent() {
//            return categoryParent;
//        }
//
//        public void setCategoryParent(int categoryParent) {
//            this.categoryParent = categoryParent;
//        }
//
//        public String getCategoryNameKhmer() {
//            return categoryNameKhmer;
//        }
//
//        public void setCategoryNameKhmer(String categoryNameKhmer) {
//            this.categoryNameKhmer = categoryNameKhmer;
//        }
//
//        public String getCategoryName() {
//            return categoryName;
//        }
//
//        public void setCategoryName(String categoryName) {
//            this.categoryName = categoryName;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        @Override
//        public String toString() {
//            return "Category{" +
//                    "categoryImage='" + categoryImage + '\'' +
//                    ", categoryParent=" + categoryParent +
//                    ", categoryNameKhmer='" + categoryNameKhmer + '\'' +
//                    ", categoryName='" + categoryName + '\'' +
//                    ", id=" + id +
//                    '}';
//        }
//    }
//
//    public static class Createddate implements Serializable{
//        @SerializedName("timezone")
//        private String timezone;
//        @SerializedName("timezone_type")
//        private int timezoneType;
//        @SerializedName("date")
//        private String date;
//
//        public String getTimezone() {
//            return timezone;
//        }
//
//        public void setTimezone(String timezone) {
//            this.timezone = timezone;
//        }
//
//        public int getTimezoneType() {
//            return timezoneType;
//        }
//
//        public void setTimezoneType(int timezoneType) {
//            this.timezoneType = timezoneType;
//        }
//
//        public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//    }
//
//    public static class Updateddate implements Serializable {
//        @SerializedName("timezone")
//        private String timezone;
//        @SerializedName("timezone_type")
//        private int timezoneType;
//        @SerializedName("date")
//        private String date;
//
//        public String getTimezone() {
//            return timezone;
//        }
//
//        public void setTimezone(String timezone) {
//            this.timezone = timezone;
//        }
//
//        public int getTimezoneType() {
//            return timezoneType;
//        }
//
//        public void setTimezoneType(int timezoneType) {
//            this.timezoneType = timezoneType;
//        }
//
//        public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//    }
//
//    public static class Data implements Serializable {
//        @SerializedName("updatedDate")
//        private Updateddate updateddate;
//        @SerializedName("createdDate")
//        private Createddate createddate;
//        @SerializedName("status")
//        private String status;
//        @SerializedName("contactMapCoordinate")
//        private String contactmapcoordinate;
//        @SerializedName("contactAddress")
//        private String contactaddress;
//        @SerializedName("contactEmail")
//        private String contactemail;
//        @SerializedName("contactPhone")
//        private String contactphone;
//        @SerializedName("contactName")
//        private String contactname;
//        @SerializedName("productImages")
//        private List<String> productimages;
//        @SerializedName("category")
//        private Category category;
//        @SerializedName("price")
//        private List<Price> price;
//        @SerializedName("description")
//        private String description;
//        @SerializedName("title")
//        private String title;
//        @SerializedName("id")
//        private int id;
//        @SerializedName("type")
//        private String type;
//
//        public Updateddate getUpdateddate() {
//            return updateddate;
//        }
//
//        public void setUpdateddate(Updateddate updateddate) {
//            this.updateddate = updateddate;
//        }
//
//        public Createddate getCreateddate() {
//            return createddate;
//        }
//
//        public void setCreateddate(Createddate createddate) {
//            this.createddate = createddate;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public String getContactmapcoordinate() {
//            return contactmapcoordinate;
//        }
//
//        public void setContactmapcoordinate(String contactmapcoordinate) {
//            this.contactmapcoordinate = contactmapcoordinate;
//        }
//
//        public String getContactaddress() {
//            return contactaddress;
//        }
//
//        public void setContactaddress(String contactaddress) {
//            this.contactaddress = contactaddress;
//        }
//
//        public String getContactemail() {
//            return contactemail;
//        }
//
//        public void setContactemail(String contactemail) {
//            this.contactemail = contactemail;
//        }
//
//        public String getContactphone() {
//            return contactphone;
//        }
//
//        public void setContactphone(String contactphone) {
//            this.contactphone = contactphone;
//        }
//
//        public String getContactname() {
//            return contactname;
//        }
//
//        public void setContactname(String contactname) {
//            this.contactname = contactname;
//        }
//
//        public List<String> getProductimages() {
//            return productimages;
//        }
//
//        public void setProductimages(List<String> productimages) {
//            this.productimages = productimages;
//        }
//
//        public Category getCategory() {
//            return category;
//        }
//
//        public void setCategory(Category category) {
//            this.category = category;
//        }
//
//        public List<Price> getPrice() {
//            return price;
//        }
//
//        public void setPrice(List<Price> price) {
//            this.price = price;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        @Override
//        public String toString() {
//            return "Data{" +
//                    "updateddate=" + updateddate +
//                    ", createddate=" + createddate +
//                    ", status=" + status +
//                    ", contactmapcoordinate='" + contactmapcoordinate + '\'' +
//                    ", contactaddress='" + contactaddress + '\'' +
//                    ", contactemail='" + contactemail + '\'' +
//                    ", contactphone='" + contactphone + '\'' +
//                    ", contactname='" + contactname + '\'' +
//                    ", productimages=" + productimages +
//                    ", category=" + category +
//                    ", price=" + price +
//                    ", description='" + description + '\'' +
//                    ", title='" + title + '\'' +
//                    ", id=" + id +
//                    ", type='" + type + '\'' +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "ProductResponse{" +
//                "meta=" + meta +
//                ", links=" + links +
//                ", data=" + data +
//                '}';
//    }
}
