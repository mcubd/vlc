package com.a.acs2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class act extends AppCompatActivity {

    private JSONObject responseObject;
    private String rootjson;
    public static String jsonn0;
    private String[] keysArray;
    ShimmerFrameLayout shimmerFrameLayout;

    @UnstableApi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);


        ShapeableImageView imgg = findViewById(R.id.imageview02);
        ShapeableImageView imgg2 = findViewById(R.id.imageview022);

        int displayWidth = getResources().getDisplayMetrics().widthPixels;

        ViewGroup.LayoutParams params = imgg.getLayoutParams();
        params.width = (int) (displayWidth * 0.8);
        ;
        params.height = (int) (displayWidth * 0.55); // This will auto-adjust the height based on the aspect ratio
        imgg.setLayoutParams(params);

        ViewGroup.LayoutParams params1 = imgg2.getLayoutParams();
        params1.width = (int) (displayWidth * 0.8);
        ;
        params1.height = (int) (displayWidth * 0.55); // This will auto-adjust the height based on the aspect ratio
        imgg2.setLayoutParams(params1);


        imgg.setShapeAppearanceModel(
                imgg.getShapeAppearanceModel()
                        .toBuilder()
                        .setAllCornerSizes(22f) // Set radius in pixels or dp
                        .build()
        );

        imgg2.setShapeAppearanceModel(
                imgg2.getShapeAppearanceModel()
                        .toBuilder()
                        .setAllCornerSizes(22f) // Set radius in pixels or dp
                        .build()
        );
        imgg.setVisibility(View.VISIBLE);
        imgg2.setVisibility(View.VISIBLE);

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();






                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
              Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
              if(getSupportFragmentManager().getBackStackEntryCount() >0){


            if (item.getItemId() == R.id.subs && !currentFragment.getClass().getSimpleName().equals("subs")   ) {

//                if(currentFragment.getClass().getSimpleName().equals("frag") || currentFragment.getClass().getSimpleName().equals("lecs") ){
//                     getSupportFragmentManager().popBackStack("FRAGMENT_11j", 0);
//                }else{
//
//
//                subs subs = new subs();
//                Bundle bundle = new Bundle();
//                bundle.putStringArray("keysArray", keysArray);
//                subs.setArguments(bundle);
//
//                getSupportFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
//                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
//                        .replace(R.id.fragment_container, subs)
//                        .addToBackStack("FRAGMENT_11")
//                        .commit(); }
                if(currentFragment.getClass().getSimpleName().equals("Dmanager") && getSupportFragmentManager().getBackStackEntryCount()==1){
                          Toast.makeText(getApplicationContext(), "Restartingggggg", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext() ,act.class);
                    finish();
                    startActivity(intent);


                }else{


                getSupportFragmentManager().popBackStack("FRAGMENT_11j", 0);
                return true;
                }

            } else if (item.getItemId() == R.id.dmanager && !currentFragment.getClass().getSimpleName().equals("Dmanager")   ) {
                Dmanager Dmanager = new Dmanager();


                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
//                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.fragment_container, Dmanager)
                        .addToBackStack("FRAGMENT_11dg")
                        .commit();
                return true;


            } else {

            }
             }else{




//                  if (item.getItemId() == R.id.dmanager ) {
//                Dmanager Dmanager = new Dmanager();
//                getSupportFragmentManager().beginTransaction()
//                        .setReorderingAllowed(true)
////                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
//                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
//                        .replace(R.id.fragment_container, Dmanager)
//                        .addToBackStack("FRAGMENT_11dg")
//                        .commit();
//                return true;


//            }




              }



            return false;
        });














        String url = "https://api.jsonsilo.com/public/6c8ea427-81b5-4c1b-8bd1-ce2f07a4d20f";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        rootjson = String.valueOf(response);
                        jsonn0 = String.valueOf(response);

                        Iterator<String> keysIterator = response.keys();
                        List<String> keysList = new ArrayList<>();

                        while (keysIterator.hasNext()) {
                            keysList.add(keysIterator.next());
                        }


                        keysArray = keysList.toArray(new String[0]);


                        subs subs0 = new subs();
                        Bundle bundle1 = new Bundle();
                        bundle1.putStringArray("keysArray", keysArray);
                        subs0.setArguments(bundle1);

                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_container, subs0)
                                .addToBackStack("FRAGMENT_11j")
                                .commit();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
 setMyViewVisibility(0);
                        Dmanager Dmanager = new Dmanager();


                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_container, Dmanager)
                                .addToBackStack("FRAGMENT_11dg")
                                .commit();
                           BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.dmanager);
 bottomNavigationView.setSelectedItemId(R.id.dmanager);

                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                         bottomNavigationView.setSelectedItemId(R.id.dmanager);
                         bottomNavigationView.setSelectedItemId(R.id.dmanager);

                    }
                });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);


        //--------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------------------------------------------


//        home home = new home();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, home)
//                .addToBackStack(null)
//                .commit();





    }

    public void setMyViewVisibility(int visibility) {
        if (shimmerFrameLayout != null) {


            shimmerFrameLayout.stopShimmer();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    FrameLayout a = findViewById(R.id.fragment_container);
                    a.setVisibility(View.VISIBLE);
                }
            }, 10);

        }
    }

    public String getYourVariable() {
        return rootjson; // Method to access the variable
    }


    @Override
    public void onBackPressed() {
        // Toast.makeText(this, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            // Pop back to the first fragment ("FRAGMENT_1")
            getSupportFragmentManager().popBackStack("FRAGMENT_11j", 0);
              BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.subs);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            //    getSupportFragmentManager().popBackStack("FRAGMENT_11j", 0);
            // super.onBackPressed();
        } else {
            // If there's only one fragment left, use the default back button behavior
            super.onBackPressed();
        }
    }

}