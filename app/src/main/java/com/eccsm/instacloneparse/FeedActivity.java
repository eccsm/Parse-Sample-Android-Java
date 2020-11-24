package com.eccsm.instacloneparse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {


    private ArrayList<String> usernameFromParse;
    private ArrayList<Bitmap> imageFromParse;
    private ArrayList<String> commentFromParse;
    private RecyclerAdapter recyclerAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout)
        {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!=null)
                    {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent intent = new Intent(FeedActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        if(item.getItemId() == R.id.post)
        {
            Intent intent2Post = new Intent(FeedActivity.this,PostActivity.class);
            startActivity(intent2Post);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        commentFromParse = new ArrayList<>();
        usernameFromParse = new ArrayList<>();
        imageFromParse = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerAdapter(imageFromParse,usernameFromParse,commentFromParse);

        recyclerView.setAdapter(recyclerAdapter);

        download();
    }


    public void download()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Posts");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e!=null)
                {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(objects.size()>0)
                    {
                        for(final ParseObject object : objects)
                        {
                            ParseFile parseFile = (ParseFile) object.get("image");

                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (e==null && data !=null)
                                    {
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

                                        imageFromParse.add(bitmap);
                                        commentFromParse.add(object.getString("comment"));
                                        usernameFromParse.add(object.getString("user"));

                                        recyclerAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}