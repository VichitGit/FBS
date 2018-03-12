package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VichitDeveloper on 3/6/18.
 */

public class ImagePostResponse {


    @SerializedName("description")
    private String mDescription;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("status")
    private int mStatus;

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

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }
}
