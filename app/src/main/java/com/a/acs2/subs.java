package com.a.acs2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link subs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class subs extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public subs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment subs.
     */
    // TODO: Rename and change types and number of parameters
    public static subs newInstance(String param1, String param2) {
        subs fragment = new subs();
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

        View view = inflater.inflate(R.layout.fragment_subs, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recd);

        String[] keysArray = getArguments().getStringArray("keysArray");
        // Toast.makeText(getContext(),  Arrays.toString(keysArray), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getContext(), String.valueOf( getFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();


        List<Item> items = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();
        List<Item> items3 = new ArrayList<>();

        for (String key : keysArray) {
            // Assuming you have a corresponding value you want to set for each key
            // Here, I'm using a placeholder. You can modify this based on your data structure.
            String value = ""; // Replace with actual logic to get value associated with key

            if (key.contains("m1")) {
                items.add(new Item(key, value, 1, R.drawable.m1, 1, 1, false, "id")); // Add item to the list

            } else if (key.contains("m2")) {
                items.add(new Item(key, value, 1, R.drawable.m2, 1, 1, false, "id")); // Add item to the list

            } else if (key.contains("p1")) {
                items.add(new Item(key, value, 1, R.drawable.p1, 1, 1, false, "id")); // Add item to the list

            } else {

            }

        }

        sub_adapter adapter = new sub_adapter(getContext(), items, new SelectListener() {
            @Override
            public void onIteamClicked(Item item, int position) {

//
//  items2.add(new Item("key", "value", 1, R.drawable.konik, 1, 1, false, "id")); // Add item to the list
//
//
//                sub_adapter newAdapter  = new sub_adapter(getContext(), items2, new SelectListener() {
//                    @Override
//                    public void onIteamClicked(Item item, int position) {
//
//                    }
//
//                });
//
//        // Change the adapter
//        recyclerView.setAdapter(newAdapter);
//
//        // Optionally notify the adapter of data changes
//        newAdapter.notifyDataSetChanged();


                JSONObject m1Object = null;
                try {
//
                    act activity = (act) getActivity();
                    String rootjson = activity.getYourVariable();
                    JSONObject jsonObject = new JSONObject(rootjson);

                    m1Object = jsonObject.getJSONObject(item.getName());
                    Iterator<String> keysIterator = m1Object.keys();

                    List<String> keysList = new ArrayList<>();
                    while (keysIterator.hasNext()) {
                        keysList.add(keysIterator.next());
                    }
//
//
                    String[] keysArray = keysList.toArray(new String[0]);
//
//
//                    for (String key : keysArray) {
//
//                        String value = ""; // Replace with actual logic to get value associated with key
//                        if (key.contains("01") && item.getName().contains("m1")) {
//                            items2.add(new Item(key, value, 1, R.drawable.sorol, 1, 1, false, "id")); // Add item to the list
//
//                        } else if (key.contains("06") && item.getName().contains("m2")) {
//                            items2.add(new Item(key, value, 1, R.drawable.konikk, 1, 1, false, "id")); // Add item to the list
//
//                        } else if (key.contains("08") && item.getName().contains("p1")) {
//                            items2.add(new Item(key, value, 1, R.drawable.pmo, 1, 1, false, "id")); // Add item to the list
//
//                        } else if (key.contains("d")) {
//
//                        } else if (key.contains("h")) {
//
//                        } else {
//
//                        }
//
//                    }
//
//
//
//
//                    sub_adapter newAdapter = new sub_adapter(getContext(), items2, new SelectListener() {
//                        @Override
//                        public void onIteamClicked(Item item, int position) {
//
//                        }
//
//                    });
//
//                    // Change the adapter
//                    recyclerView.setAdapter(newAdapter);
//
//                    // Optionally notify the adapter of data changes
//                    newAdapter.notifyDataSetChanged();

                    frag frag = new frag();
                    Bundle bundle = new Bundle();
                       bundle.putStringArray("keysArray", keysArray);
                    bundle.putString("pre", item.getName());
                    bundle.putString("book", item.getName());
                    frag.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, frag)
                            .addToBackStack(null)
                            .commit();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        });


        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        recyclerView.setAdapter(new sub_adapter(getContext(),items,subject.this));
//
        ((act) getActivity()).setMyViewVisibility(View.GONE);

        recyclerView.setAdapter(adapter);


        return view;
    }
}