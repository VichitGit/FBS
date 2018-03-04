package vichitpov.com.fbs.ui.activity.profile;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.squareup.picasso.Picasso;

import java.io.File;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.ConvertBitmap;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activity.SettingsActivity;
import vichitpov.com.fbs.ui.activity.login.StartLoginActivity;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textName, textCountSell, textCountBuy, textEmail, textAddress, textPhone, textSaveProfile;
    private UserInformationManager userInformationManager;
    private ImageView imageProfile, imageSetting;
    private String selectedImagePath = "null";
    private SpotsDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView imageBack = findViewById(R.id.image_back);
        ImageView imageChangeProfile = findViewById(R.id.imageChangeProfile);
        LinearLayout linearEditProfile = findViewById(R.id.linear_edit_profile);
        LinearLayout linearSold = findViewById(R.id.linear_sold);
        LinearLayout linearBought = findViewById(R.id.linear_bought);
        LinearLayout linearRePost = findViewById(R.id.linear_reload);

        imageSetting = findViewById(R.id.image_setting);
        imageProfile = findViewById(R.id.imageProfile);
        textName = findViewById(R.id.textName);
        textCountSell = findViewById(R.id.textSold);
        textCountBuy = findViewById(R.id.textBought);
        textEmail = findViewById(R.id.textEmail);
        textAddress = findViewById(R.id.textAddress);
        textPhone = findViewById(R.id.textPhone);
        textSaveProfile = findViewById(R.id.textSaveProfile);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        dialog = new SpotsDialog(this, "Changing...");

        checkPreference();

        imageBack.setOnClickListener(this);
        linearEditProfile.setOnClickListener(this);
        linearSold.setOnClickListener(this);
        linearBought.setOnClickListener(this);
        linearRePost.setOnClickListener(this);
        imageChangeProfile.setOnClickListener(this);
        imageSetting.setOnClickListener(this);
        textSaveProfile.setOnClickListener(this);


    }

    @SuppressLint("SetTextI18n")
    private void checkPreference() {
        String pFirstName = userInformationManager.getUser().getFirstName();
        String pLastName = userInformationManager.getUser().getLastName();
        String pAddress = userInformationManager.getUser().getAddress();
        String pEmail = userInformationManager.getUser().getEmail();
        String pCountBuy = userInformationManager.getUser().getTotalBuyer();
        String pCountSell = userInformationManager.getUser().getTotalSeller();
        String pPhone = userInformationManager.getUser().getPhone();
        String pProfile = userInformationManager.getUser().getProfile();

        if (!pProfile.equals("N/A")) {
            Picasso.with(this)
                    .load(pProfile)
                    .placeholder(R.drawable.ic_user_holder)
                    .error(R.drawable.ic_user_holder)
                    .into(imageProfile);
        }

        if (!pFirstName.equals("N/A") && !pLastName.equals("N/A")) {
            textName.setText(pFirstName + " " + pLastName);
        } else {
            textPhone.setText("What is your phone number?");
        }

        if (!pAddress.equals("N/A")) {
            textAddress.setText(pAddress);
        } else {
            textAddress.setText("Where is your address?");
        }

        if (!pEmail.equals("N/A")) {
            textEmail.setText("Contact email: " + pEmail);
        } else {
            textEmail.setText("norton.e@norton.com");
        }

        if (!pCountBuy.equals("N/A")) {
            textCountBuy.setText("Post buy: " + pCountBuy);
        }

        if (!pCountSell.equals("N/A")) {
            textCountSell.setText("Post sell: " + pCountSell);
        }

        if (!pPhone.equals("N/A")) {
            textPhone.setText("Contact Phone: " + pPhone);
        }


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.image_back)
            finish();
        else if (id == R.id.linear_edit_profile)
            startActivity(new Intent(getApplicationContext(), EditUserProfileActivity.class));
        else if (id == R.id.image_setting)
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        else if (id == R.id.linear_sold)
            startActivity(new Intent(getApplicationContext(), ProductSoldActivity.class));
        else if (id == R.id.linear_bought)
            startActivity(new Intent(getApplicationContext(), ProductBoughtActivity.class));
        else if (id == R.id.linear_reload)
            dialogBottom();
        else if (id == R.id.imageChangeProfile) {
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
        } else if (id == R.id.textSaveProfile) {
            if (!selectedImagePath.equals("null")) {
                ApiService apiService = ServiceGenerator.createService(ApiService.class);
                String accessToken = userInformationManager.getUser().getAccessToken();
                File fileImage = new File(selectedImagePath);

                if (!accessToken.equals("N/A")) {
                    dialog.show();
                    RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
                    MultipartBody.Part profileBody = MultipartBody.Part.createFormData("image_file", fileImage.getName(), requestBodyImage);

                    Call<UserInformationResponse> call = apiService.updateUserImageProfile(accessToken, profileBody);
                    call.enqueue(new Callback<UserInformationResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                userInformationManager.saveInformation(response.body());
                                startActivity(getIntent());
                                finish();
                                overridePendingTransition(0, 0);

                            } else if (response.code() == 401) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));

                            } else {
                                dialog.dismiss();
                                Log.e("pppp else", response.message() + " = " + response.code());

                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserInformationResponse> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            Log.e("pppp onFailure", t.getMessage());
                            dialog.dismiss();
                        }
                    });

                }
            }

        }
    }

    private void uploadImageProfile() {


    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            textSaveProfile.setVisibility(View.VISIBLE);
            imageSetting.setVisibility(View.GONE);
            Image image = ImagePicker.getFirstImageOrNull(data);
            Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());
            bitmap = ConvertBitmap.getResizedBitmap(bitmap, 400); // convert size image
            imageProfile.setImageBitmap(bitmap);
            selectedImagePath = image.getPath();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void dialogBottom() {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setIconTintColorResource(R.color.colorHint)
                .setMenu(R.menu.menu_dialog_bottom_sheet)
                .setItemClickListener(item -> {
                    if (item.getItemId() == R.id.dialog_bottom_re_post_buy) {
                        startActivity(new Intent(getApplicationContext(), RePostToBuyActivity.class));
                    } else if (item.getItemId() == R.id.dialog_bottom_re_post_sell) {
                        startActivity(new Intent(getApplicationContext(), RePostToSellActivity.class));
                    }

                })
                .createDialog();

        dialog.show();
    }
}



























