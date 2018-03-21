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

    public static class Links implements Serializable {
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

    public static class Price implements Serializable {
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
    }

    public static class Signupdate implements Serializable {
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
    }

    public static class DataContact implements Serializable {
        @SerializedName("signUpDate")
        private Signupdate mSignupdate;
        @SerializedName("status")
        private String mStatus;
        @SerializedName("role")
        private String mRole;
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

        public Signupdate getSignupdate() {
            return mSignupdate;
        }

        public void setSignupdate(Signupdate mSignupdate) {
            this.mSignupdate = mSignupdate;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String mStatus) {
            this.mStatus = mStatus;
        }

        public String getRole() {
            return mRole;
        }

        public void setRole(String mRole) {
            this.mRole = mRole;
        }

        public String getProfilepicture() {
            return mProfilepicture;
        }

        public void setProfilepicture(String mProfilepicture) {
            this.mProfilepicture = mProfilepicture;
        }

        public String getCity() {
            return mCity;
        }

        public void setCity(String mCity) {
            this.mCity = mCity;
        }

        public String getMapCoordinate() {
            return mMapCoordinate;
        }

        public void setMapCoordinate(String mMapCoordinate) {
            this.mMapCoordinate = mMapCoordinate;
        }

        public String getAddress() {
            return mAddress;
        }

        public void setAddress(String mAddress) {
            this.mAddress = mAddress;
        }

        public String getGender() {
            return mGender;
        }

        public void setGender(String mGender) {
            this.mGender = mGender;
        }

        public String getPhone() {
            return mPhone;
        }

        public void setPhone(String mPhone) {
            this.mPhone = mPhone;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String mEmail) {
            this.mEmail = mEmail;
        }

        public String getLastname() {
            return mLastname;
        }

        public void setLastname(String mLastname) {
            this.mLastname = mLastname;
        }

        public String getFirstname() {
            return mFirstname;
        }

        public void setFirstname(String mFirstname) {
            this.mFirstname = mFirstname;
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

    public static class Contactme implements Serializable{

        @SerializedName("data")
        private List<DataContact> mDataContact;

        public List<DataContact> getDatacontact() {
            return mDataContact;
        }

        public void setDatacontact(List<DataContact> mDataContact) {
            this.mDataContact = mDataContact;
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
    }

    public static class Updateddate implements Serializable{
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
    }

    public static class Data implements Serializable {
        @SerializedName("updatedDate")
        private Updateddate mUpdateddate;
        @SerializedName("createdDate")
        private Createddate mCreateddate;
        @SerializedName("status")
        private String mStatus;
        @SerializedName("contactMe")
        private Contactme mContactme;
        @SerializedName("viewCount")
        private int mViewcount;
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

        public int getViewcount() {
            return mViewcount;
        }

        public void setViewcount(int mViewcount) {
            this.mViewcount = mViewcount;
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

}
