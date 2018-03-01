package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VichitDeveloper on 2/27/18.
 */

public class UserInformationResponse {


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
        @SerializedName("signUpDate")
        private Signupdate signupdate;
        @SerializedName("totalPostsSell")
        private int totalpostssell;
        @SerializedName("totalPostsBuy")
        private int totalpostsbuy;
        @SerializedName("status")
        private String status;
        @SerializedName("role")
        private String role;
        @SerializedName("profilePicture")
        private String profilepicture;
        @SerializedName("city")
        private String city;
        @SerializedName("address")
        private String address;
        @SerializedName("gender")
        private String gender;
        @SerializedName("map_coordinate")
        private String mapCondinate;
        @SerializedName("phone")
        private String phone;
        @SerializedName("email")
        private String email;
        @SerializedName("lastName")
        private String lastname;
        @SerializedName("firstName")
        private String firstname;
        @SerializedName("id")
        private int id;
        @SerializedName("type")
        private String type;

        public Signupdate getSignupdate() {
            return signupdate;
        }

        public void setSignupdate(Signupdate signupdate) {
            this.signupdate = signupdate;
        }

        public int getTotalpostssell() {
            return totalpostssell;
        }

        public void setTotalpostssell(int totalpostssell) {
            this.totalpostssell = totalpostssell;
        }

        public int getTotalpostsbuy() {
            return totalpostsbuy;
        }

        public void setTotalpostsbuy(int totalpostsbuy) {
            this.totalpostsbuy = totalpostsbuy;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getProfilepicture() {
            return profilepicture;
        }

        public void setProfilepicture(String profilepicture) {
            this.profilepicture = profilepicture;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
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

        public String getMapCondinate() {
            return mapCondinate;
        }

        public void setMapCondinate(String mapCondinate) {
            this.mapCondinate = mapCondinate;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "signupdate=" + signupdate +
                    ", totalpostssell=" + totalpostssell +
                    ", totalpostsbuy=" + totalpostsbuy +
                    ", status='" + status + '\'' +
                    ", role='" + role + '\'' +
                    ", profilepicture='" + profilepicture + '\'' +
                    ", city='" + city + '\'' +
                    ", address='" + address + '\'' +
                    ", gender='" + gender + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", firstname='" + firstname + '\'' +
                    ", id=" + id +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public static class Signupdate {
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
}
