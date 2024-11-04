package com.a.acs2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;



public class subject extends AppCompatActivity  {

    private MyViewModel myViewModel;
    private JSONObject responseObject;
    private String rootjson;
    public static String jsonn;


    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);



        RecyclerView recyclerView = findViewById(R.id.sub);





        String url="https://api.jsonsilo.com/public/6c8ea427-81b5-4c1b-8bd1-ce2f07a4d20f";
        myViewModel = new MyViewModel();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseObject = response;
                            jsonn = String.valueOf(response);

                            rootjson = String.valueOf(response);


                            myViewModel.setJsonData(String.valueOf(response));
                            // Extract the value of the "gg" key
                            Log.d("MyFragment", "JSON Data: " + String.valueOf(response));














                            Iterator<String> keysIterator = response.keys();
            List<String> keysList = new ArrayList<>();

            // Add keys to the list
            while (keysIterator.hasNext()) {
                keysList.add(keysIterator.next());
            }

            // Convert the List to an array if necessary
            String[] keysArray = keysList.toArray(new String[0]);
            Log.d("MyFragment", "JSON Data-- " + String.valueOf(keysArray));


                            subs subs = new subs();


            Bundle bundle = new Bundle();
            bundle.putStringArray("keysArray", keysArray);




                            subs.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, subs)
                    .addToBackStack("FRAGMENT_1")
                    .commit();





//                            JSONObject jsonObject = new JSONObject(response);

                            // Find the value of the key "h"
//                            String hValue = findValueByKey(response, "h");
//
//                            // Print the value
//                            System.out.println("Value of h: " + hValue);







                            JSONObject m1Object = response.getJSONObject("m1");
                            JSONObject sorolObject = m1Object.getJSONObject("sorol");
                            List<Item> items = new ArrayList<Item>();

                            Iterator<String> keys = response.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = response.getString(key);
                                items.add(new Item(key, value,1,R.drawable.math,1,1,false,"id"));
                            }

                            // Set up RecyclerView

                            //        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                          recyclerView.setAdapter(new sub_adapter(getApplicationContext(),items,this));


                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                            ItemAdapter adapter = new ItemAdapter(itemList);
//                            recyclerView.setAdapter(adapter);
                         //   recyclerView.setAdapter(new sub_adapter(getApplicationContext(),items,subject.this));
//                            String lec1Value = sorolObject.getString("Lec1");

                            // Display the value in a Toast
                            //Toast.makeText(getApplicationContext(), lec1Value, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);





        //--------------------------------------------------------------------------------

//        RecyclerView recyclerView = findViewById(R.id.sub);
//        List<Item> items = new ArrayList<Item>();
//        items.add(new Item("Math","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new Item("Chemistry","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new Item("Physics","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new sub_adapter(getApplicationContext(),items,this));


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == id.subs){
                startActivity(new Intent(this, Downloads.class));
            }
            return false;
        });


    }


    public String getYourVariable() {
        return rootjson; // Method to access the variable
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(id.fragment_container);




        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            // Pop back to the first fragment ("FRAGMENT_1")
            getSupportFragmentManager().popBackStack("FRAGMENT_1", 0);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            // If there's only one fragment left, use the default back button behavior
            super.onBackPressed();
        }


    }

}