package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.ConvertBitmap;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ImagePostResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

import static vichitpov.com.fbs.base.Retrofit.multipartBoy;
import static vichitpov.com.fbs.base.Retrofit.returnNull;

public class EditProductActivity extends AppCompatActivity {

    private ImageView image1, image2, image3, image4, image5;
    private EditText editTitle, editDescription, editPriceStart, editPriceEnd, editContactName, editContactEmail, editContactAddress, editContactPhone;
    private TextView textCategory, textChangeCategory;
    private String selectCategoryId, clickImage;
    private String currentPathPhoto1 = "null", currentPathPhoto2 = "null", currentPathPhoto3 = "null", currentPathPhoto4 = "null", currentPathPhoto5 = "null";
    private List<String> listImage;
    private UserInformationManager userInformationManager;
    private List<String> pathImageList;
    private Button buttonUpdate;
    private ApiService apiService;
    private String accessToken;
    private SpotsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ImageView imageBrowseGallery = findViewById(R.id.imageBrowsePhoto);
        initView();

        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        accessToken = userInformationManager.getUser().getAccessToken();
        dialog = new SpotsDialog(this, "Updating...");
        listImage = new ArrayList<>();
        pathImageList = new ArrayList<>();


        checkIntent();


        textChangeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            startActivityForResult(intent, 10);
        });

        image1.setOnClickListener(view -> {
            clickImage = "image1";
            browsePhoto();
        });
        image2.setOnClickListener(view -> {
            clickImage = "image2";
            browsePhoto();
        });
        image3.setOnClickListener(view -> {
            clickImage = "image3";
            browsePhoto();
        });
        image4.setOnClickListener(view -> {
            clickImage = "image4";
            browsePhoto();
        });
        image5.setOnClickListener(view -> {
            clickImage = "image5";
            browsePhoto();
        });

        imageBrowseGallery.setOnClickListener(view -> {
            if (listImage.size() < 5) {
                browsePhoto();
            } else {
                Toast.makeText(this, "Photo limit 5.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        MultipartBody.Part photoBody1;
        MultipartBody.Part photoBody2;
        MultipartBody.Part photoBody3;
        MultipartBody.Part photoBody4;
        MultipartBody.Part photoBody5;

        if (pathImageList.get(0) != null) {
            File file = new File(pathImageList.get(0));
            photoBody1 = multipartBoy(file);
        } else {
            photoBody1 = returnNull();
        }

        if (pathImageList.get(1) != null) {
            File file = new File(pathImageList.get(1));
            photoBody2 = multipartBoy(file);
        } else {
            photoBody2 = returnNull();
        }

        if (pathImageList.get(2) != null) {
            File file = new File(pathImageList.get(2));
            photoBody3 = multipartBoy(file);
        } else {
            photoBody3 = returnNull();
        }

        if (pathImageList.get(3) != null) {
            File file = new File(pathImageList.get(3));
            photoBody4 = multipartBoy(file);
        } else {
            photoBody4 = returnNull();
        }

        if (pathImageList.get(4) != null) {
            File file = new File(pathImageList.get(4));
            photoBody5 = multipartBoy(file);
        } else {
            photoBody5 = returnNull();
        }

        dialog.show();
        apiService = ServiceGenerator.createService(ApiService.class);
        Call<ImagePostResponse> call = apiService.uploadImage(accessToken, photoBody1, photoBody2, photoBody3, photoBody4, photoBody5);
        call.enqueue(new Callback<ImagePostResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImagePostResponse> call, @NonNull Response<ImagePostResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(EditProductActivity.this, "Upload image successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(EditProductActivity.this, "Updated failed!", Toast.LENGTH_SHORT).show();
                    Log.e("pppp out", response.code() + " = " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImagePostResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                dialog.dismiss();
                Toast.makeText(EditProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.e("pppp onFailure ", t.getMessage());
            }
        });


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

            switch (clickImage) {
                case "image1":
                    displayImage(pathList.get(0).getPath(), image1);
                    break;
                case "image2":
                    displayImage(pathList.get(0).getPath(), image2);
                    break;
                case "image3":
                    displayImage(pathList.get(0).getPath(), image3);
                    break;
                case "image4":
                    displayImage(pathList.get(0).getPath(), image4);
                    break;
                case "image5":
                    displayImage(pathList.get(0).getPath(), image5);
                    break;
            }
        }


////            int currentImageSize = photoList.size();
////
////            if (listImage.size() == 0) {
////                for (int i = 0; i < photoList.size(); i++) {
////                    listImage.add(photoList.get(i).getPath());
////                }
////                for (int j = 0; j < photoList.size(); j++) {
////                    if (j == 0) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(0));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image1.setImageBitmap(bitmap);
////                    } else if (j == 1) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(1));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image2.setImageBitmap(bitmap);
////                    } else if (j == 2) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(2));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image3.setImageBitmap(bitmap);
////
////                    } else if (j == 3) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(3));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image4.setImageBitmap(bitmap);
////                    } else if (j == 4) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(4));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image5.setImageBitmap(bitmap);
////                    }
////                }
////            } else if (listImage.size() > 4) {
////                Toast.makeText(this, "Photo limit 5.", Toast.LENGTH_SHORT).show();
////            } else {
////                for (int i = 0; i < photoList.size(); i++) {
////                    listImage.add(currentImageSize, photoList.get(i).getPath());
////                }
////                for (int j = currentImageSize; j < listImage.size(); j++) {
////                    if (j == 0) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(0));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image1.setImageBitmap(bitmap);
////
////                    } else if (j == 1) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(1));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image2.setImageBitmap(bitmap);
////
////                    } else if (j == 2) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(2));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image3.setImageBitmap(bitmap);
////
////                    } else if (j == 3) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(3));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image4.setImageBitmap(bitmap);
////
////                    } else if (j == 4) {
////                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(4));
////                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
////                        image5.setImageBitmap(bitmap);
////                    }
////                }
//            }

    }


    //display image after got from gallery and convert image bitmap.
    private void displayImage(String path, ImageView imageView) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
        imageView.setImageBitmap(bitmap);
        pathImageList.add(path);
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
            if (productResponse.getProductimages() != null) {
                for (int i = 0; i < productResponse.getProductimages().size(); i++) {
                    if (productResponse.getProductimages().get(i) != null) {
                        if (i == 0) {
                            currentPathPhoto1 = productResponse.getProductimages().get(i).substring(36);
                            Picasso.with(getApplicationContext())
                                    .load(productResponse.getProductimages().get(i))
                                    .placeholder(R.drawable.ef_image_placeholder)
                                    .error(R.drawable.ic_unavailable)
                                    .into(image1);
                        } else if (i == 1) {
                            currentPathPhoto2 = productResponse.getProductimages().get(i).substring(36);
                            Picasso.with(getApplicationContext())
                                    .load(productResponse.getProductimages().get(i))
                                    .placeholder(R.drawable.ef_image_placeholder)
                                    .error(R.drawable.ic_unavailable)
                                    .into(image2);
                        } else if (i == 2) {
                            currentPathPhoto3 = productResponse.getProductimages().get(i).substring(36);
                            Picasso.with(getApplicationContext())
                                    .load(productResponse.getProductimages().get(i))
                                    .placeholder(R.drawable.ef_image_placeholder)
                                    .error(R.drawable.ic_unavailable)
                                    .into(image3);
                        } else if (i == 3) {
                            currentPathPhoto4 = productResponse.getProductimages().get(i).substring(36);
                            Picasso.with(getApplicationContext())
                                    .load(productResponse.getProductimages().get(i))
                                    .placeholder(R.drawable.ef_image_placeholder)
                                    .error(R.drawable.ic_unavailable)
                                    .into(image4);
                        } else if (i == 4) {
                            currentPathPhoto5 = productResponse.getProductimages().get(i).substring(36);
                            Picasso.with(getApplicationContext())
                                    .load(productResponse.getProductimages().get(i))
                                    .placeholder(R.drawable.ef_image_placeholder)
                                    .error(R.drawable.ic_unavailable)
                                    .into(image5);
                        }
                    }
                }
            }


            if (productResponse.getTitle() != null)
                editTitle.setText(productResponse.getTitle());

            if (productResponse.getDescription() != null)
                editDescription.setText(productResponse.getDescription());

            if (productResponse.getCategory().getCategoryName() != null) {
                textCategory.setText(productResponse.getCategory().getCategoryName());
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
                editContactEmail.setText(productResponse.getContactemail());
            }
        }
    }

    private void initView() {
        image1 = findViewById(R.id.image_thumbnail_one);
        image2 = findViewById(R.id.image_thumbnail_two);
        image3 = findViewById(R.id.image_thumbnail_three);
        image4 = findViewById(R.id.image_thumbnail_four);
        image5 = findViewById(R.id.image_thumbnail_five);

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
