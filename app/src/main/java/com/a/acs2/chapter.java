package com.a.acs2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class chapter extends AppCompatActivity implements SelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

//        String value;
//        Bundle bundle = getIntent().getExtras();
//        value = bundle.getString("extra");
//        JSONObject jsonObject = new JSONObject(value);
//
//        RecyclerView recyclerView = findViewById(R.id.sub);
//
//
////        JSONObject m1Object = response.getJSONObject("m1");
//        List<Item> items = new ArrayList<Item>();
//
//        Iterator<String> keys = value.keys();
//        while (keys.hasNext()) {
//            String key = keys.next();
//            String value = response.getString(key);
//            items.add(new Item(key, value,R.drawable.math));
//        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(new sub_adapter(getApplicationContext(),items,chapter.this));



















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
            recyclerView.setAdapter(new sub_adapter(this, items, chapter.this));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSON parsing error", Toast.LENGTH_SHORT).show();
        }

//        String url="http://192.168.0.101:3004/miss.json";
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            // Extract the value of the "gg" key
//                            JSONObject m1Object = response.getJSONObject("m1");
//                            JSONObject sorolObject = m1Object.getJSONObject("sorol");
//                            List<Item> items = new ArrayList<Item>();
//
//                            Iterator<String> keys = response.keys();
//                            while (keys.hasNext()) {
//                                String key = keys.next();
//                                String value = response.getString(key);
//                                items.add(new Item(key, value,R.drawable.math));
//                            }
//
//                            // Set up RecyclerView
//
//                            //        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////                          recyclerView.setAdapter(new sub_adapter(getApplicationContext(),items,this));
//
//
//                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
////                            ItemAdapter adapter = new ItemAdapter(itemList);
////                            recyclerView.setAdapter(adapter);
//                            recyclerView.setAdapter(new sub_adapter(getApplicationContext(),items,chapter.this));
////                            String lec1Value = sorolObject.getString("Lec1");
//
//                            // Display the value in a Toast
//                            //Toast.makeText(getApplicationContext(), lec1Value, Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(), "JSON parsing error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle error
//                        Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        // Add the request to the RequestQueue
//        queue.add(jsonObjectRequest);





        //--------------------------------------------------------------------------------

//        RecyclerView recyclerView = findViewById(R.id.sub);
//        List<Item> items = new ArrayList<Item>();
//        items.add(new Item("Math","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new Item("Chemistry","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new Item("Physics",value,R.drawable.math));
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new sub_adapter(getApplicationContext(),items,this));




    }

    @Override
    public void onIteamClicked(Item item, int position) {

        String value = item.getEmail();
        Intent intent = new Intent(getApplicationContext(), lec.class);
        intent.putExtra("extra", value);
        startActivity(intent);

//        if(item.getName()=="Math"){
//            String value = item.getEmail();
//            Intent intent = new Intent(getApplicationContext(), math.class);
//            intent.putExtra("sample_name", value);
//            startActivity(intent);
//
//        } else if (item.getName()=="Chemistry") {
//
//        }else {
//
//        }


    }
}