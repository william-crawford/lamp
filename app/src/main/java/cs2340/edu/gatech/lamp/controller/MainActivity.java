package cs2340.edu.gatech.lamp.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cs2340.edu.gatech.lamp.R;
import cs2340.edu.gatech.lamp.model.LoginValidator;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyApp", "Started");
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        Button register = findViewById(R.id.btn_register);

    }

    private void login() {
        LoginValidator.loginResult result = LoginValidator.processLogin(
                username.getText().toString(),
                password.getText().toString()
        );

        switch (result) {
            case ADMIN:
                //doAdminThings
                break;
            case SHELTER:
                //shelterThings
                break;
            case HOMELESS:
                goToHomeless();
                break;
            case DENIED:
                password.setText("");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Login failed")
                        .setMessage("The login information provided was incorrect")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }

    }

    private void goToHomeless() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
