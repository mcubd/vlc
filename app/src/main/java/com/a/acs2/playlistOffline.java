package com.a.acs2;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadIndex;
import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadService;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;





/**
 * A simple {@link Fragment} subclass.
 * Use the {@link subs#newInstance} factory method to
 * create an instance of this fragment.
 */
@UnstableApi
public class playlistOffline extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Item> items;
    private DownloadManager downloadManager;
    RecyclerView recyclerView;
    DownloadIndex downloadIndex;
      Handler handler;
    Runnable updateRunnable;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public playlistOffline() {
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

    @UnstableApi
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_playlist_offline, container, false);

          recyclerView = view.findViewById(R.id.playlist);
          downloadManager = DemoUtil.getDownloadManager(getContext());

          downloadIndex = downloadManager.getDownloadIndex();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        String key010 = getArguments().getString("ids");
        // Toast.makeText(getContext(),  Arrays.toString(keysArray), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getContext(), String.valueOf( getFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
        List<String> stringList = new ArrayList<>(List.of(key010.split(", ")));



        items = new ArrayList<>();

        for (String key : stringList) {
            // Assuming you have a corresponding value you want to set for each key
            // Here, I'm using a placeholder. You can modify this based on your data structure.

            try {
                Download download =   downloadIndex.getDownload(key);
                if (download != null) {


                    if(download.state ==2) {
                        items.add(new Item(key ,String.valueOf((int) download.getPercentDownloaded()+"%"),download.getPercentDownloaded(),R.drawable.math,R.drawable.play,R.drawable.delete,false,"id"));
                    }else if (download.state ==1){
                        items.add(new Item(key ,String.valueOf((int) download.getPercentDownloaded()+"%"),download.getPercentDownloaded(),R.drawable.math,R.drawable.pause,R.drawable.delete,false,"id"));
                    }else {
                        items.add(new Item(key ,String.valueOf((int) download.getPercentDownloaded()+"%"),download.getPercentDownloaded(),R.drawable.math,R.drawable.pause,R.drawable.delete,false,"id"));

                    }




                } } catch (IOException e) {throw new RuntimeException(e);}
                        }

