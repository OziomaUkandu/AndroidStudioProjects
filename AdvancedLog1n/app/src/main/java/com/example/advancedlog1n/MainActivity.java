package com.example.advancedlog1n;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
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

    }

    public void onButtonClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   SecondActivity.class);
        myIntent.putExtra("value1", "Thank you Cla your request is being processed");
        startActivity(myIntent);
    }
}