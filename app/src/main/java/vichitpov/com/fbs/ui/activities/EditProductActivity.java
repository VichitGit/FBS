package vichitpov.com.fbs.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.ConvertBitmap;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;

public class EditProductActivity extends AppCompatActivity {

    private ImageView image1, image2, image3, image4, image5;
    private EditText editTitle, editDescription, editPriceStart, editPriceEnd, editContactName, editContactEmail, editContactAddress, editContactPhone;
    private TextView textCategory, textChangeCategory;
    private String selectCategoryId, checkImageIndex;
    private String currentPathPhoto1, currentPathPhoto2, currentPathPhoto3, currentPathPhoto4, currentPathPhoto5;
    private List<String> listImage;
    private UserInformationManager userInformationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ImageView imageBrowseGallery = findViewById(R.id.imageBrowsePhoto);
        initView();

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        listImage = new ArrayList<>();

        checkIntent();


        textChangeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            startActivityForResult(intent, 10);
        });

        image1.setOnClickListener(view -> {
            checkImageIndex = "image1";
        });
        image2.setOnClickListener(view -> {
            checkImageIndex = "image2";
        });
        image3.setOnClickListener(view -> {
            checkImageIndex = "image3";
        });
        image4.setOnClickListener(view -> {
            checkImageIndex = "image4";
        });
        image5.setOnClickListener(view -> {
            checkImageIndex = "image5";
        });

        imageBrowseGallery.setOnClickListener(view -> {
            if (listImage.size() < 5) {
                browsePhoto();
            } else {
                Toast.makeText(this, "Photo limit 5.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {

        List<String> newListPath = new ArrayList<>();
        for (int i = 0; i < listImage.size(); i++) {
            if (listImage.get(i) != null) {
                newListPath.add(listImage.get(i));
            }
        }
    }


    private void updateProduct() {


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<Image> photoList = ImagePicker.getImages(data);
            int currentImageSize = photoList.size();

            if (listImage.size() == 0) {
                for (int i = 0; i < photoList.size(); i++) {
                    listImage.add(photoList.get(i).getPath());
                }
                for (int j = 0; j < photoList.size(); j++) {
                    if (j == 0) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(0));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image1.setImageBitmap(bitmap);
                    } else if (j == 1) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(1));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image2.setImageBitmap(bitmap);
                    } else if (j == 2) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(2));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image3.setImageBitmap(bitmap);

                    } else if (j == 3) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(3));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image4.setImageBitmap(bitmap);
                    } else if (j == 4) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(4));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image5.setImageBitmap(bitmap);
                    }
                }
            } else if (listImage.size() > 4) {
                Toast.makeText(this, "Photo limit 5.", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < photoList.size(); i++) {
                    listImage.add(currentImageSize, photoList.get(i).getPath());
                }
                for (int j = currentImageSize; j < listImage.size(); j++) {
                    if (j == 0) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(0));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image1.setImageBitmap(bitmap);

                    } else if (j == 1) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(1));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image2.setImageBitmap(bitmap);

                    } else if (j == 2) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(2));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image3.setImageBitmap(bitmap);

                    } else if (j == 3) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(3));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image4.setImageBitmap(bitmap);

                    } else if (j == 4) {
                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(4));
                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
                        image5.setImageBitmap(bitmap);
                    }
                }
            }

            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 10 && resultCode == RESULT_OK) {
                textCategory.setText(data.getStringExtra("CategoryName"));
                selectCategoryId = data.getStringExtra("CategoryId");
            }
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
                .limit(5) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .enableLog(false) // disabling log
                .start(); // start image picker activity with request code
    }

    private void checkIntent() {
        ProductResponse.Data productResponse = (ProductResponse.Data) getIntent().getSerializableExtra("ProductList");
        if (productResponse != null) {
            if (productResponse.getProductimages() != null) {
                for (int i = 0; i < productResponse.getProductimages().size(); i++)
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
