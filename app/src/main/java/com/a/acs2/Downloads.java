package com.a.acs2;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadCursor;
import androidx.media3.exoplayer.offline.DownloadIndex;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter;

import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadManager.Listener;
import androidx.media3.exoplayer.offline.DownloadRequest;
import androidx.media3.exoplayer.offline.DownloadService;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadRequest;
import androidx.media3.exoplayer.offline.DownloadService;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;


@UnstableApi
public class Downloads extends AppCompatActivity implements SelectListenerdownload {

    private DownloadManager downloadManager;
    Context context = this;


    private Handler handler;
    private Runnable updateRunnable;
    RecyclerView recyclerView;
    List<Item> items;
    DownloadIndex downloadIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        recyclerView = findViewById(R.id.recyclerview);


        downloadManager = DemoUtil.getDownloadManager(this);

        DownloadIndex downloadIndex = downloadManager.getDownloadIndex();
        items = new ArrayList<Item>();

        DownloadCursor downloadsCursor = null;
        try {
            downloadsCursor = downloadIndex.getDownloads();
            if (downloadsCursor != null) {
                downloadIndex = downloadManager.getDownloadIndex();

                List<String> playlist = new ArrayList<>();
                Map<String, List<String>> groupedNames = new HashMap<>();
                for (int i = downloadsCursor.getCount() - 1; i >= 0; i--) {
                    downloadsCursor.moveToPosition(i);
                    Download download = downloadsCursor.getDownload();


                    if (download != null) {

                        if (download.state == 2) {
                            items.add(new Item(String.valueOf(download.request.id), String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), R.drawable.math, R.drawable.play, R.drawable.delete, false, download.request.id));

                        } else if (download.state == 1) {
                            items.add(new Item(String.valueOf(download.request.id), String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), R.drawable.math, R.drawable.pause, R.drawable.delete, false, download.request.id));

                        } else {
                            items.add(new Item(String.valueOf(download.request.id), String.valueOf((int) download.getPercentDownloaded() + "%"), download.getPercentDownloaded(), R.drawable.math, R.drawable.pause, R.drawable.delete, false, download.request.id));

                        }
                    }

//                    String prefix =   download.request.id.substring(0, 3) ;
//                    if (!groupedNames.containsKey(prefix)) {
//                        groupedNames.put(prefix, new ArrayList<>());
//                    }
//                    groupedNames.get(prefix).add(download.request.id);
//                    playlist.add(prefix);

                }

//
//
//                for (Map.Entry<String, List<String>> entry : groupedNames.entrySet()) {
//                    String groupName = entry.getKey();  // Access group name directly
//                    List<String> namesList = entry.getValue();  // Access list of names directly
//
//                    // Loop through the list of names
//                    for (int i = 0; i < namesList.size(); i++) {
//                        String name = namesList.get(i);  // Access each name by index
//                        // Perform operations on the name here
////                        System.out.println("Group:------------ " + groupName + ", Name: " + name);
//                    }
//
//                    if (namesList.size() >1){
//                        items.add(new Item(groupName ,String.join(", ", namesList),  404,R.drawable.math,R.drawable.play,R.drawable.delete,true,String.valueOf(namesList.size())));
//
//
//                    } else {
                //  System.out.println("Group:------------ " + groupName + ", Name: " + name);


//                        try {
//                            Download download =   downloadIndex.getDownload(namesList.get(0));
//                            if (download != null) {
//
//                        if(download.state ==2) {
//                            items.add(new Item(String.valueOf(download.request.id) , String.valueOf((int) download.getPercentDownloaded()+"%"),  download.getPercentDownloaded(),R.drawable.math,R.drawable.play,R.drawable.delete,false,"id"));
//
//                        }else if (download.state ==1){
//                            items.add(new Item(String.valueOf(download.request.id) , String.valueOf((int) download.getPercentDownloaded()+"%"),  download.getPercentDownloaded(),R.drawable.math,R.drawable.pause,R.drawable.delete,false,"id"));
//
//                        }else {
//                            items.add(new Item(String.valueOf(download.request.id) , String.valueOf((int) download.getPercentDownloaded()+"%"),  download.getPercentDownloaded(),R.drawable.math,R.drawable.pause,R.drawable.delete,false,"id"));
//
//                        }
//                            }
//                        } catch (IOException e) {throw new RuntimeException(e);}


//
//                    }
//                }


//                for (int i = downloadsCursor.getCount() - 1; i >= 0; i--) {
//                    downloadsCursor.moveToPosition(i);
//
//
//
//
//
//                    Download download = downloadsCursor.getDownload(  );
//
//
//
//
//
//                }

