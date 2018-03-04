package vichitpov.com.fbs.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

public class EditProductActivity extends AppCompatActivity {

    private ImageView image1, image2, image3, image4, image5;
    private EditText editTitle, editDescription, editPriceStart, editPriceEnd, editContactName, editContactEmail, editContactAddress, editContactPhone;
    private TextView textCategory;

    private UserInformationManager userInformationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

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

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));

        checkIntent();


    }

    private void checkIntent() {

        ProductResponse.Data productResponse = (ProductResponse.Data) getIntent().getSerializableExtra("ProductList");

        if (productResponse != null) {
            if (productResponse.getTitle() != null)
                editTitle.setText(productResponse.getTitle());

            if (productResponse.getDescription() != null)
                editDescription.setText(productResponse.getDescription());

//            if (productResponse.getCategory().getCategoryName() != null) {
//                textCategory.setText(productResponse.getCategory().getCategoryName());
//            }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
