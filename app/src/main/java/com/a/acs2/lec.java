package com.a.acs2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.a.acs2.R.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONObject;

public class lec extends AppCompatActivity implements SelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);



        String jsonString;
        Bundle bundle = getIntent().getExtras();
        jsonString = bundle.getString("extra");

        // Initialize the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.sub);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        try {
            // Convert the JSON string to a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);
            List<Item> items = new ArrayList<>();

            // Iterate over the keys of the JSONObject
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = jsonObject.getString(key);


                items.add(new Item(key, value,1, R.drawable.math,1,1,false,"id"));
            }

            // Set the adapter for the RecyclerView
            recyclerView.setAdapter(new sub_adapter(this, items, lec.this));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSON parsing error", Toast.LENGTH_SHORT).show();
        }






    }

    @UnstableApi
    @Override
    public void onIteamClicked(Item item, int position) {

//        if(item.getName()=="Math"){




        try {
            String value1 = item.getEmail();
            Bundle bundle0 = getIntent().getExtras();
            String jsonString = bundle0.getString("extra");


            JSONObject jsonObject = new JSONObject(value1);
            String src = jsonObject.getString("src");
            String title = jsonObject.getString("title");
            Intent intent = new Intent(getApplicationContext(), video.class);
            intent.putExtra("src", src);
            intent.putExtra("title", title);
            intent.putExtra("json", jsonString);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSON parsing error", Toast.LENGTH_SHORT).show();
        }

//            String value = item.getEmail();
//            Intent intent = new Intent(getApplicationContext(), video.class);
//            intent.putExtra("src", value);
//            startActivity(intent);


////
//        } else if (item.getName()=="Chemistry") {
//
//        }else {
//
//        }


    }
}