//        dmanager_adapter adapter = new dmanager_adapter(getContext(), items, new SelectListenerdownload() {
//            @Override
//            public void onIteamClicked(Item item, int position) {
//                String jsonString = "{\"m1\":{\"z\":{\"m2\":{\"src\":\"http://192.168.0.101:3004/m2.mp4\",\"title\":\"lec 3  bro sorol\"},\"Lec1\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2FThis.mp4?alt=media&token=5baedafe-5a03-4fd5-a40b-282be1ae12b5\",\"title\":\"lec 1 of sorol!!\"},\"Lec2\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fvideoplayback%20(1).mp4?alt=media&token=75bf7507-3575-406d-8f40-702cc974c4be\",\"title\":\"lec 2  bro sorol\"},\"Lec3\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fvideoplayback.mp4?alt=media&token=db7395ee-3e7f-4709-9ba9-305743b5db91\",\"title\":\"lec 3  bro sorol\"}},\"bri\":{\"Lec1\":\"lec 0.1 of bri\",\"Lec2\":\"haya bri 2 lec\"},\"sorol\":{\"সরলরেখা\":{\"Lec 1\":{\"id\":\"m19l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_m1.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 1\"},\"Lec 2\":{\"id\":\"m19l1.2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_m2.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 2\"}}}},\"m2\":{\"9\":{\"gotibidda\":{\"Lec1\":{\"id\":\"m29l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_main.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 1\"},\"Lec2\":{\"id\":\"m29l1.2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_w.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 2\"},\"Lec3\":{\"id\":\"m29l3\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_j.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 3\"},\"Lec4\":{\"id\":\"m29l2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_s.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 4\"}}},\"konik\":{\"কনিক\":{\"Lec 1\":{\"id\":\"m29l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_main.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 1\"}}}},\"p1\":{\"pmotion\":{\"পর্যায়বৃত্ত গতি\":{\"Lec 1\":{\"id\":\"p19l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_p.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 1\"}}}}}";
//                try {
//                    // Parse JSON string into JSONObject
//                    JSONObject jsonObject = new JSONObject(jsonString);
//                    JSONObject m1Object = jsonObject.getJSONObject(item.getName().substring(0, 2));
//
//                    Log.d(TAG, "m1obj---------------- " +m1Object );
//
//                    Iterator<String> keys = m1Object.keys();
//
//                        String key = keys.next();
//
//                        JSONObject sub = m1Object.getJSONObject(key);
//                    Log.d(TAG, "9lecch-l---------------- " +sub );
//
//
//                    Iterator<String> keys1 = sub.keys();
//
//                    String key1 = keys1.next();
//
//                    JSONObject sub1 = sub.getJSONObject(key1);
//                    Log.d(TAG, "9lec---------------- " +sub1 );
//
//                    Intent intent = new Intent(getContext(), Offline_vid2.class);
//                    intent.putExtra("playlist", "yes");
//                    intent.putExtra("ids", key010);
//                    intent.putExtra("lecs",String.valueOf(sub1)  );
//                    startActivity(intent);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//
//
//
//            }
//
//
//            @Override @UnstableApi
//            public void onDltClicked(Item item, int position) {
//                DownloadService.sendRemoveDownload(
//                        getContext(), OfflineVideoDownloadService.class, item.getName(), /* foreground= */ true);
//
//
//                ((dmanager_adapter) recyclerView.getAdapter()).removeItem(position); // Assuming item.getPercentDownloaded() returns an int
//
//
//            }
//
//            @Override @UnstableApi
//            public void onPauseClicked(Item item, int position) {
//
//
//                try {
//
//                    DownloadIndex downloadIndex0 = downloadManager.getDownloadIndex();
//                    Download download =   downloadIndex0.getDownload(item.getName());
//
//                    int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(item.getName());
//
//
//                    if (download != null) {
//
//                        if (download.state == 1) {
//                            if (positionm != -1) {
//                                // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
//                                ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int
//
//                            }
//                            DownloadService.sendSetStopReason(
//                                    getContext(), OfflineVideoDownloadService.class, item.getName(), 0, /* foreground= */ true);
//
//                        } else if (download.state == 2) {
//                            if (positionm != -1) {
//                                // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
//                                ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.pause); // Assuming item.getPercentDownloaded() returns an int
//
//                            }
//                            DownloadService.sendSetStopReason(
//                                    getContext(), OfflineVideoDownloadService.class, item.getName(), 1, /* foreground= */ true);
//
//                        }
//                    }
//
//
//                }
//                catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//
//                ////-------------------------------------
//
//
//            }
//
//
//        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(new sub_adapter(getContext(),items,subject.this));
//
       // recyclerView.setAdapter(adapter);

          handler = new Handler();
          updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateIndex();
                handler.postDelayed(this,   500); // Update every second
            }


        };
        handler.post(updateRunnable);











        return view;
    }


    @UnstableApi
    private void updateIndex() {
      //  Log.d(TAG, "500---------------------frzg---------- "  );
        List<String> newList = new ArrayList<>();

        for (Item item : items) {
            if ( item.getPercent()  < 100) {
                newList.add(item.getName());

            }
        }
        //Log.d(TAG, "new list "  +newList);


        ArrayList<String> idlist = new ArrayList<>();

        List<Download> downloads = downloadManager.getCurrentDownloads();
        for (Download item : downloads) {
            idlist.add(item.request.id);
        }
//        System.out.println("276...---"+newList);







        for (Download item : downloads) {



            if (newList.contains(item.request.id)) {
                //  System.out.println("updated " +item.getPercentDownloaded());
//                Item itemm = items.get(0);
//                itemm.setEmail (String.valueOf(item.getPercentDownloaded())); // Update the percentage
//                recyclerView.getAdapter().notifyItemChanged(0);
//                System.out.println("275...---"+item.getPercentDownloaded());
//                System.out.println("276...---"+(int)item.getPercentDownloaded());
//                System.out.println("276...---"+(int)item.getPercentDownloaded());


//                for (Item item0 : items) {
//                    if (item0.getName().equals(item.request.id)) {
//                        if ((int)item.getPercentDownloaded() != (int)item0.getPercent()) {
//
//                        }
//
//                    }
//                }


                for (int i = 0; i < items.size(); i++) {

//                    System.out.println("276...---"+items.get(i));


                    if (items.get(i).getName().equals(item.request.id)) {
                        if ((int)item.getPercentDownloaded() != (int)items.get(i).getPercent()) {


                            // int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(item.request.id);
//                if (positionm != -1) {
//                   // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                            ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(i,"Downloading... "+ String.valueOf((int) item.getPercentDownloaded())+"%",  item.getPercentDownloaded()); // Assuming item.getPercentDownloaded() returns an int
//
//                }


                        }}
//                    User user = items.get(i);
//                    // Access both user and index i
                }



//                int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(item.request.id);
//                if (positionm != -1) {
//                   // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
//                    ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(positionm,"Downloading... "+ String.valueOf((int) item.getPercentDownloaded())+"%",  item.getPercentDownloaded()); // Assuming item.getPercentDownloaded() returns an int
//
//                }
//                recyclerView.getAdapter().
                // recyclerView.getAdapter().updateDownloadPercentage(0, String.valueOf(item.getPercentDownloaded())); // Update the download percentage for that item
                //  ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(0, String.valueOf(item.getPercentDownloaded())); // Assuming item.getPercentDownloaded() returns an int

            }
            // System.out.println(item.getPercentDownloaded());
        }

        for (String fruit : newList) {
            if (!idlist.contains(fruit)) {
                Log.d(TAG, "stuck at 99% fixing it now " );
                downloadIndex = downloadManager.getDownloadIndex();



                try {
                    Download download =   downloadIndex.getDownload(fruit);
                    if (download != null) {
                        if (download.state == 3) {


                            int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(download.request.id);
                            if (positionm != -1) {
                                // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                                ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(positionm,"Downloading... 100%",100  ); // Assuming item.getPercentDownloaded() returns an int

                            }

                            items.get(positionm).setHidden(true);

                            //  ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(0, String.valueOf(100)); // Assuming item.getPercentDownloaded() returns an int


                        }}} catch (IOException e) {throw new RuntimeException(e);}

            }
        }



//---------------------end
//
//        for (Item item : items) {
//            System.out.println(item.getName()+item.getEmail());
//        }
//        Log.d(TAG, "recy items : "  +items);
//
//
//
//        List<Download> downloads = downloadManager.getCurrentDownloads();
//        Log.d(TAG, "getCurrentDownloads : "  +downloads);
//        for (Download item : downloads) {
//            System.out.println(item.getPercentDownloaded());
//        }



        //-------------------------
//        downloadManager = DemoUtil.getDownloadManager(this);
//
//        DownloadIndex downloadIndex = downloadManager.getDownloadIndex();
//        List<Item> items = new ArrayList<Item>();
////        items.add(new Item("Download","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",R.drawable.math));
////        items.add(new Item("dgdfdfffffdgfffg","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",R.drawable.math));
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new dmanager_adapter(getApplicationContext(),items,this));
//
//
//        DownloadCursor downloadsCursor = null;
//        try {
//            downloadsCursor =   downloadIndex.getDownloads();
//            if (downloadsCursor != null) {
//                while (downloadsCursor.moveToNext()) {
//
//                    Download download = downloadsCursor.getDownload(  );
//
//
//                  //  Log.d(TAG, "index : state "+download.state+" id "+download.request.id );
//
//                  //  TextView  textView = findViewById(R.id.textView2);
//                  //  textView.setText("index : state "+String.valueOf(download.state) +" id "+String.valueOf(download.request.id) );
//                    Toast.makeText(getApplicationContext(),String.valueOf(download.getPercentDownloaded()), Toast.LENGTH_SHORT).show();
//                  // items.add(new Item(download.,"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",R.drawable.math));
//
//
//                }
//                downloadsCursor.close(); // Don't forget to close the cursor
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//-----end-update-Index

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable); // Stop updates when the activity is destroyed
    }


}