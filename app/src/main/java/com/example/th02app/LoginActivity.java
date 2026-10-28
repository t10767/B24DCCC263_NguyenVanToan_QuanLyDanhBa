package com.example.th02app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String FIXED_USERNAME = "admin";
    public static final String FIXED_PASSWORD = "123456";

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvError = findViewById(R.id.tvError);
        View btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            tvError.setText(R.string.error_empty_credentials);
            tvError.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.error_empty_credentials, Toast.LENGTH_SHORT).show();
            return;
        }

        if (FIXED_USERNAME.equals(username) && FIXED_PASSWORD.equals(password)) {
            tvError.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            tvError.setText(R.string.error_invalid_credentials);
            tvError.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
        }
    }
}

