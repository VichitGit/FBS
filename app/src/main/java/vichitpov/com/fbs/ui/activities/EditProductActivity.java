package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.ImageListAdapter;
import vichitpov.com.fbs.base.VailidationEmail;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.model.ImageModel;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ImagePostResponse;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class EditProductActivity extends AppCompatActivity {

    @NotEmpty(message = "Required title")
    private EditText editTitle;
    @NotEmpty(message = "Required description")
    private EditText editDescription;
    @NotEmpty(message = "Required price")
    private EditText editPriceStart;
    @NotEmpty(message = "Required price")
    private EditText editPriceEnd;
    @NotEmpty(message = "Required contact name")
    private EditText editContactName;
    private EditText editContactEmail;
    @NotEmpty(message = "Required address")
    private EditText editContactAddress;
    private EditText editContactPhone;

    private TextView textCategory, textChangeCategory, textAddPhoto;
    private int productId;
    private String selectCategoryId, accessToken, postType, mapCoordinate, email;
    private Button buttonUpdate;
    private ImageView imageBack, imageEmpty;
    private ApiService apiService;
    private SpotsDialog dialog;

    private RecyclerView recyclerView;
    private ImageListAdapter adapter;
    private ProductPostedResponse.Data updatedProduct;

    private ProductResponse.Data productResponse;
    private List<ImageModel> imageList = new ArrayList<>();
    private List<String> newImageList = new ArrayList<>();
    private List<String> currentImageList = new ArrayList<>();
    private List<String> imageMustToDeleteList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        initView();

        Validator validator = new Validator(this);
        UserInformationManager userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        apiService = ServiceGenerator.createService(ApiService.class);
        accessToken = userInformationManager.getUser().getAccessToken();
        dialog = new SpotsDialog(this, "Updating...");

        setUpRecyclerView();
        checkIntent();

        //click remove image
        adapter.onClickRemovePhoto((id, position) -> {
            ImageModel imageModel = imageList.get(position);
            //remove current image
            if (imageModel.getImageType().equals(ImageModel.URL)) {
                imageMustToDeleteList.add(imageModel.getImagePath());
                currentImageList.remove(imageModel.getImagePath());

            } else if (imageModel.getImageType().equals(ImageModel.URI)) { //remove new image selected
                //check list to delete new image selected
                if (newImageList.size() != 0) {
                    for (int i = 0; i < newImageList.size(); i++) {
                        if (imageModel.getImagePath().contains(newImageList.get(i))) {
                            newImageList.remove(i);
                        }
                    }
                }
            }
            adapter.refreshData(position);
            if (imageList.size() == 0) {
                imageEmpty.setVisibility(View.VISIBLE);
            }

        });
        textAddPhoto.setOnClickListener(view -> {
            if (imageList.size() == 5) {
                textAddPhoto.setClickable(false);
                Toast.makeText(this, "Photo limit 5", Toast.LENGTH_SHORT).show();
                return;
            }

            textAddPhoto.setClickable(true);
            browsePhoto();
        });
        textChangeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            startActivityForResult(intent, 10);
        });
        buttonUpdate.setOnClickListener(view -> {
            validator.validate();
        });
        imageBack.setOnClickListener(view -> finish());

        //validation all editText
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                email = editContactEmail.getText().toString();

                if (!email.isEmpty() && !VailidationEmail.isEmailValid(email)) {
                    editContactEmail.setError("Invalid email(example@gmail.com)");
                    editContactEmail.findFocus();
                    return;
                } else {
                    if (email.isEmpty()) {
                        email = AnyConstant.EMPTY_EMAIL;
                    }
                    uploadPhoto();
                }
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getApplicationContext());

                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else if (view instanceof TextView) {
                        ((TextView) view).setError(message);
                    }
                }
            }
        });
    }

    //upload photo before update product
    private void uploadPhoto() {
        if (newImageList.size() != 0) {
            MultipartBody.Part[] multipartBody = new MultipartBody.Part[newImageList.size()];
            for (int i = 0; i < newImageList.size(); i++) {
                File file = new File(newImageList.get(i));
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                multipartBody[i] = MultipartBody.Part.createFormData("image_file[]", file.getName(), requestBody);
            }

            dialog.show();
            Call<ImagePostResponse> call = apiService.uploadMultipartImage(accessToken, multipartBody);
            call.enqueue(new Callback<ImagePostResponse>() {
                @Override
                public void onResponse(@NonNull Call<ImagePostResponse> call, @NonNull Response<ImagePostResponse> response) {
                    if (response.isSuccessful()) {
                        //loop substring to get endpoint of image
                        StringBuilder currentUrl = new StringBuilder();
                        if (currentImageList.size() != 0) {
                            for (int i = 0; i < currentImageList.size(); i++) {
                                currentUrl.append(currentImageList.get(i).substring(36) + ";");
                            }
                        }

                        String finalUrl = currentUrl + response.body().getDescription();
                        //upload full product after upload image success
                        updateProduct(productId, finalUrl);

                    } else {
                        dialog.dismiss();
                        Log.e("pppp", "else = " + response.code() + " = " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ImagePostResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("pppp", "onFailure = " + t.getMessage());
                    dialog.dismiss();
                }
            });
        } else {
            //when user not change current image.
            //(original current image)
            StringBuffer currentUrl = new StringBuffer();
            if (currentImageList.size() > 0) {
                for (int i = 0; i < currentImageList.size(); i++) {
                    if (i == currentImageList.size() - 1) {
                        currentUrl.append(currentImageList.get(i).substring(36));
                    } else {
                        currentUrl.append(currentImageList.get(i).substring(36)).append(";");
                    }
                }
            }

            dialog.show();
            updateProduct(productId, String.valueOf(currentUrl));
        }
    }

    //update full product after upload new image selected
    private void updateProduct(int productId, String imageUrl) {
        String title = editTitle.getText().toString();
        String desc = editDescription.getText().toString();
        String name = editContactName.getText().toString();
        String phone = editContactPhone.getText().toString();
        String address = editContactAddress.getText().toString();
        Double priceStart = Double.valueOf(editPriceStart.getText().toString());
        Double priceEnd = Double.valueOf(editPriceEnd.getText().toString());
        int category = Integer.parseInt(selectCategoryId);


        Call<ProductPostedResponse> call = apiService.updateProduct(productId, accessToken, title, desc, priceStart, priceEnd, postType,
                category, imageUrl, name, phone, email, address, mapCoordinate);

        call.enqueue(new Callback<ProductPostedResponse>() {
            @Override
            public void onResponse(Call<ProductPostedResponse> call, Response<ProductPostedResponse> response) {
                if (response.isSuccessful()) {
                    removeOldImage();
                    dialog.dismiss();
                    //return result after update success
                    updatedProduct = response.body().getData();
                    backResultCodeToRefreshItemUpdated();
                    Toast.makeText(EditProductActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();
                    Toast.makeText(EditProductActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductPostedResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditProductActivity.this, "Server Problem", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    //delete current image that changed
    private void removeOldImage() {
        StringBuilder pathImage = new StringBuilder();
        if (imageMustToDeleteList.size() != 0) {

            for (int i = 0; i < imageMustToDeleteList.size(); i++) {
                pathImage.append(imageMustToDeleteList.get(i).substring(36) + ",");
            }

            String urlImage = String.valueOf(pathImage);
            Log.e("pppp", "imageMustRemove: " + urlImage);

            Call<String> call = apiService.removeImageAfterUpdate(accessToken, String.valueOf(pathImage));
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                    if (response.code() == 204) {
                        Log.e("pppp", "removeOldImage isSuccessful");
                    } else {
                        Log.e("pppp", "removeOldImage else " + response.code() + " = " + response.message());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Log.e("pppp", "onFailure: " + t.getMessage());

                }
            });
        }
    }

    private void setUpRecyclerView() {
        adapter = new ImageListAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode != RESULT_CANCELED) {
            textCategory.setText(data.getStringExtra("CategoryName"));
            selectCategoryId = data.getStringExtra("CategoryId");
        }

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            List<Image> pathList = ImagePicker.getImages(data);
            String path = pathList.get(0).getPath();

            imageList.add(new ImageModel(path, ImageModel.URI));
            newImageList.add(path);
            adapter.notifyDataSetChanged();

            if (imageList.size() > 0) {
                imageEmpty.setVisibility(View.GONE);
            }

        }
    }

    private void backResultCodeToRefreshItemUpdated() {

        productResponse.setTitle(updatedProduct.getTitle());
        productResponse.getCategory().setId(updatedProduct.getCategory().getId());
        productResponse.setDescription(updatedProduct.getDescription());
        productResponse.getPrice().get(0).setMin(String.valueOf(updatedProduct.getPrice().get(0).getMin()));
        productResponse.getPrice().get(0).setMax(String.valueOf(updatedProduct.getPrice().get(0).getMax()));
        productResponse.setContactphone(updatedProduct.getContactphone());
        productResponse.setContactemail(email); //validation email above
        productResponse.setContactaddress(updatedProduct.getContactaddress());
        productResponse.setProductimages(updatedProduct.getProductimages());

        Intent returnResult = new Intent();
        returnResult.putExtra(AnyConstant.RETURN_RESULT, productResponse);
        setResult(AnyConstant.EDIT_RESULT, returnResult);
        finish();
    }

    //browse to gallery
    private void browsePhoto() {
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

    //check intent
    private void checkIntent() {
        productResponse = (ProductResponse.Data) getIntent().getSerializableExtra(AnyConstant.PRODUCT_LIST);
        if (productResponse != null) {
            productId = productResponse.getId();

            //set current image to list
            if (productResponse.getProductimages() != null) {
                for (int i = 0; i < productResponse.getProductimages().size(); i++) {
                    if (productResponse.getProductimages().get(i) != null) {
                        imageList.add(new ImageModel(productResponse.getProductimages().get(i), ImageModel.URL));
                        currentImageList.add(productResponse.getProductimages().get(i));
                    }
                }

                adapter.addItem(imageList);
            }

            if (productResponse.getTitle() != null)
                editTitle.setText(productResponse.getTitle());

            if (productResponse.getDescription() != null)
                editDescription.setText(productResponse.getDescription());

            if (productResponse.getCategory().getCategoryName() != null) {
                textCategory.setText(productResponse.getCategory().getCategoryName());
                selectCategoryId = String.valueOf(productResponse.getCategory().getId());
            }

            if (productResponse.getPrice().get(0).getMin() != null && productResponse.getPrice().get(0).getMax() != null) {
                editPriceEnd.setText(productResponse.getPrice().get(0).getMax());
                editPriceStart.setText(productResponse.getPrice().get(0).getMin());
            }

            if (productResponse.getContactname() != null) {
                editContactName.setText(productResponse.getContactname());
            }

            if (productResponse.getContactphone() != null) {
                editContactPhone.setText(productResponse.getContactphone());
            }

            if (productResponse.getContactaddress() != null) {
                editContactAddress.setText(productResponse.getContactaddress());

            }

            if (productResponse.getContactemail() != null) {
                if (!productResponse.getContactemail().equals(AnyConstant.EMPTY_EMAIL))
                    editContactEmail.setText(productResponse.getContactemail());
            }

            postType = productResponse.getType();
            mapCoordinate = productResponse.getContactmapcoordinate();
        }
    }

    private void initView() {
        buttonUpdate = findViewById(R.id.buttonUpload);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editPriceStart = findViewById(R.id.editPriceFrom);
        editPriceEnd = findViewById(R.id.editPriceTo);
        editContactName = findViewById(R.id.editName);
        editContactEmail = findViewById(R.id.editEmail);
        editContactAddress = findViewById(R.id.editAddress);
        editContactPhone = findViewById(R.id.editPhone);
        textCategory = findViewById(R.id.textCategory);
        textChangeCategory = findViewById(R.id.textChangeCategory);
        imageBack = findViewById(R.id.imageBack);
        imageEmpty = findViewById(R.id.imageEmpty);

        recyclerView = findViewById(R.id.recyclerView);
        textAddPhoto = findViewById(R.id.textAddPhoto);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
