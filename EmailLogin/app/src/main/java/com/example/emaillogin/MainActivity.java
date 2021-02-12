package com.example.emaillogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        Button buttonSend = findViewById(R.id.activity_main_loginButton);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        String recipientList = emailEditText.getText().toString();
        String[] recipients = recipientList.split(",");//if i choose more than 1 email

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);//Takes string array of email recipients
        intent.setType("message/rfc822");//only opens email clients
        startActivity(Intent.createChooser(intent, "Email"));
    }
}