package vichitpov.com.fbs.ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.ContactAdapter;
import vichitpov.com.fbs.base.BaseAppCompatActivity;
import vichitpov.com.fbs.base.Retrofit;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.FavoriteResponse;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;
import vichitpov.com.fbs.ui.fragment.ShowMapFragment;

public class DetailProductActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ImageView imageBack, imageContact, imageFavorite;
    private BannerSlider bannerSlider;
    private Button buttonCall;
    private TextView textTitle, textToolbar, textDescription, textPrice, textName, textPhone, textEmail, textAddress;
    private String getPhone, accessToken;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ApiService apiService;
    private LinearLayout linearContact;
    private UserInformationManager userInformationManager;
    private int page = 1;
    private int totalPage;
    private int productId;
    private boolean isFavorite;
    private boolean isContact;
    private List<Integer> favoriteIdList;
    private ProgressDialog dialogRemove, dialogAdd, dialogNotification;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        favoriteIdList = new ArrayList<>();
        dialogRemove = new ProgressDialog(this);
        dialogRemove.setMessage(getString(R.string.dialog_remove_favorite));

        dialogAdd = new ProgressDialog(this);
        dialogAdd.setMessage(getString(R.string.dialog_add_favorite));

        dialogNotification = new ProgressDialog(this);
        dialogNotification.setMessage(getString(R.string.dialog_check));

        apiService = ServiceGenerator.createService(ApiService.class);
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        accessToken = userInformationManager.getUser().getAccessToken();

        initView();
        setUpRecycler();
        getIntentFromAnotherActivity();
        checkExistUserFavorite();

        imageBack.setOnClickListener(this);
        buttonCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + getPhone));
            startActivity(intent);
        });

        imageFavorite.setOnClickListener(view -> {
            if (!accessToken.equals("N/A")) {
                if (isFavorite) {
                    removeFavorite();
                } else {
                    addFavorite();
                }
            } else {
                startActivity(new Intent(getApplicationContext(), StartLoginActivity.class));
            }
        });
        imageContact.setOnClickListener(view -> {
            Log.e("pppp", "isContact: " + isContact);
            if (isContact) {
                checkExistUserContact();
            } else {
                Toast.makeText(this, "This product you already sent the notification!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //add favorite to user's list.
    private void addFavorite() {
        Call<FavoriteResponse> call = apiService.addFavorite(accessToken, productId);
        dialogAdd.show();
        call.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteResponse> call, @NonNull Response<FavoriteResponse> response) {
                if (response.isSuccessful()) {
                    isFavorite = true;
                    imageFavorite.setImageResource(R.drawable.ic_favorite_selected);
                    Toast.makeText(getApplicationContext(), R.string.text_added_favorite, Toast.LENGTH_SHORT).show();
                    dialogAdd.dismiss();

                } else {
                    isFavorite = false;
                    imageFavorite.setImageResource(R.drawable.ic_favorite_un_select);
                    dialogAdd.dismiss();
                    Toast.makeText(getApplicationContext(), "Error connection. please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                isFavorite = false;
                imageFavorite.setImageResource(R.drawable.ic_favorite_un_select);
                dialogAdd.dismiss();
                Toast.makeText(getApplicationContext(), "Server problem!", Toast.LENGTH_SHORT).show();
                //Log.e("pppp", "onFailure: " + t.getMessage());
            }
        });
    }

    //remove favorite.
    private void removeFavorite() {
        dialogRemove.show();
        Call<String> call = apiService.removeFavorite(accessToken, productId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 204) {
                        isFavorite = false;
                        imageFavorite.setImageResource(R.drawable.ic_favorite_un_select);
                        Toast.makeText(getApplicationContext(), R.string.text_removed_favorite, Toast.LENGTH_SHORT).show();
                        dialogRemove.dismiss();
                    }
                } else {
                    isFavorite = true;
                    imageFavorite.setImageResource(R.drawable.ic_favorite_selected);
                    dialogRemove.dismiss();
                    Toast.makeText(getApplicationContext(), "Error connection. please try again", Toast.LENGTH_SHORT).show();
                    //Log.e("pppp", "Remove Else: " + response.code() + " = " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                t.printStackTrace();
                imageFavorite.setImageResource(R.drawable.ic_favorite_selected);
                dialogRemove.dismiss();
                Toast.makeText(getApplicationContext(), "Server problem!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //check exist contact
    private void checkExistUserContact() {
        if (!accessToken.equals("N/A")) {
            Call<JSONObject> call = apiService.addContact(accessToken, productId);
            call.enqueue(new Callback<JSONObject>() {
                @Override
                public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                    if (response.isSuccessful()) {
                        //Log.e("pppp", "if: " + response.code() + " = " + response.message());
                        isContact = false;
                        imageContact.setImageResource(R.drawable.ic_notification_selected);
                        Toast.makeText(DetailProductActivity.this, "You have alerted the owner of this item!", Toast.LENGTH_LONG).show();

                    } else if (response.code() == 400) {
                        // 400 = Bad Request = id already contact
                        //Log.e("pppp", "else if: " + response.code() + " = " + response.message());

                        isContact = false;
                        Toast.makeText(DetailProductActivity.this, "Alert has already sent!", Toast.LENGTH_LONG).show();
                        imageContact.setImageResource(R.drawable.ic_notification_selected);

                    } else {
                        isContact = true;
                        //Log.e("pppp", "else: " + response.code() + " = " + response.message());
                        imageContact.setImageResource(R.drawable.ic_notification_un_select);
                    }
                }

                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("pppp", "onFailure: " + t.getMessage());

                    isContact = true;
                    imageContact.setImageResource(R.drawable.ic_notification_un_select);
                }
            });
        } else {
            startActivity(new Intent(this, StartLoginActivity.class));
        }
    }

    //check exist user favorite
    private void checkExistUserFavorite() {
        if (!accessToken.equals("N/A")) {
            Call<ProductResponse> call = apiService.getAllUserFavorite(accessToken, page);
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    if (response.isSuccessful()) {
                        totalPage = response.body().getMeta().getLastPage();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            favoriteIdList.add(response.body().getData().get(i).getId());
                        }

                        setUpFavoriteIcon();

                        page = page + 1;
                        if (page <= totalPage) {
                            checkExistUserFavorite();
                        }

                    } else {
                        Log.e("pppp", "else: " + response.code() + " = " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    t.printStackTrace();
                    //Log.e("pppp", "onFailure: " + t.getMessage());
                }
            });


        }
    }

    private void setUpFavoriteIcon() {
        if (favoriteIdList.size() != 0) {
            //Log.e("pppp", "productId: " + productId);
            for (int i = 0; i < favoriteIdList.size(); i++) {
                if (favoriteIdList.get(i) == productId) {
                    //Log.e("pppp", "if productId: " + favoriteIdList.get(i));
                    isFavorite = true;
                    imageFavorite.setImageResource(R.drawable.ic_favorite_selected);
                    return;
                } else {
                    //Log.e("pppp", "else productId: " + favoriteIdList.get(i));
                    isFavorite = false;
                    imageFavorite.setImageResource(R.drawable.ic_favorite_un_select);
                }
            }
        }
    }

    private void setUpRecycler() {
        adapter = new ContactAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @SuppressLint("SetTextI18n")
    private void getIntentFromAnotherActivity() {
        ProductResponse.Data productResponse = (ProductResponse.Data) getIntent().getSerializableExtra("productList");
        ProductPostedResponse.Data notificationList = (ProductPostedResponse.Data) getIntent().getSerializableExtra("NotificationList");

        if (productResponse != null) {
            productId = productResponse.getId();
            countView(productId);
            getPhone = productResponse.getContactphone();

            //check product already sent notification
            if (!accessToken.equals("N/A")) {
                if (productResponse.getContactme().getDatacontact().size() > 0) {
                    String userId = userInformationManager.getUser().getId();
                    for (int i = 0; i < productResponse.getContactme().getDatacontact().size(); i++) {
                        String userContactedId = String.valueOf(productResponse.getContactme().getDatacontact().get(i).getId());

                        //Log.e("pppp", userId + " = " + userContactedId);
                        if (userId.equals(userContactedId)) {
                            isContact = false;
                            imageContact.setImageResource(R.drawable.ic_notification_selected);
                            //Log.e("pppp", "userId == userContactedId");
                            break;
                        } else {
                            //Log.e("pppp", "userId != userContactedId");
                            isContact = true;
                            imageContact.setImageResource(R.drawable.ic_notification_un_select);
                        }
                    }
                } else {
                    //Log.e("pppp", "else null");
                    isContact = true;
                    imageContact.setImageResource(R.drawable.ic_notification_un_select);
                }

            } else {
                //Log.e("pppp", "N/A");
                isContact = true;
                imageContact.setImageResource(R.drawable.ic_notification_un_select);
            }


            if (productResponse.getTitle() != null) {
                textToolbar.setText(productResponse.getTitle());
            }

            if (productResponse.getContactme().getDatacontact().size() > 0) {
                //Log.e("pppp", "contactMe: " + productResponse.getContactme().getDatacontact().size());
                if (productResponse.getContactme().getDatacontact().size() == 0) {
                    linearContact.setVisibility(View.GONE);
                } else {
                    adapter.addItem(productResponse.getContactme().getDatacontact());
                    recyclerView.setAdapter(adapter);
                }
            }

            List<Banner> imageSliderList = new ArrayList<>();
            if (productResponse.getProductimages().size() != 0) {
                for (int i = 0; i < productResponse.getProductimages().size(); i++) {
                    imageSliderList.add(new RemoteBanner(productResponse.getProductimages().get(i)));
                }
                bannerSlider.setBanners(imageSliderList);
            }

            String priceFrom = productResponse.getPrice().get(0).getMin();
            String priceTo = productResponse.getPrice().get(0).getMax();

            int priceSubFrom = Integer.parseInt(priceFrom.substring(priceFrom.lastIndexOf(".") + 1));
            int priceSubTo = Integer.parseInt(priceTo.substring(priceTo.lastIndexOf(".") + 1));

            if (priceSubFrom == 0) {
                priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));
            }

            if (priceSubTo == 0) {
                priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
            }

            textTitle.setText(productResponse.getTitle());
            textPrice.setText("Price: " + priceFrom + "$ - " + priceTo + "$");
            textDescription.setText(productResponse.getDescription());
            textAddress.setText("Contact Address: " + productResponse.getContactaddress());
            textName.setText("Contact Name: " + productResponse.getContactname());
            textPhone.setText("Contact Phone: " + productResponse.getContactphone());

            if (productResponse.getContactemail().equals("norton@null.com") || productResponse.getContactemail() == null) {
                textEmail.setVisibility(View.GONE);
            } else {
                textEmail.setVisibility(View.VISIBLE);
                textEmail.setText("Contact Email: " + productResponse.getContactemail());
            }

            if (productResponse.getContactmapcoordinate() != null) {
                //Log.e("pppp", productResponse.getContactmapcoordinate());
                //Log.e("pppp", productResponse.getContactname());
                ShowMapFragment showMapFragment = new ShowMapFragment();
                Bundle bundle = new Bundle();
                bundle.putString("LatLng", productResponse.getContactmapcoordinate());
                bundle.putString("Name", productResponse.getContactname());
                showMapFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layoutShowMap, showMapFragment)
                        .commit();
            }

        } else if (notificationList != null) {
            linearContact.setVisibility(View.GONE);
            productId = notificationList.getId();
            countView(productId);
            getPhone = notificationList.getContactphone();

            //check product already sent notification
            if (!accessToken.equals("N/A")) {
                if (notificationList.getContactme().getData().size() > 0) {
                    String userId = userInformationManager.getUser().getId();
                    for (int i = 0; i < notificationList.getContactme().getData().size(); i++) {
                        String userContactedId = String.valueOf(notificationList.getContactme().getData().get(i).getId());

                        //Log.e("pppp", userId + " = " + userContactedId);
                        if (userId.equals(userContactedId)) {
                            isContact = false;
                            imageContact.setImageResource(R.drawable.ic_notification_selected);
                            //Log.e("pppp", "userId == userContactedId");
                            break;
                        } else {
                            //Log.e("pppp", "userId != userContactedId");
                            isContact = true;
                            imageContact.setImageResource(R.drawable.ic_notification_un_select);
                        }
                    }
                } else {
                    //Log.e("pppp", "else null");
                    isContact = true;
                    imageContact.setImageResource(R.drawable.ic_notification_un_select);
                }

            } else {
                isContact = true;
                imageContact.setImageResource(R.drawable.ic_notification_un_select);
            }

            if (notificationList.getTitle() != null) {
                textToolbar.setText(notificationList.getTitle());
            }

            List<Banner> imageSliderList = new ArrayList<>();
            if (notificationList.getProductimages().size() != 0) {
                for (int i = 0; i < notificationList.getProductimages().size(); i++) {
                    imageSliderList.add(new RemoteBanner(notificationList.getProductimages().get(i))
                            .setPlaceHolder(getResources().getDrawable(R.drawable.ic_placeholder_banner))
                            .setErrorDrawable(getResources().getDrawable(R.drawable.ic_error_banner))
                    );
                }
                bannerSlider.setBanners(imageSliderList);
            }

            String priceFrom = notificationList.getPrice().get(0).getMin();
            String priceTo = notificationList.getPrice().get(0).getMax();

            int priceSubFrom = Integer.parseInt(priceFrom.substring(priceFrom.lastIndexOf(".") + 1));
            int priceSubTo = Integer.parseInt(priceTo.substring(priceTo.lastIndexOf(".") + 1));

            if (priceSubFrom == 0) {
                priceFrom = notificationList.getPrice().get(0).getMin().substring(0, notificationList.getPrice().get(0).getMax().indexOf("."));
            }

            if (priceSubTo == 0) {
                priceTo = notificationList.getPrice().get(0).getMax().substring(0, notificationList.getPrice().get(0).getMin().indexOf("."));
            }

            textTitle.setText(notificationList.getTitle());
            textPrice.setText("Price: " + priceFrom + "$ - " + priceTo + "$");
            textDescription.setText(notificationList.getDescription());
            textAddress.setText("Contact Address: " + notificationList.getContactaddress());
            textName.setText("Contact Name: " + notificationList.getContactname());
            textPhone.setText("Contact Phone: " + notificationList.getContactphone());

            if (notificationList.getContactemail().equals("norton@null.com") || notificationList.getContactemail() == null) {
                textEmail.setVisibility(View.GONE);
            } else {
                textEmail.setVisibility(View.VISIBLE);
                textEmail.setText("Contact Email: " + notificationList.getContactemail());
            }

            if (notificationList.getContactmapcoordinate() != null) {
                //Log.e("pppp", notificationList.getContactmapcoordinate());
                //Log.e("pppp", notificationList.getContactname());
                ShowMapFragment showMapFragment = new ShowMapFragment();
                Bundle bundle = new Bundle();
                bundle.putString("LatLng", notificationList.getContactmapcoordinate());
                bundle.putString("Name", notificationList.getContactname());
                showMapFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layoutShowMap, showMapFragment)
                        .commit();
            }

        }
    }

    private void countView(int id) {
        UserInformationManager userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            Retrofit.countView(userInformationManager.getUser().getAccessToken(), id);
        }
    }

    private void initView() {
        imageBack = findViewById(R.id.image_back);
        imageContact = findViewById(R.id.image_contact);
        imageFavorite = findViewById(R.id.image_favorite);
        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        textPrice = findViewById(R.id.textPrice);
        textName = findViewById(R.id.textName);
        textPhone = findViewById(R.id.textPhone);
        textEmail = findViewById(R.id.textEmail);
        textAddress = findViewById(R.id.textAddress);
        textToolbar = findViewById(R.id.textToolbar);
        bannerSlider = findViewById(R.id.bannerSlider);
        buttonCall = findViewById(R.id.buttonCall);
        recyclerView = findViewById(R.id.recyclerView);
        linearContact = findViewById(R.id.linearContact);

    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
