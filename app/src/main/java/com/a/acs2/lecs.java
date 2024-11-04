package com.a.acs2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link lecs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lecs extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public lecs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment lecs.
     */
    // TODO: Rename and change types and number of parameters
    public static lecs newInstance(String param1, String param2) {
        lecs fragment = new lecs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lecs, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recd);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] keysArray = getArguments().getStringArray("keysArray");
        String book22 = getArguments().getString("book");
        String subbb = getArguments().getString("sub");
        Log.d("Fragment00", Arrays.toString(keysArray));
       // Toast.makeText(getContext(),  Arrays.toString(keysArray), Toast.LENGTH_SHORT).show();


        List<Item> items = new ArrayList<>();

        for (String key0 : keysArray) {
            String key="Lecture "+key0;
            // Assuming you have a corresponding value you want to set for each key
            // Here, I'm using a placeholder. You can modify this based on your data structure.
            String value = ""; // Replace with actual logic to get value associated with key

            if(subbb.contains("01") && book22.contains("m1")){
                items.add(new Item(key0, key,1, R.drawable.sorollec,1,1,false,"id")); // Add item to the list

            }else if(subbb.contains("06") && book22.contains("m2")){
 items.add(new Item(key0, key,1, R.drawable.konik,1,1,false,"id")); // Add item to the list

            }else if(subbb.contains("08") && book22.contains("p1")){
items.add(new Item(key0, key,1, R.drawable.pmm,1,1,false,"id")); // Add item to the list

            }else if(subbb.contains("g")){

            }else if(subbb.contains("j")){

            }else{

            }


                }



        lec_adapter adapter = new lec_adapter(getContext(), items, new SelectListener() {
            @UnstableApi
            @Override
            public void onIteamClicked(Item item, int position) {
                // Handle item clicks here
              //  Toast.makeText(getContext(),String.valueOf(position) , Toast.LENGTH_SHORT).show();

                Log.d("Fragment", "Clicked item4: " + item.getName());
                //  Toast.makeText(getContext(),  item.getName(), Toast.LENGTH_SHORT).show();
                // You can also pass more data or navigate to another fragment/activity here
                act activity = (act) getActivity();

                String rootjson = activity.getYourVariable();


                lecs lecs = new lecs();


                    Bundle bundle = new Bundle();


                String book = getArguments().getString("book");
                String sub = getArguments().getString("sub");

             //   Toast.makeText(getContext(), book +sub+ item.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), video.class);
                intent.putExtra("playlist", "yes");
                intent.putExtra("book", book);
                intent.putExtra("sub", sub);
                intent.putExtra("posi", String.valueOf(position));

                intent.putExtra("lec", item.getName());




                startActivity(intent);









            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(new sub_adapter(getContext(),items,subject.this));
//
        recyclerView.setAdapter(adapter);



        return view;
    }
}