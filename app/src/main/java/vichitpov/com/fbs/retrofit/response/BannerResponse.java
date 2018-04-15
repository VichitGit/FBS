package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResponse {

    @SerializedName("meta")
    private Meta meta;
    @SerializedName("data")
    private List<Data> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Meta {
        @SerializedName("timestamp")
        private String timestamp;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
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

    public static class Data {
        @SerializedName("createdDate")
        private Createddate createddate;
        @SerializedName("bannerImage")
        private String bannerimage;
        @SerializedName("status")
        private String status;
        @SerializedName("content")
        private String content;
        @SerializedName("title")
        private String title;
        @SerializedName("id")
        private int id;

        public Createddate getCreateddate() {
            return createddate;
        }

        public void setCreateddate(Createddate createddate) {
            this.createddate = createddate;
        }

        public String getBannerimage() {
            return bannerimage;
        }

        public void setBannerimage(String bannerimage) {
            this.bannerimage = bannerimage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }
}
