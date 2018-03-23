package vichitpov.com.fbs.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.VailidationEmail;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;

public class EditProductBuyActivity extends AppCompatActivity {

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

    private TextView textCategory, textChangeCategory;
    private Button buttonUpdate;
    private ImageView imageBack;

    private ProductResponse.Data productResponse;
    private ProductPostedResponse.Data updatedProduct;
    private UserInformationManager userInformationManager;
    private ApiService apiService;
    private SpotsDialog dialog;
    private String productType, selectCategoryId, mapCoordinate, accessToken, email;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_buy);

        initView();
        checkIntent();
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        apiService = ServiceGenerator.createService(ApiService.class);
        Validator validator = new Validator(this);
        dialog = new SpotsDialog(this, "Updating...");

        //listener
        buttonUpdate.setOnClickListener(view -> validator.validate());

        imageBack.setOnClickListener(view -> {
            finish();
        });

        textChangeCategory.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChooseCategoryActivity.class);
            startActivityForResult(intent, 10);
        });

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

                    updateProduct();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //result back selected category id from ChooseCategoryActivity
        if (requestCode == 10 && resultCode != RESULT_CANCELED) {
            textCategory.setText(data.getStringExtra("CategoryName"));
            selectCategoryId = data.getStringExtra("CategoryId");

            Log.e("pppp", "onActivityResult: " + data.getStringExtra("CategoryName"));
            Log.e("pppp", "onActivityResult: " + selectCategoryId);
        }
    }

    //update product
    private void updateProduct() {
        accessToken = userInformationManager.getUser().getAccessToken();
        String title = editTitle.getText().toString();
        String desc = editDescription.getText().toString();
        String name = editContactName.getText().toString();
        String phone = editContactPhone.getText().toString();
        String address = editContactAddress.getText().toString();
        Double priceStart = Double.valueOf(editPriceStart.getText().toString());
        Double priceEnd = Double.valueOf(editPriceEnd.getText().toString());
        int category = Integer.parseInt(selectCategoryId);

        Log.e("pppp", category + "");

        if (accessToken.equals("N/A")) {
            startActivity(new Intent(this, StartLoginActivity.class));
        } else {
            dialog.show();
            Call<ProductPostedResponse> call = apiService.updateProduct(productId, accessToken, title, desc, priceStart, priceEnd, productType, category, "", name, phone, email, address, mapCoordinate);
            call.enqueue(new Callback<ProductPostedResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductPostedResponse> call, @NonNull Response<ProductPostedResponse> response) {
                    if (response.isSuccessful()) {
                        updatedProduct = response.body().getData();
                        backReturnResultCodeToRefreshItemUpdated();
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Log.e("pppp", response.message() + " = " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductPostedResponse> call, @NonNull Throwable t) {
                    dialog.dismiss();
                    t.printStackTrace();
                    Log.e("pppp", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    //send back result code to refresh list that selected to update.
    private void backReturnResultCodeToRefreshItemUpdated() {
        productResponse.setTitle(updatedProduct.getTitle());
        productResponse.getCategory().setId(updatedProduct.getCategory().getId());
        productResponse.setDescription(updatedProduct.getDescription());
        productResponse.getPrice().get(0).setMin(String.valueOf(updatedProduct.getPrice().get(0).getMin()));
        productResponse.getPrice().get(0).setMax(String.valueOf(updatedProduct.getPrice().get(0).getMax()));
        productResponse.setContactphone(updatedProduct.getContactphone());
        productResponse.setContactemail(email); //validation email above
        productResponse.setContactaddress(updatedProduct.getContactaddress());

        Intent returnResult = new Intent();
        returnResult.putExtra(AnyConstant.PRODUCT_LIST, productResponse);
        setResult(AnyConstant.EDIT_PRODUCT_BUY_CODE, returnResult);
        finish();
    }

    //check intent
    private void checkIntent() {
        productResponse = (ProductResponse.Data) getIntent().getSerializableExtra(AnyConstant.PRODUCT_LIST);
        if (productResponse != null) {

            productId = productResponse.getId();

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

            productType = productResponse.getType();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
