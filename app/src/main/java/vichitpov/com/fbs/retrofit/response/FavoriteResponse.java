package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VichitPov on 3/8/18.
 */

public class FavoriteResponse {
    @SerializedName("meta")
    private Meta mMeta;
    @SerializedName("data")
    private List<Data> mData;

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta mMeta) {
        this.mMeta = mMeta;
    }

    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> mData) {
        this.mData = mData;
    }

    public static class Meta {
        @SerializedName("timestamp")
        private String mTimestamp;

        public String getTimestamp() {
            return mTimestamp;
        }

        public void setTimestamp(String mTimestamp) {
            this.mTimestamp = mTimestamp;
        }
    }

    public static class Price {
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

    public static class Category {
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
    }

    public static class Contactme {
        @SerializedName("data")
        private List<DataContactMe> mData;

        public List<DataContactMe> getData() {
            return mData;
        }

        public void setData(List<DataContactMe> mData) {
            this.mData = mData;
        }
    }

    static class DataContactMe {

        @SerializedName("profilePicture")
        private String mProfilepicture;
        @SerializedName("city")
        private String mCity;
        @SerializedName("map_coordinate")
        private String mMapCoordinate;
        @SerializedName("address")
        private String mAddress;
        @SerializedName("gender")
        private String mGender;
        @SerializedName("phone")
        private String mPhone;
        @SerializedName("email")
        private String mEmail;
        @SerializedName("lastName")
        private String mLastname;
        @SerializedName("firstName")
        private String mFirstname;
        @SerializedName("id")
        private int mId;
        @SerializedName("type")
        private String mType;

        @Override
        public String toString() {
            return "DataContactMe{" +
                    "mProfilepicture='" + mProfilepicture + '\'' +
                    ", mCity='" + mCity + '\'' +
                    ", mMapCoordinate='" + mMapCoordinate + '\'' +
                    ", mAddress='" + mAddress + '\'' +
                    ", mGender='" + mGender + '\'' +
                    ", mPhone='" + mPhone + '\'' +
                    ", mEmail='" + mEmail + '\'' +
                    ", mLastname='" + mLastname + '\'' +
                    ", mFirstname='" + mFirstname + '\'' +
                    ", mId=" + mId +
                    ", mType='" + mType + '\'' +
                    '}';
        }
    }


    public static class Createddate {
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

    public static class Updateddate {
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

    public static class Data {
        @SerializedName("updatedDate")
        private Updateddate mUpdateddate;
        @SerializedName("createdDate")
        private Createddate mCreateddate;
        @SerializedName("status")
        private String mStatus;
        @SerializedName("contactMe")
        private Contactme mContactme;
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

        public Contactme getContactme() {
            return mContactme;
        }

        public void setContactme(Contactme mContactme) {
            this.mContactme = mContactme;
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

        @Override
        public String toString() {
            return "Data{" +
                    "mUpdateddate=" + mUpdateddate +
                    ", mCreateddate=" + mCreateddate +
                    ", mStatus='" + mStatus + '\'' +
                    ", mContactme=" + mContactme +
                    ", mFavoritecount=" + mFavoritecount +
                    ", mContactmapcoordinate='" + mContactmapcoordinate + '\'' +
                    ", mContactaddress='" + mContactaddress + '\'' +
                    ", mContactemail='" + mContactemail + '\'' +
                    ", mContactphone='" + mContactphone + '\'' +
                    ", mContactname='" + mContactname + '\'' +
                    ", mProductimages=" + mProductimages +
                    ", mCategory=" + mCategory +
                    ", mPrice=" + mPrice +
                    ", mDescription='" + mDescription + '\'' +
                    ", mTitle='" + mTitle + '\'' +
                    ", mId=" + mId +
                    ", mType='" + mType + '\'' +
                    '}';
        }
    }
}
