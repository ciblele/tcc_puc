package com.pucminas.crs.login;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.pucminas.crs.MyApplication;
import com.pucminas.crs.R;
import com.pucminas.crs.utils.ApiResponse;
import com.pucminas.crs.utils.Constant;
import com.pucminas.crs.utils.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.email_ad)
    EditText emailAd;

    @BindView(R.id.password)
    EditText password;

    LoginViewModel viewModel;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        progressDialog = Constant.getProgressDialog(this, "Please wait...");

        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        viewModel.loginResponse().observe(this, this::consumeResponse);

    }


    @OnClick(R.id.login)
    void onLoginClicked() {
        if (isValid()) {
            if (!Constant.checkInternetConnection(this)) {
                Toast.makeText(LoginActivity.this,getResources().getString(R.string.erro_rede), Toast.LENGTH_SHORT).show();
            } else {
                viewModel.hitLoginApi(emailAd.getText().toString(), password.getText().toString());
            }

        }
    }

    private boolean isValid() {

        if (emailAd.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this,getResources().getString(R.string.email_valido), Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this,getResources().getString(R.string.senha_valida), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressDialog.show();
                break;

            case SUCCESS:
                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {
        if (!response.isJsonNull()) {
            Log.d("response=", response.toString());
        } else {
            Toast.makeText(LoginActivity.this,getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }
    }
}
