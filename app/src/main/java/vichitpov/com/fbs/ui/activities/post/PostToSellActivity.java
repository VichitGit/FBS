package vichitpov.com.fbs.ui.activities.post;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.File;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.ConvertBitmap;
import vichitpov.com.fbs.base.VailidationEmail;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ImagePostResponse;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.ChooseCategoryActivity;

public class PostToSellActivity extends BaseAppCompatActivity implements Validator.ValidationListener, View.OnClickListener {
    @NotEmpty(message = R.string.validation_category + "")
    private TextView textCategory;
    @NotEmpty(message = R.string.validation_title + "")
    private EditText editTitle;
    @NotEmpty(message = R.string.validation_description + "")
    private EditText editDescription;
    @NotEmpty(message = R.string.validation_price + "")
    private EditText editPriceStart;
    @NotEmpty(message = R.string.validation_price + "")
    private EditText editPriceEnd;
    @NotEmpty(message = R.string.validation_contact_name + "")
    private EditText editContactName;
    private EditText editContactEmail;
    @NotEmpty(message = R.string.validation_address + "")
    private EditText editContactAddress;
    @NotEmpty(message = R.string.validation_contact_phone + "")
    private EditText editContactPhone;

    private UserInformationManager userInformationManager;
    private Button remove1, remove2, remove3, remove4, remove5;
    private Button buttonUpload;
    private ImageView image1, image2, image3, image4, image5, imageBack;
    private String path1, path2, path3, path4, path5;
    private String clickBrowse;
    private String selectedCategoryId;
    private String coordinate;
    private String accessToken;
    private Validator validator;
    private ApiService apiService;
    private SpotsDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_to_sell);

        validator = new Validator(this);
        validator.setValidationListener(this);

        dialog = new SpotsDialog(this, getString(R.string.alert_dialog_uploading));
        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        accessToken = userInformationManager.getUser().getAccessToken();

        initView();
        checkSharePreference();
        onClickBrowseToGallery();
        onClickRemovePhoto();

        imageBack.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        textCategory.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AnyConstant.CHOOSE_CATEGORY && resultCode != RESULT_CANCELED) {
            String selectedCategoryName = data.getStringExtra(AnyConstant.CATEGORY_NAME);
            selectedCategoryId = data.getStringExtra(AnyConstant.CATEGORY_ID);
            textCategory.setText(selectedCategoryName);
        }

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            switch (clickBrowse) {
                case "image1": {
                    path1 = image.getPath();
                    Bitmap bitmap = bitmap(path1);
                    image1.setImageBitmap(bitmap);
                    remove1.setVisibility(View.VISIBLE);
                    break;
                }
                case "image2": {
                    path2 = image.getPath();
                    Bitmap bitmap = bitmap(path2);
                    image2.setImageBitmap(bitmap);
                    remove2.setVisibility(View.VISIBLE);
                    break;
                }
                case "image3": {
                    path3 = image.getPath();
                    Bitmap bitmap = bitmap(path3);
                    image3.setImageBitmap(bitmap);
                    remove3.setVisibility(View.VISIBLE);
                    break;
                }
                case "image4": {
                    path4 = image.getPath();
                    Bitmap bitmap = bitmap(path4);
                    image4.setImageBitmap(bitmap);
                    remove4.setVisibility(View.VISIBLE);
                    break;
                }
                case "image5": {
                    path5 = image.getPath();
                    Bitmap bitmap = bitmap(path5);
                    image5.setImageBitmap(bitmap);
                    remove5.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonUpload) {
            validator.validate();
        } else if (id == R.id.textCategory) {
            startActivityForResult(new Intent(getApplicationContext(), ChooseCategoryActivity.class), AnyConstant.CHOOSE_CATEGORY);
        } else if (id == R.id.imageBack) {
            finish();
        }
    }

    @Override
    public void onValidationSucceeded() {
        String title, description, name, address, email, phone, priceFrom, priceTo;

        title = editTitle.getText().toString();
        description = editDescription.getText().toString();
        name = editContactName.getText().toString();
        email = editContactEmail.getText().toString();
        phone = editContactPhone.getText().toString();
        address = editContactAddress.getText().toString();
        priceFrom = editPriceStart.getText().toString();
        priceTo = editPriceEnd.getText().toString();

        if (!VailidationEmail.isEmailValid(email) && !email.isEmpty()) {
            editContactEmail.setError("Invalid email(example@gmail.com)");
        } else {
            int from = Integer.parseInt(priceFrom);
            int to = Integer.parseInt(priceTo);
            int cateId = Integer.parseInt(selectedCategoryId);

            if (email.isEmpty()) {
                email = "norton@null.com";
            }

            uploadImage(title, description, name, email, phone, address, from, to, cateId);

        }
    }

    //upload product after upload image success
    private void uploadProduct(String title, String description, String name, String email, String phone, String address, int priceFrom, int priceTo, int category, String image) {
        accessToken = userInformationManager.getUser().getAccessToken();
        Call<ProductPostedResponse> call = apiService.postProduct(accessToken, "sell", title, category, description, priceFrom, priceTo, name, phone, email, image, address, coordinate);
        call.enqueue(new Callback<ProductPostedResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductPostedResponse> call, @NonNull Response<ProductPostedResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        dialog.dismiss();
                        finish();
                        Toast.makeText(PostToSellActivity.this, R.string.text_upload_success, Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(PostToSellActivity.this, R.string.text_upload_failed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductPostedResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    //upload image only.
    private void uploadImage(String title, String description, String name, String email, String phone, String address, int priceFrom, int priceTo, int category) {

        MultipartBody.Part photoBody1;
        MultipartBody.Part photoBody2;
        MultipartBody.Part photoBody3;
        MultipartBody.Part photoBody4;
        MultipartBody.Part photoBody5;

        if (path1 != null) {
            File file = new File(path1);
            photoBody1 = multipartBoy(file);
        } else {
            photoBody1 = returnNull();
        }

        if (path2 != null) {
            File file = new File(path2);
            photoBody2 = multipartBoy(file);
        } else {
            photoBody2 = returnNull();
        }

        if (path3 != null) {
            File file = new File(path3);
            photoBody3 = multipartBoy(file);
        } else {
            photoBody3 = returnNull();
        }

        if (path4 != null) {
            File file = new File(path4);
            photoBody4 = multipartBoy(file);
        } else {
            photoBody4 = returnNull();
        }

        if (path5 != null) {
            File file = new File(path5);
            photoBody5 = multipartBoy(file);
        } else {
            photoBody5 = returnNull();
        }

        dialog.show();

        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<ImagePostResponse> call = apiService.uploadImage(accessToken, photoBody1, photoBody2, photoBody3, photoBody4, photoBody5);
        call.enqueue(new Callback<ImagePostResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImagePostResponse> call, @NonNull Response<ImagePostResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String image = response.body().getDescription();
                        uploadProduct(title, description, name, email, phone, address, priceFrom, priceTo, category, image);

                    } else {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImagePostResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof TextView) {
                ((TextView) view).setError(message);
            }
        }
    }

    //check share preference
    private void checkSharePreference() {
        String firstName = userInformationManager.getUser().getFirstName();
        String lastName = userInformationManager.getUser().getLastName();
        String phone = userInformationManager.getUser().getPhone();
        String desc = userInformationManager.getUser().getDescription();
        String address = userInformationManager.getUser().getAddress();
        String email = userInformationManager.getUser().getEmail();
        coordinate = userInformationManager.getUser().getMapCondinate();

        if (!firstName.equals("N/A") && !lastName.equals("N/A")) {
            String fullName = firstName + " " + lastName;
            editContactName.setText(fullName);
        }
        if (!phone.equals("N/A")) {
            editContactPhone.setText(phone);
        }

        if (!desc.equals("N/A")) {
            editDescription.setText(desc);
        }

        if (!address.equals("N/A")) {
            editContactAddress.setText(address);
        }

        if (!email.equals("N/A")) {
            editContactEmail.setText(email);
        }
    }

    private Bitmap bitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
        return bitmap;
    }

    private void showGallery() {
        ImagePicker.create(this)
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Norton E-Commerce") // folder selection title
                .toolbarImageTitle("Select Photo") // image selection title
                .toolbarArrowColor(Color.WHITE)
                .single() // single mode
                .multi() // multi mode (default mode)
                .limit(1) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .enableLog(false) // disabling log
                .start(); // start image picker activity with request code
    }

    private void onClickRemovePhoto() {
        remove1.setOnClickListener(view -> {
            image1.setImageResource(R.drawable.ic_add);
            remove1.setVisibility(View.GONE);
            path1 = null;
        });

        remove2.setOnClickListener(view -> {
            image2.setImageResource(R.drawable.ic_add);
            remove2.setVisibility(View.GONE);
            path2 = null;
        });
        remove3.setOnClickListener(view -> {
            image3.setImageResource(R.drawable.ic_add);
            remove3.setVisibility(View.GONE);
            path3 = null;
        });

        remove4.setOnClickListener(view -> {
            image4.setImageResource(R.drawable.ic_add);
            remove4.setVisibility(View.GONE);
            path4 = null;
        });

        remove5.setOnClickListener(view -> {
            image5.setImageResource(R.drawable.ic_add);
            remove5.setVisibility(View.GONE);
            path5 = null;
        });


    }

    private void onClickBrowseToGallery() {

        image1.setOnClickListener(view -> {
            clickBrowse = "image1";
            showGallery();
        });
        image2.setOnClickListener(view -> {
            clickBrowse = "image2";
            showGallery();
        });

        image3.setOnClickListener(view -> {
            clickBrowse = "image3";
            showGallery();
        });

        image4.setOnClickListener(view -> {
            clickBrowse = "image4";
            showGallery();
        });

        image5.setOnClickListener(view -> {
            clickBrowse = "image5";
            showGallery();
        });
    }

    private MultipartBody.Part multipartBoy(File file) {
        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData("image_file[]", file.getName(), requestBodyImage);
    }

    private MultipartBody.Part returnNull() {
        RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        return MultipartBody.Part.createFormData("image_file[]", "", requestBodyImage);
    }

    private void initView() {
        image1 = findViewById(R.id.image_thumbnail_one);
        image2 = findViewById(R.id.image_thumbnail_two);
        image3 = findViewById(R.id.image_thumbnail_three);
        image4 = findViewById(R.id.image_thumbnail_four);
        image5 = findViewById(R.id.image_thumbnail_five);

        imageBack = findViewById(R.id.imageBack);

        remove1 = findViewById(R.id.buttonRemove1);
        remove2 = findViewById(R.id.buttonRemove2);
        remove3 = findViewById(R.id.buttonRemove3);
        remove4 = findViewById(R.id.buttonRemove4);
        remove5 = findViewById(R.id.buttonRemove5);

        remove1.setVisibility(View.GONE);
        remove2.setVisibility(View.GONE);
        remove3.setVisibility(View.GONE);
        remove4.setVisibility(View.GONE);
        remove5.setVisibility(View.GONE);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editPriceStart = findViewById(R.id.editPriceFrom);
        editPriceEnd = findViewById(R.id.editPriceTo);
        editContactName = findViewById(R.id.editName);
        editContactEmail = findViewById(R.id.editEmail);
        editContactAddress = findViewById(R.id.editAddress);
        editContactPhone = findViewById(R.id.editPhone);
        textCategory = findViewById(R.id.textCategory);
        buttonUpload = findViewById(R.id.buttonUpload);

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
