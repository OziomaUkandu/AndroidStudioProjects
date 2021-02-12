package com.example.simplelogin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText, passwordEditText, numberEditText, emailEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.activity_main_nameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);
        numberEditText = findViewById(R.id.activity_main_numberEditText);
        emailEditText = findViewById(R.id.activity_main_emailEditText);
        loginButton = findViewById(R.id.activity_main_loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0 && numberEditText.getText().length() > 0 &&
                        emailEditText.getText().length() > 0) {
                    String toastMessage = "Thank You, your request is being processed";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                } else {
                    String toastMessage = "Username, Password, Number or Email aren't completed";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}