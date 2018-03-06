package vichitpov.com.fbs.ui.activities;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.ConvertBitmap;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

public class EditProductActivity extends AppCompatActivity {

    private ImageView image1, image2, image3, image4, image5;
    private EditText editTitle, editDescription, editPriceStart, editPriceEnd, editContactName, editContactEmail, editContactAddress, editContactPhone;
    private TextView textCategory, textChangeCategory;
    private String selectCategoryId, checkImageIndex;
    private String pathPhoto1, pathPhoto2, pathPhoto3, pathPhoto4, pathPhoto5;
    private List<String> listImage;
    private int currentImageSize;
    private com.oswaldogh89.picker.ImagePicker imagePicker;


    private UserInformationManager userInformationManager;
    private Button remove1, remove2, remove3, remove4, remove5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);


        image1 = findViewById(R.id.image_thumbnail_one);
        image2 = findViewById(R.id.image_thumbnail_two);
        image3 = findViewById(R.id.image_thumbnail_three);
        image4 = findViewById(R.id.image_thumbnail_four);
        image5 = findViewById(R.id.image_thumbnail_five);

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
        textChangeCategory = findViewById(R.id.textChangeCategory);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        listImage = new ArrayList<>();

        checkIntent();
        imagePicker = findViewById(R.id.picker);
        imagePicker.setMainactivity(this);
        imagePicker.SetBorderImageColor("#075e55");
        imagePicker.enableDelateAll(false);


        ArrayList<String> urls = new ArrayList<>();

        imagePicker.addImagesFromUrl(urls);

        Button btnUpload = findViewById(R.id.buttonAddPhoto);
        btnUpload.setOnClickListener(view -> {
            HashMap<Integer, String> images = imagePicker.GetPathImages();
            for (Map.Entry entry : images.entrySet()) {
                Log.v("pppp", entry.getValue() + "");
            }
        });


        textChangeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ChooseCategoryActivity.class);
            startActivityForResult(intent, 10);
        });