                recyclerView.setLayoutManager(new LinearLayoutManager(this));


               // recyclerView.setAdapter(new dmanager_adapter(getApplicationContext(), items, this));
                downloadsCursor.close(); // Don't forget to close the cursor

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //------------


        handler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateIndex();
                handler.postDelayed(this, 500); // Update every second
            }
        };
        handler.post(updateRunnable);


    }

    @Override
    @UnstableApi
    public void onIteamClicked(ItemDmanager item, int position) {

//
//        if (item.getPercent() >200){
//            handler.removeCallbacks(updateRunnable);
//
//
//
//            playlistOffline playlistOffline = new playlistOffline();
//        Bundle bundle = new Bundle();
//        bundle.putString("ids", item.getEmail());
//
//
//
//
//            playlistOffline.setArguments(bundle);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, playlistOffline)
//                .addToBackStack(null)
//                .commit();
//        }


        String jsonString = "{\"m1\":{\"z\":{\"m2\":{\"src\":\"http://192.168.0.101:3004/m2.mp4\",\"title\":\"lec 3  bro sorol\"},\"Lec1\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2FThis.mp4?alt=media&token=5baedafe-5a03-4fd5-a40b-282be1ae12b5\",\"title\":\"lec 1 of sorol!!\"},\"Lec2\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fvideoplayback%20(1).mp4?alt=media&token=75bf7507-3575-406d-8f40-702cc974c4be\",\"title\":\"lec 2  bro sorol\"},\"Lec3\":{\"src\":\"https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fvideoplayback.mp4?alt=media&token=db7395ee-3e7f-4709-9ba9-305743b5db91\",\"title\":\"lec 3  bro sorol\"}},\"01\":{\"সরলরেখা\":{\"Lec 1\":{\"id\":\"m101l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_m1.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 1\"},\"Lec 2\":{\"id\":\"m101l1.2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_m2.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 2\"}}},\"bri\":{\"Lec1\":\"lec 0.1 of bri\",\"Lec2\":\"haya bri 2 lec\"}},\"m2\":{\"9\":{\"gotibidda\":{\"Lec1\":{\"id\":\"m29l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_main.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 1\"},\"Lec2\":{\"id\":\"m29l1.2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_w.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 2\"},\"Lec3\":{\"id\":\"m29l3\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_j.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 3\"},\"Lec4\":{\"id\":\"m29l2\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_s.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 4\"}}},\"06\":{\"কনিক\":{\"Lec 1\":{\"id\":\"m206l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid_main.mp4?alt=media&token=3c30824f-420f-4a1c-be7b-b51d9d908015\",\"title\":\"Lec 1\"}}}},\"p1\":{\"08\":{\"পর্যায়বৃত্ত গতি\":{\"Lec 1\":{\"id\":\"p108l1\",\"src\":\"https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_p.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc\",\"title\":\"Lec 1\"}}}}}";
        try {
            // Parse JSON string into JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject m1Object = jsonObject.getJSONObject(item.getimgnum().substring(0, 2));
           String id2ndpart = item.getimgnum().substring(2, 4);


            Iterator<String> keys = m1Object.keys();
            Log.d(TAG, "keys-l---------------- " +String.valueOf(keys) );

            while (keys.hasNext()) {
                String keyy = keys.next();
                Log.d(TAG, "lllll-l---------------- " + keyy+" - "+id2ndpart);
                if(keyy.contains(id2ndpart)){
                    JSONObject sub00 = m1Object.getJSONObject(id2ndpart);
                    Log.d(TAG, "mainn-----main---l---------------- " + sub00);
                    Iterator<String> keys6 = sub00.keys();
                    String key98 = keys6.next();
                    JSONObject lecs78 = sub00.getJSONObject(key98);
                    Log.d(TAG, "mainn-----jjj---l---------------- " + lecs78);

                    String id = item.getimgnum();

                                Intent intent = new Intent(getApplicationContext(), Offline_vid2.class);
            intent.putExtra("playlist", "yes");
            intent.putExtra("ids", id);
            intent.putExtra("lecs",String.valueOf(lecs78)  );
            startActivity(intent);
                }
                
            }
//            String key = keys.next();
//            Log.d(TAG, "key-l---------------- " + key);
//
//            JSONObject sub = m1Object.getJSONObject(key);
//            Log.d(TAG, "sub-l---------------- " + sub);
//
//
//            Iterator<String> keys1 = sub.keys();
//            Log.d(TAG, "keys1-l---------------- " + keys1);

//            String key1 = keys1.next();
//            Log.d(TAG, "key1-l---------------- " + key1);
//
//            JSONObject sub1 = sub.getJSONObject(key1);
//            Log.d(TAG, "sub1---------------- " + sub1);

//            Intent intent = new Intent(getApplicationContext(), Offline_vid2.class);
//            intent.putExtra("playlist", "yes");
//            intent.putExtra("ids", key010);
//            intent.putExtra("lecs",String.valueOf(sub1)  );
//            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @UnstableApi
    public void onDltClicked(ItemDmanager item, int position) {
        //  Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();

        //  Log.d(TAG, "dlt "  +item.getName());
        if (item.getPercent() > 200) {

            List<String> stringList = new ArrayList<>(List.of(item.getEmail().split(", ")));

            for (int i = 0; i < stringList.size(); i++) {
                DownloadService.sendRemoveDownload(
                        context, OfflineVideoDownloadService.class, stringList.get(i), /* foreground= */ true);

            }


            ((dmanager_adapter) recyclerView.getAdapter()).removeItem(position); // Assuming item.getPercentDownloaded() returns an int

        } else {

            DownloadService.sendRemoveDownload(
                    context, OfflineVideoDownloadService.class, item.getName(), /* foreground= */ true);


            ((dmanager_adapter) recyclerView.getAdapter()).removeItem(position); // Assuming item.getPercentDownloaded() returns an int

        }


    }

    @Override
    @UnstableApi
    public void onPauseClicked(ItemDmanager item, int position) {
        // Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT).show();
        // String contentId="ttz";
        // Initialize the download manager and download tracker
        //  downloadManager = DemoUtil.getDownloadManager(this);

        Log.d(TAG, "pause " + item.getName());
        try {

            DownloadIndex downloadIndex0 = downloadManager.getDownloadIndex();
            Download download = downloadIndex0.getDownload(item.getName());

            int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(item.getName());


            if (download != null) {

                if (download.state == 1) {
                    if (positionm != -1) {
                        // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                        ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int

                    }
                    DownloadService.sendSetStopReason(
                            context, OfflineVideoDownloadService.class, item.getName(), 0, /* foreground= */ true);

                } else if (download.state == 2) {
                    if (positionm != -1) {
                        // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                        ((dmanager_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.pause); // Assuming item.getPercentDownloaded() returns an int

                    }
                    DownloadService.sendSetStopReason(
                            context, OfflineVideoDownloadService.class, item.getName(), 1, /* foreground= */ true);

                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @UnstableApi
    private void updateIndex() {
        List<String> newList = new ArrayList<>();

        for (Item item : items) {
            if (item.getPercent() < 100) {
                newList.add(item.getName());

            }
        }
        //Log.d(TAG, "500---------------------main---------- "  );
//

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
                        if ((int) item.getPercentDownloaded() != (int) items.get(i).getPercent()) {


                            // int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(item.request.id);
//                if (positionm != -1) {
//                   // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                            ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(i, "Downloading... " + String.valueOf((int) item.getPercentDownloaded()) + "%", item.getPercentDownloaded()); // Assuming item.getPercentDownloaded() returns an int
//
//                }


                        }
                    }
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
                Log.d(TAG, "stuck at 99% fixing it now ");
                downloadIndex = downloadManager.getDownloadIndex();


                try {
                    Download download = downloadIndex.getDownload(fruit);
                    if (download != null) {
                        if (download.state == 3) {


                            int positionm = ((dmanager_adapter) recyclerView.getAdapter()).getPositionByName(download.request.id);
                            if (positionm != -1) {
                                // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                                ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(positionm, "Downloading... 100%", 100); // Assuming item.getPercentDownloaded() returns an int

                            }

                            items.get(positionm).setHidden(true);

                            //  ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(0, String.valueOf(100)); // Assuming item.getPercentDownloaded() returns an int


                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable); // Stop updates when the activity is destroyed
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

//        if (currentFragment instanceof lecs) {
//            // Pop back to Fragment 1
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        } else {
//            // Default back button behavior
//            super.onBackPressed();
//        }
        // Toast.makeText(getApplicationContext(), String.valueOf( getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();

        Log.d(TAG, "yyyyyyyyyyyyyyyy" + getSupportFragmentManager().getBackStackEntryCount());

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            // Pop back to the first fragment ("FRAGMENT_1")
            // getSupportFragmentManager().popBackStack("FRAGMENT_1", 0);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            recyclerView.getAdapter().notifyDataSetChanged();
            handler.post(updateRunnable);
            Log.d(TAG, "111111111111111111111111 ");


        } else {
            // If there's only one fragment left, use the default back button behavior
            Log.d(TAG, "2222222222222222222222 ");
            super.onBackPressed();

        }


    }

}