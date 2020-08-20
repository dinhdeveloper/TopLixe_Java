package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.java.music.R;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.common.SharePrefs;
import com.java.music.model.CustomerModel;
import com.java.music.model.Token;
import com.java.music.model.UserEntityBean;
import com.java.music.model.UserEntityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    LinearLayout layoutLogin;

    APIService apiService;
    Token token = new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = APIUntil.getServer();

        SharePrefs prefs = new SharePrefs(getApplicationContext());
        CustomerModel sha = prefs.getUserModel();
        if (sha!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        layoutLogin = findViewById(R.id.layoutLogin);



        layoutLogin.setOnClickListener(v -> {
            if (edtUsername.getText().toString().trim().isEmpty() || edtPassword.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Nhập đúng tên đăng nhập hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
            }else {
                UserEntityModel model = new UserEntityModel();
                model.setApiToken("7E277A25310E4D1AA3E6B0F0615AD39A");
                UserEntityBean bean = new UserEntityBean();
                bean.setUsername(edtUsername.getText().toString().trim());
                bean.setPassword(edtPassword.getText().toString().trim());
                model.setUserEntity(bean);

                apiService.login(model).enqueue(new Callback<CustomerModel>() {
                    @Override
                    public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                        if (response.isSuccessful()){
                            CustomerModel customerModel = response.body();
                            SharePrefs prefs = new SharePrefs(getApplicationContext());
                            prefs.saveUserModel(customerModel);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerModel> call, Throwable t) {
                        Log.e("onFailure",t.getMessage());
                    }
                });
            }
        });
    }
}