package com.java.music.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.java.music.R;
import com.java.music.api.APIService;
import com.java.music.api.APIUntil;
import com.java.music.model.UserEntityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    LinearLayout layoutLogin;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = APIUntil.getServer();

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

//        layoutLogin.setOnClickListener(v -> {
//            apiService.login().enqueue(new Callback<List<UserEntityModel>>() {
//                @Override
//                public void onResponse(Call<List<UserEntityModel>> call, Response<List<UserEntityModel>> response) {
//                    if (response.isSuccessful()) {
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<UserEntityModel>> call, Throwable t) {
//                    Log.e("onFailure", t.getMessage());
//                }
//            });
//        });
    }
}