//        image1.setOnClickListener(view -> {
//            checkImageIndex = "image1";
//            browsePhoto();
//        });
//        image2.setOnClickListener(view -> {
//            checkImageIndex = "image2";
//            browsePhoto();
//        });
//        image3.setOnClickListener(view -> {
//            checkImageIndex = "image3";
//            browsePhoto();
//        });
//        image4.setOnClickListener(view -> {
//            checkImageIndex = "image4";
//            browsePhoto();
//        });
//        image5.setOnClickListener(view -> {
//            checkImageIndex = "image5";
//            browsePhoto();
//        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//
//            List<Image> photoList = ImagePicker.getImages(data);
//            currentImageSize = photoList.size();
//
//            Log.e("pppp", listImage.size() + "");
//
//            if (listImage.size() == 0) {
//                for (int i = 0; i < photoList.size(); i++) {
//                    listImage.add(photoList.get(i).getPath());
//                }
//
//                for (int j = 0; j < photoList.size(); j++) {
//                    if (j == 0) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(0));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image1.setImageBitmap(bitmap);
//                        remove1.setVisibility(View.VISIBLE);
//
//                    } else if (j == 1) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(1));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image2.setImageBitmap(bitmap);
//                        remove2.setVisibility(View.VISIBLE);
//
//                    } else if (j == 2) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(2));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image3.setImageBitmap(bitmap);
//                        remove3.setVisibility(View.VISIBLE);
//
//                    } else if (j == 3) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(3));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image4.setImageBitmap(bitmap);
//                        remove4.setVisibility(View.VISIBLE);
//                    } else if (j == 4) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(4));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image5.setImageBitmap(bitmap);
//                        remove5.setVisibility(View.VISIBLE);
//                    }
//                }
//
//            } else if (listImage.size() > 4) {
//
//                Toast.makeText(this, "Photo limit 5.", Toast.LENGTH_SHORT).show();
//
//            } else {
//                for (int i = 0; i < photoList.size(); i++) {
//                    listImage.add(currentImageSize, photoList.get(i).getPath());
//                }
//
//                for (int j = currentImageSize; j < listImage.size(); j++) {
//                    if (j == 0) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(0));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image1.setImageBitmap(bitmap);
//                        remove1.setVisibility(View.VISIBLE);
//
//                    } else if (j == 1) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(1));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image2.setImageBitmap(bitmap);
//                        remove2.setVisibility(View.VISIBLE);
//
//                    } else if (j == 2) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(2));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image3.setImageBitmap(bitmap);
//                        remove3.setVisibility(View.VISIBLE);
//
//                    } else if (j == 3) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(3));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image4.setImageBitmap(bitmap);
//                        remove4.setVisibility(View.VISIBLE);
//                    } else if (j == 4) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(listImage.get(4));
//                        bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400);
//                        image5.setImageBitmap(bitmap);
//                        remove5.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//
//        }
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("pppp requestCode", requestCode + "");
        Log.e("pppp resultCode", requestCode + "");
        Log.e("pppp RESULT_OK", RESULT_OK + "");

        switch (requestCode) {
            case com.oswaldogh89.picker.ImagePicker.REQUEST_CAMERA:
                if (resultCode != RESULT_CANCELED && data != null) {
                    imagePicker.AddNewImage(data);
                }

                break;
            case com.oswaldogh89.picker.ImagePicker.REQUEST_GALLERY:
                if (resultCode != RESULT_CANCELED && data != null) {
                    imagePicker.AddNewImage(data);
                }

                break;
//            case 10:
//                textCategory.setText(imageReturnedIntent.getStringExtra("CategoryName"));
//                selectCategoryId = imageReturnedIntent.getStringExtra("CategoryId");
//
//                break;
        }
    }


    private void browsePhoto() {
//        ImagePicker.create(this)
//                .folderMode(true) // folder mode (false by default)
//                .toolbarFolderTitle("Norton E-Commerce") // folder selection title
//                .toolbarImageTitle("Select Photo") // image selection title
//                .toolbarArrowColor(Color.WHITE)
//                .single() // single mode
//                .multi() // multi mode (default mode)
//                .limit(5) // max images can be selected (99 by default)
//                .showCamera(true) // show camera or not (true by default)
//                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
//                .enableLog(false) // disabling log
//                .start(); // start image picker activity with request code
    }

    private void checkIntent() {

        ProductResponse.Data productResponse = (ProductResponse.Data) getIntent().getSerializableExtra("ProductList");

//        if (productResponse != null) {
//            if (productResponse.getProductimages() != null) {
//                for (int i = 0; i < productResponse.getProductimages().size(); i++)
//                    if (productResponse.getProductimages().get(i) != null) {
//                        if (i == 0) {
//                            Picasso.with(getApplicationContext())
//                                    .load(productResponse.getProductimages().get(i))
//                                    .placeholder(R.drawable.ef_image_placeholder)
//                                    .error(R.drawable.ic_unavailable)
//                                    .into(image1);
//                        } else if (i == 1) {
//                            Picasso.with(getApplicationContext())
//                                    .load(productResponse.getProductimages().get(i))
//                                    .placeholder(R.drawable.ef_image_placeholder)
//                                    .error(R.drawable.ic_unavailable)
//                                    .into(image2);
//                        } else if (i == 2) {
//                            Picasso.with(getApplicationContext())
//                                    .load(productResponse.getProductimages().get(i))
//                                    .placeholder(R.drawable.ef_image_placeholder)
//                                    .error(R.drawable.ic_unavailable)
//                                    .into(image3);
//                        } else if (i == 3) {
//                            Picasso.with(getApplicationContext())
//                                    .load(productResponse.getProductimages().get(i))
//                                    .placeholder(R.drawable.ef_image_placeholder)
//                                    .error(R.drawable.ic_unavailable)
//                                    .into(image4);
//                        } else if (i == 4) {
//                            Picasso.with(getApplicationContext())
//                                    .load(productResponse.getProductimages().get(i))
//                                    .placeholder(R.drawable.ef_image_placeholder)
//                                    .error(R.drawable.ic_unavailable)
//                                    .into(image5);
//                        }
//
//                    }
//            }

//            if (productResponse.getTitle() != null)
//                editTitle.setText(productResponse.getTitle());
//
//            if (productResponse.getDescription() != null)
//                editDescription.setText(productResponse.getDescription());
//
//            if (productResponse.getCategory().getCategoryName() != null) {
//                textCategory.setText(productResponse.getCategory().getCategoryName());
//            }
//
//            if (productResponse.getPrice().get(0).getMin() != null && productResponse.getPrice().get(0).getMax() != null) {
//                editPriceEnd.setText(productResponse.getPrice().get(0).getMax());
//                editPriceStart.setText(productResponse.getPrice().get(0).getMin());
//            }
//
//            if (productResponse.getContactname() != null) {
//                editContactName.setText(productResponse.getContactname());
//            }
//
//
//            if (productResponse.getContactphone() != null) {
//                editContactPhone.setText(productResponse.getContactphone());
//            }
//
//            if (productResponse.getContactaddress() != null) {
//                editContactAddress.setText(productResponse.getContactaddress());
//            }
//
//            if (productResponse.getContactemail() != null) {
//                editContactEmail.setText(productResponse.getContactemail());
//            }
//
//
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
