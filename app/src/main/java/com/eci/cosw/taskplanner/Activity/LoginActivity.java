package com.eci.cosw.taskplanner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eci.cosw.taskplanner.Model.LoginWrapper;
import com.eci.cosw.taskplanner.Model.Token;
import com.eci.cosw.taskplanner.R;
import com.eci.cosw.taskplanner.Service.AuthService;
import com.eci.cosw.taskplanner.Util.RetrofitHttp;
import com.eci.cosw.taskplanner.Util.SharedPreference;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static AuthService authService;
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(1);
    private SharedPreference sharedPreference;
    private RetrofitHttp retrofitHttp;
    private String file;
    private String TOKEN_KEY;
    private String USER_LOGGED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        file = getString(R.string.preference_file_key);
        TOKEN_KEY = getString(R.string.token_key);
        USER_LOGGED = getString(R.string.user_logged);

        sharedPreference = new SharedPreference(this, file);
        retrofitHttp = new RetrofitHttp();

        authService = retrofitHttp.getRetrofit().create(AuthService.class);
    }

    public void login(final View view) {
        view.setEnabled(false); //This prevent interaction while login

        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        final String stringEmail = email.getText().toString();
        final String stringPassword = password.getText().toString();

        if (!stringEmail.matches("")) {
            if (!stringPassword.matches("")) {
                view.setEnabled(false);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LoginWrapper loginWrapper = new LoginWrapper(stringEmail,
                                    stringPassword);
                            Response<Token> response = authService.login(loginWrapper).execute();
                            if (response.isSuccessful()) {
                                Token token = response.body();

                                sharedPreference.save(TOKEN_KEY, token.getAccessToken());

                                sharedPreference.save(USER_LOGGED, stringEmail);

                                startLoginActivity();
                                finish(); //This finishes login activity
                            } else {
                                showErrorLoginMessage(view);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            showErrorLoginMessage(view);
                        }
                    }
                });
            } else {
                password.setError("You must enter a password");
            }
        } else {
            email.setError("You must enter an email");
        }
    }

    public void showErrorLoginMessage(final View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
                Snackbar.make(view, getString(R.string.login_error_message),
                        Snackbar.LENGTH_LONG)
                        .show();

            }
        });
    }

    public void startLoginActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

}

