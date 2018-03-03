package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VichitDeveloper on 3/2/18.
 */

public class CategoriesResponse {

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

    public static class Categorychildren {
        @SerializedName("category_image")
        private String mCategoryImage;
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
            return "Categorychildren{" +
                    "mCategoryImage='" + mCategoryImage + '\'' +
                    ", mCategoryNameKhmer='" + mCategoryNameKhmer + '\'' +
                    ", mCategoryName='" + mCategoryName + '\'' +
                    ", mId=" + mId +
                    '}';
        }
    }

    public static class Data {
        @SerializedName("categoryChildren")
        private List<Categorychildren> mCategorychildren;
        @SerializedName("categoryImage")
        private String mCategoryimage;
        @SerializedName("categoryNameKhmer")
        private String mCategorynamekhmer;
        @SerializedName("categoryName")
        private String mCategoryname;

        public List<Categorychildren> getCategorychildren() {
            return mCategorychildren;
        }

        public void setCategorychildren(List<Categorychildren> mCategorychildren) {
            this.mCategorychildren = mCategorychildren;
        }

        public String getCategoryimage() {
            return mCategoryimage;
        }

        public void setCategoryimage(String mCategoryimage) {
            this.mCategoryimage = mCategoryimage;
        }

        public String getCategorynamekhmer() {
            return mCategorynamekhmer;
        }

        public void setCategorynamekhmer(String mCategorynamekhmer) {
            this.mCategorynamekhmer = mCategorynamekhmer;
        }

        public String getCategoryname() {
            return mCategoryname;
        }

        public void setCategoryname(String mCategoryname) {
            this.mCategoryname = mCategoryname;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "mCategorychildren=" + mCategorychildren +
                    ", mCategoryimage='" + mCategoryimage + '\'' +
                    ", mCategorynamekhmer='" + mCategorynamekhmer + '\'' +
                    ", mCategoryname='" + mCategoryname + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CategoriesResponse{" +
                "mMeta=" + mMeta +
                ", mData=" + mData +
                '}';
    }
}
