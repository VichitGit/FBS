package vichitpov.com.fbs.retrofit.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by VichitPov on 2/25/18.
 */

public class ProductResponse {

    @SerializedName("meta")
    private Meta meta;
    @SerializedName("links")
    private Links links;
    @SerializedName("data")
    private List<Data> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
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
        @SerializedName("total")
        private int total;
        @SerializedName("to")
        private int to;
        @SerializedName("per_page")
        private int perPage;
        @SerializedName("path")
        private String path;
        @SerializedName("last_page")
        private int lastPage;
        @SerializedName("from")
        private int from;
        @SerializedName("current_page")
        private int currentPage;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }
    }

    public static class Links {
        @SerializedName("last")
        private String last;
        @SerializedName("first")
        private String first;

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }
    }

    public static class Price {
        @SerializedName("max")
        private String max;
        @SerializedName("min")
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
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

    public static class User {
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("role")
        private int role;
        @SerializedName("status")
        private int status;
        @SerializedName("access_token")
        private String accessToken;
        @SerializedName("profile_pic")
        private String profilePic;
        @SerializedName("city")
        private String city;
        @SerializedName("address")
        private String address;
        @SerializedName("gender")
        private String gender;
        @SerializedName("phone")
        private String phone;
        @SerializedName("email")
        private String email;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("accountkit_id")
        private String accountkitId;
        @SerializedName("id")
        private int id;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
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

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getAccountkitId() {
            return accountkitId;
        }

        public void setAccountkitId(String accountkitId) {
            this.accountkitId = accountkitId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Data {
        @SerializedName("status")
        private int status;
        @SerializedName("user")
        private User user;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
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
}
