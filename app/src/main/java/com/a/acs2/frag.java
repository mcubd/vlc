package com.a.acs2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag extends Fragment {

    private MyViewModel myViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag.
     */
    // TODO: Rename and change types and number of parameters
    public static frag newInstance(String param1, String param2) {
        frag fragment = new frag();
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
        View view = inflater.inflate(R.layout.fragment_frag, container, false);





        String[] keysArray = getArguments().getStringArray("keysArray");
        String book22 = getArguments().getString("book");


        RecyclerView recyclerView = view.findViewById(R.id.sub);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));





        List<Item> items = new ArrayList<>(); // Initialize the items list

// Using a for-each loop with keysArray
        for (String key : keysArray) {

            String value = ""; // Replace with actual logic to get value associated with key
           if(key.contains("01") && book22.contains("m1")){
               items.add(new Item(key, value,1, R.drawable.sorol,1,1,false,"id")); // Add item to the list

           }else  if(key.contains("06") && book22.contains("m2")){
 items.add(new Item(key, value,1, R.drawable.konikk,1,1,false,"id")); // Add item to the list

           }else  if(key.contains("08") && book22.contains("p1")){
 items.add(new Item(key, value,1, R.drawable.pmo,1,1,false,"id")); // Add item to the list

           }else  if(key.contains("d")){

           }else  if(key.contains("h")){

           }else{

           }

        }

        // Set the adapter for the RecyclerView
//            recyclerView.setAdapter(new sub_adapter(this, items, frag.this));


        chap_adapter adapter = new chap_adapter(getContext(), items, new SelectListener() {
            @Override
            public void onIteamClicked(Item item, int position) {

                act activity = (act) getActivity();

                String rootjson = activity.getYourVariable();


                lecs lecs = new lecs();


                try {

                String ke = getArguments().getString("pre");
                JSONObject jsonObject = new JSONObject(rootjson);
                    JSONObject leclist0 = jsonObject.getJSONObject(ke).getJSONObject(item.getName());
                    JSONObject leclist = jsonObject.getJSONObject(ke).getJSONObject(item.getName()).getJSONObject(leclist0.keys().next());
//                    Log.w("Fragment", String.valueOf(leclist0));
//
                    Iterator<String> keysIterator = leclist.keys();
                List<String> keysList = new ArrayList<>();

                // Add keys to the list
                while (keysIterator.hasNext()) {
                    keysList.add(keysIterator.next());
                }

                // Convert the List to an array if necessary
                String[] keysArray = keysList.toArray(new String[0]);
               // Log.d("MyFragment", "JSON Data-- " + String.valueOf(keysArray));





                Bundle bundle = new Bundle();
                bundle.putStringArray("keysArray", keysArray);

                    String book = getArguments().getString("book");
                    bundle.putString("book",book);
                    bundle.putString("sub", item.getName());



                    lecs.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, lecs)
                            .addToBackStack(null)
                            .commit();


                } catch (JSONException e) {
                e.printStackTrace();
            }





            }
        });


//        recyclerView.setAdapter(new sub_adapter(getContext(),items,subject.this));
//
        recyclerView.setAdapter(adapter);




        return view;
    }

}