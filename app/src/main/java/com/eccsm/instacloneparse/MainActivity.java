package com.eccsm.instacloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText emailText,passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ParseUser.getCurrentUser() != null)
        {
            Intent intent2Feed = new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent2Feed);
            finish();
        }

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);


    }

    public void loginClicked(View view)
    {
        ParseUser.logInInBackground(emailText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Welcome " + user.getUsername(), Toast.LENGTH_LONG).show();
                    Intent intent2Feed = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent2Feed);
                    finish();
                }
            }
        });

    }

    public void signUpClicked(View view)
    {
        ParseUser user = new ParseUser();
        user.setUsername(emailText.getText().toString());
        user.setPassword(passwordText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null)
                {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "User Created Successfully!", Toast.LENGTH_LONG).show();
                    Intent intent2Feed = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent2Feed);
                    finish();
                }

            }
        });
    }
}