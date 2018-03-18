package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

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
import vichitpov.com.fbs.model.ImageModel;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ImagePostResponse;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class EditProductActivity extends AppCompatActivity {

    //private ImageView image1, image2, image3, image4, image5;
    private EditText editTitle, editDescription, editPriceStart, editPriceEnd, editContactName, editContactEmail, editContactAddress, editContactPhone;
    private TextView textCategory, textChangeCategory;
    private String selectCategoryId;
    private int productId;
    private String phone, name, email, address;
    private UserInformationManager userInformationManager;
    private Button buttonUpdate;
    private ApiService apiService;
    private String accessToken;
    private SpotsDialog dialog;

    private RecyclerView recyclerView;
    private ImageListAdapter adapter;

    private List<ImageModel> imageList = new ArrayList<>();
    private List<String> newImageList = new ArrayList<>();
    private List<String> oldImageList = new ArrayList<>();
    private List<String> finalList = new ArrayList<>();
    private String postType;

    private TextView textAddPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        initView();

        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        accessToken = userInformationManager.getUser().getAccessToken();
        dialog = new SpotsDialog(this, "Updating...");

        setUpRecyclerView();
        checkIntent();

        adapter.onClickRemovePhoto((id, position) -> {
            ImageModel imageModel = imageList.get(position);
            //check condition to delete image type
            if (imageModel.getImageType().equals(ImageModel.URL)) {
                oldImageList.add(imageModel.getImagePath());
            } else if (imageModel.getImageType().equals(ImageModel.URI)) {
                //check list to delete
                if (newImageList.size() != 0) {
                    for (int i = 0; i < newImageList.size(); i++) {
                        if (imageModel.getImagePath().contains(newImageList.get(i))) {
                            newImageList.remove(i);
                        }
                    }
                }
            }
            adapter.refreshData(position);

        });
        textAddPhoto.setOnClickListener(view -> {
            if (imageList.size() == 5) {
                textAddPhoto.setEnabled(false);
                Toast.makeText(this, "Photo limit 5", Toast.LENGTH_SHORT).show();
            } else {
                textAddPhoto.setEnabled(true);
                browsePhoto();
            }
        });
        textChangeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            startActivityForResult(intent, 10);
        });
        buttonUpdate.setOnClickListener(view -> {
            uploadPhoto();
        });
    }

    private void uploadPhoto() {
        StringBuilder stringBuilder = new StringBuilder();
        if (oldImageList.size() != 0) {
            for (int i = 0; i < oldImageList.size(); i++) {
                stringBuilder.append(oldImageList.get(i).substring(36)).append(";");
            }
        }

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
                public void onResponse(Call<ImagePostResponse> call, Response<ImagePostResponse> response) {
                    if (response.isSuccessful()) {
                        String finalUrl;
                        if (oldImageList.size() != 0) {
                            finalUrl = stringBuilder + response.body().getDescription();

                        } else {
                            finalUrl = response.body().getDescription();

                        }
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

            updateProduct(productId, String.valueOf(stringBuilder));

        }
    }

    private void updateProduct(int productId, String imageUrl) {
        int priceStart = Integer.parseInt(editPriceStart.getText().toString());
        int priceEnd = Integer.parseInt(editPriceEnd.getText().toString());
        int category = Integer.parseInt(selectCategoryId);
        String mapCoordinate = userInformationManager.getUser().getMapCondinate();

        Log.e("pppp", "mapCoordinate: " + mapCoordinate);

        Call<ProductPostedResponse> call = apiService.updateProduct(productId, accessToken,
                editTitle.getText().toString(), editDescription.getText().toString(), priceStart,
                priceEnd, postType, category, imageUrl, name, phone, email, address, mapCoordinate);

        call.enqueue(new Callback<ProductPostedResponse>() {
            @Override
            public void onResponse(Call<ProductPostedResponse> call, Response<ProductPostedResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.e("pppp", "2: " + response.body().toString());
                    Toast.makeText(EditProductActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Log.e("pppp", "2: " + response.code() + " = " + response.message());
                    Toast.makeText(EditProductActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductPostedResponse> call, Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Log.e("pppp", "2: " + t.getMessage());


            }
        });


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

        }
    }


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

    private void checkIntent() {
        ProductResponse.Data productResponse = (ProductResponse.Data) getIntent().getSerializableExtra("ProductList");
        if (productResponse != null) {
            productId = productResponse.getId();

            if (productResponse.getProductimages() != null) {
                for (int i = 0; i < productResponse.getProductimages().size(); i++) {
                    if (productResponse.getProductimages().get(i) != null) {
                        imageList.add(new ImageModel(productResponse.getProductimages().get(i), ImageModel.URL));
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
                name = productResponse.getContactname();
            }

            if (productResponse.getContactphone() != null) {
                editContactPhone.setText(productResponse.getContactphone());
                phone = productResponse.getContactphone();
            }

            if (productResponse.getContactaddress() != null) {
                editContactAddress.setText(productResponse.getContactaddress());
                address = productResponse.getContactaddress();

            }

            if (productResponse.getContactemail() != null) {
                email = productResponse.getContactemail();
                editContactEmail.setText(productResponse.getContactemail());
            } else {
                email = "norton@null.com";
            }

            postType = productResponse.getType();
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

        recyclerView = findViewById(R.id.recyclerView);
        textAddPhoto = findViewById(R.id.textAddPhoto);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
