package vichitpov.com.fbs.ui.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.UserInformationResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;

public class EditDescriptionActivity extends AppCompatActivity {

    private EditText editDescription;
    private TextView textSave;
    private UserInformationManager userInformationManager;
    private SpotsDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_description);
        editDescription = findViewById(R.id.editDescription);
        textSave = findViewById(R.id.textSave);

        dialog = new SpotsDialog(this, getString(R.string.alert_dialog_updating));
        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            editDescription.setText(userInformationManager.getUser().getDescription());
        }

        textSave.setOnClickListener(view -> {
            if (!editDescription.getText().toString().isEmpty()) {
                updateDescription(editDescription.getText().toString());
            } else {
                editDescription.setError(getString(R.string.validation_description));
            }
        });
    }

    private void updateDescription(String desc) {
        dialog.show();
        ApiService apiService = ServiceGenerator.createService(ApiService.class);
        Call<UserInformationResponse> call = apiService.updateOnlyDescription(userInformationManager.getUser().getAccessToken(), desc);
        call.enqueue(new Callback<UserInformationResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserInformationResponse> call, @NonNull Response<UserInformationResponse> response) {
                if (response.isSuccessful()) {
                    userInformationManager.saveInformation(response.body());
                    Intent intent = new Intent();
                    setResult(AnyConstant.EDIT_DESCRIPTION_CODE, intent);
                    finish();
                    Toast.makeText(EditDescriptionActivity.this, R.string.text_update_success, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    //Log.e("pppp", "else: " + response.code() + " = " + response.message());
                }

            }

            @Override
            public void onFailure(Call<UserInformationResponse> call, Throwable t) {
                t.printStackTrace();
                //Log.e("pppp", "onFailure: " + t.getMessage());
                dialog.dismiss();
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
