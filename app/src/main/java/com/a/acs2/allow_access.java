//package com.a.acs2;
//
//
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class allow_access extends AppCompatActivity implements MyAdapter.OnItemClickListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//
//        List<Item> items = new ArrayList<Item>();
//        items.add(new Item("John wick","john.wick@email.com",R.drawable.math));
//        items.add(new Item("Robert j","robert.j@email.com",R.drawable.math));
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items));
//
//    }
//    @Override
//    public void onItemClick(Item item) {
//        // Handle item click here
//        Toast.makeText(this, "Clicked on: " + item.getName(), Toast.LENGTH_SHORT).show();
//    }
//
//}

package com.a.acs2;


import androidx.appcompat.app.AppCompatActivity;
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
import java.util.List;
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

import org.json.JSONException;
import org.json.JSONObject;


@UnstableApi
public class allow_access extends AppCompatActivity  implements SelectListener  {
    Context context = this;

    private static final String TAG = "MainActivity";
    private DownloadManager downloadManager;
    private TextView textView;
    private Handler handler;
    private Runnable updateRunnable;
    private DefaultBandwidthMeter bandwidthMeter;
    private  long uptime =System.currentTimeMillis();
    private  float upkb=0;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_allow_access);

          recyclerView = findViewById(R.id.recyclerview);
        textView = findViewById(R.id.textView);

          bandwidthMeter = new DefaultBandwidthMeter.Builder(context).build();



        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Download","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",1,R.drawable.math,1,1,false,"id"));

        items.add(new Item("Offline Tes","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",1,R.drawable.math,1,1,false,"id"));

        items.add(new Item("Download Manager","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",1,R.drawable.math,1,1,false,"id"));
        items.add(new Item("playlist download","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",1,R.drawable.math,1,1,false,"id"));

        items.add(new Item("index","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",1,R.drawable.math,1,1,false,"id"));
        items.add(new Item("Lec 1","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",1,R.drawable.math,1,1,false,"id"));
        items.add(new Item("z","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",1,R.drawable.math,1,1,false,"id"));

   //     items.add(new Item("bongo","https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/trash%2FBONGO%20BnG%20-%20S2%20E1.mp4?alt=media&token=f55c3eb4-cae9-4752-8765-0df964815659",1,R.drawable.math,1,1,false));
     //   items.add(new Item("Fast","https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/trash%2F2021%20(1).mp4?alt=media&token=5428188e-0e1c-42cb-9b31-fe2b103f12ef",1,R.drawable.math,1,1,false));
        items.add(new Item("ok","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",1,R.drawable.math,1,1,false,"id"));

  //      items.add(new Item("Lec 3","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F10000000_1497496571041437_1414437614340569988_n.mp4?alt=media&token=68a5999d-de18-4870-98c6-1bacf122bb3b",1,R.drawable.math,1,1,false));
       //      items.add(new Item("Lec 4","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2FACS.mp4?alt=media&token=61d0b4c6-8c6d-45b4-9144-c6fc782d0f61",1,R.drawable.math,1,1,false));
    //    items.add(new Item("Lec 4.2","https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/output.mp4?alt=media&token=0588a112-6bb8-4803-8642-8047d93f25f4",1,R.drawable.math,1,1,false));

     //   items.add(new Item("Hd","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0522(2).mp4?alt=media&token=adcbb336-5571-4d65-860a-844f98dad1b0",1,R.drawable.math,1,1,false));

        items.add(new Item("Download2","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",1,R.drawable.math,1,1,false,"id"));

     //   items.add(new Item("Lec 6","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2FACS.mp4?alt=media&token=61d0b4c6-8c6d-45b4-9144-c6fc782d0f61",1,R.drawable.math,1,1,false));
        //items.add(new Item("Download","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",R.drawable.math));
             items.add(new Item("xor","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F1390942-hd_2048_1080_24fps.mp4?alt=media&token=2591979e-3d12-4152-9fe2-14363f956fe6",1,R.drawable.math ,1,1,false,"id"));


        //kkkk


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items,this));





/*        String value;
        Bundle bundle = getIntent().getExtras();
        value = bundle.getString("sample_name");
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();*/




        handler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateDownloadProgress();
                handler.postDelayed(this, 500); // Update every second
            }
        };
        handler.post(updateRunnable);









    }


    private void updateDownloadProgress() {
        downloadManager = DemoUtil.getDownloadManager(this);
        List<Download> downloads = downloadManager.getCurrentDownloads();
        StringBuilder progressBuilder = new StringBuilder();

        for (Download download : downloads) {
            int progressPercent = (int) download.getPercentDownloaded(); // Assuming getPercentDownloaded() returns a value between 0.0 and 1.0

           //------------------
            // Get total size and downloaded bytes


            long zz = download.updateTimeMs; // Downloaded size in bytes
            long totalBytes = download.contentLength; // Downloaded size in bytes
            long downloadedBytes = download.getBytesDownloaded(); // Downloaded size in bytes

            // Convert bytes to megabytes (MB)
            float totalMB = totalBytes / (1024*  1024f); // Total size in MB
            float downloadedMB = downloadedBytes / (1024*  1024f); // Downloaded size in MB


            ////--------------------------------------
            ////--------------------------------------
            float newkb=downloadedBytes-upkb;
            if (newkb !=0){
                float newtime=System.currentTimeMillis()-uptime;
              //  Log.d(TAG, "delta-kb : "  +newkb);
          //      Log.d(TAG, "delta-time : "  +newtime  );
            //    Log.d(TAG, "delta-time2 : "  +newtime/ 1000 );
             //   Log.d(TAG, "speed : "  + ( newkb / (newtime / 1000))+ " kbps" );
             //   Log.d(TAG, "speed in mb : "  + (( newkb / (1024*  1024f)) / (newtime / 1000))+ " kbps" );
               // textView.setText( String.valueOf(newkb+" kb "+uptime+" vs "+System.currentTimeMillis())); // Update your TextView with progress
                textView.setText(  " speed "+String.valueOf( ( newkb / (newtime / 1000))  )); // Update your TextView with progress

                upkb=downloadedBytes;
                uptime=System.currentTimeMillis();
            }




            //---------------------------------
            //---------------------------------
            // Calculate download speed (in bytes per second)
            long currentTimeMs = System.currentTimeMillis();
            float elapsedTimeMs = currentTimeMs - download.updateTimeMs;
            float bytesPerSecond;

//            Log.d(TAG, "elapsedTimeMs : "  +download.getProgress());

            // Log.d(TAG, "download.updateTimeMs : "  +download.updateTimeMs);
           // Log.d(TAG, "elapsedTimeMs : "  +elapsedTimeMs);

           // Log.d(TAG, "downloadedBytes : "  +downloadedBytes);
            long downloadSpeed = bandwidthMeter.getBitrateEstimate();
           // long downloadSpeedf = bandwidthMeter.getTransferListener();
          //  Log.d(TAG, "downloadSpeed : "  +downloadSpeed);



            if (elapsedTimeMs !=0 && downloadedBytes !=0) {
                //Log.d(TAG, "elapsedTimeMs / 1000 : "  +elapsedTimeMs / 1000);
              //  bytesPerSecond  = (elapsedTimeMs > 0) ? (downloadedBytes / (elapsedTimeMs / 1000)) : 0; // Bytes per second
                 bytesPerSecond  = ( downloadedBytes / (elapsedTimeMs / 1000)); // Bytes per second

            }else  {
                bytesPerSecond  =0;
            }
           // Log.d(TAG, "bytesPerSecond : "  +bytesPerSecond);



            //  long bytesPerSecond = download.getBytesDownloaded() / (System.currentTimeMillis() - download.startTimeMs / 1000); // Download speed in bytes/second

            // Optionally convert speed to KB/s or MB/s for better readability
            float speedMBps = bytesPerSecond / (1024 * 1024f); // Speed in MB/s

            // Append download info to the StringBuilder
            progressBuilder.append("Download ID: ").append(download.request.id)
                    .append("  - Progress: ").append(progressPercent).append("%")
                    .append("\n - Downloaded: ").append(String.format("%.2f", downloadedMB)).append(" MB")
                    .append("\n - Total Size: ").append(String.format("%.2f", totalMB)).append(" mb")

                    .append("\n\n"); // Add line breaks between downloads
            //----------
         //   progressBuilder.append("Download ID: ").append(download.request.id)
                 //   .append(" - Progress: ").append(progressPercent).append("%\n");
        //    Log.d(TAG, "Download ID: " + download.request.id + " - Progress: " + progressPercent + "%");
        }

//        textView.setText(  " speed "+String.valueOf( ( newkb / (newtime / 1000))  ));
        textView.setText(progressBuilder.toString()); // Update your TextView with progress
    }



    @Override @UnstableApi
    public void onIteamClicked(Item item, int position) {

        if(item.getName()=="Lec 6"){

            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("src", value);
            intent.putExtra("title", "Additional Text");
            startActivity(intent);


/*            MediaItem s = DemoUtil.getLastDownloadedMediaItem();
            String uriString = s.toString();
            Log.d("TAG", "Urii: " + uriString);
        Toast.makeText(this, "Clicked on: " +uriString, Toast.LENGTH_SHORT).show();*/

        }


       else if (item.getName()=="Download"){



            String videoUrl0 = "https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fm0.mp4?alt=media";
           String videoUrl1="https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Foutput.mp4?alt=media&token=caeb84ea-974a-4415-89e3-2819af819ca6";
            //String videoUrl="https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid.mp4?alt=media&token=af591276-adcc-4b37-a258-f08581a56b15" ;
            String videoUrl="https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_p.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc" ;
            Uri uri = Uri.parse(videoUrl);

            String contentId="ttz";
            // Initialize the download manager and download tracker
            downloadManager = DemoUtil.getDownloadManager(this);

            DownloadRequest downloadRequest = new DownloadRequest.Builder(contentId, uri).build();
            DownloadService.sendAddDownload(
                    context, OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);




        }
        else if (item.getName()=="Download2"){


            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), zz.class);

            startActivity(intent);
//            String videoUrl0 = "https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fm0.mp4?alt=media";
//            String videoUrl="https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2F0617%20(2).mp4?alt=media&token=22aeafa8-483c-4d44-96ab-36a4aff7d7a1";
//            Uri uri = Uri.parse(videoUrl);
//
//            String contentId="vid2";
//            // Initialize the download manager and download tracker
//            downloadManager = DemoUtil.getDownloadManager(this);
//
//            DownloadRequest downloadRequest = new DownloadRequest.Builder(contentId, uri).build();
//            DownloadService.sendAddDownload(
//                    context, OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);




        }
        else if (item.getName()=="xor"){


            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), z.class);

            startActivity(intent);
//            String videoUrl0 = "https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fm0.mp4?alt=media";
//            String videoUrl="https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2F0617%20(2).mp4?alt=media&token=22aeafa8-483c-4d44-96ab-36a4aff7d7a1";
//            Uri uri = Uri.parse(videoUrl);
//
//            String contentId="vid2";
//            // Initialize the download manager and download tracker
//            downloadManager = DemoUtil.getDownloadManager(this);
//
//            DownloadRequest downloadRequest = new DownloadRequest.Builder(contentId, uri).build();
//            DownloadService.sendAddDownload(
//                    context, OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);




        }
        else if (item.getName()=="playlist download"){



            String videoUrl0="https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Foutput.mp4?alt=media&token=caeb84ea-974a-4415-89e3-2819af819ca6";
            String videoUrl1="https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2FBaleen%20Whale%20Vocalizations_%20What%20Do%20Whales%20Sound%20Like_.mp4?alt=media&token=4157fb1d-d2ee-4594-bfec-9ca4cb77622c";
            String videoUrl2="https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2FLargest%20Blue%20Whale%20on%20Earth%20the%20best%20Sound%20in%20the%20Entire%20World%F0%9F%92%99__%20Ijju_mariym.mp4?alt=media&token=3e58c9e3-e280-4d1a-be15-ba14bcb6d9e5";
            String videoUrl3="https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2FProbl%C3%A8me%20double%20pour%20Jerry%20%F0%9F%98%BC%20_%20D%C3%A9couvre%20en%20plus%20sur%20Boomerang%20%23tometjerry%20%23shorts%20%23cartoons.mp4?alt=media&token=8bda24d9-668a-44d4-b2f3-71c404b9670b";



            String contentId0="m29l1";
            String contentId1="m29l1.2";
            String contentId2="m29l2";
            String contentId3="m29l3";
            // Initialize the download manager and download tracker
            downloadManager = DemoUtil.getDownloadManager(this);

            DownloadRequest downloadRequest0 = new DownloadRequest.Builder(contentId0,  Uri.parse(videoUrl0)).build();
            DownloadService.sendAddDownload(
                    context, OfflineVideoDownloadService.class, downloadRequest0, /* foreground= */ true);
            DownloadRequest downloadRequest1 = new DownloadRequest.Builder(contentId1,  Uri.parse(videoUrl1)).build();
            DownloadService.sendAddDownload(
                    context, OfflineVideoDownloadService.class, downloadRequest1, /* foreground= */ true);
            DownloadRequest downloadRequest2 = new DownloadRequest.Builder(contentId2,  Uri.parse(videoUrl2)).build();
            DownloadService.sendAddDownload(
                    context, OfflineVideoDownloadService.class, downloadRequest2, /* foreground= */ true);
            DownloadRequest downloadRequest3 = new DownloadRequest.Builder(contentId3,  Uri.parse(videoUrl3)).build();
            DownloadService.sendAddDownload(
                    context, OfflineVideoDownloadService.class, downloadRequest3, /* foreground= */ true);



        }


        else if (item.getName()=="index"){

            String str ="1a";


            if (str.matches("\\d+")){
                Toast.makeText(getApplicationContext(),  "true", Toast.LENGTH_SHORT).show();

            }

        }
        else if (item.getName()=="dwnpr"){
            textView.setText("Text Changed!");

            String videoUrl0 = "https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fm0.mp4?alt=media";
            String videoUrl="https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Foutput.mp4?alt=media&token=caeb84ea-974a-4415-89e3-2819af819ca6";
            Uri uri = Uri.parse(videoUrl);
            String contentId="ttz";

            downloadManager = DemoUtil.getDownloadManager(this);
            downloadManager.addListener(
                    new DownloadManager.Listener() {


//                        @UnstableApi

//                        public void onIdle (DownloadManager downloadManager) {
//                            Log.d("down-no","down-no00");
//                        }

                        @UnstableApi

                        public void onDownloadChanged(DownloadManager downloadManager, Download download, Exception finalException) {
                            // This method is called when any download state changes (e.g., started, paused, resumed)
                            // You can retrieve the download and its progress

                            Log.d("down-statee00","down-statee00");

                            if (finalException != null) {
                                Log.e("Download Error", "Error occurred during download: " + finalException.getMessage());
                                return;
                            }

                            // Check the download state and retrieve progress
                            if (download.state == Download.STATE_DOWNLOADING) {
                                float progress = download.getPercentDownloaded();
                                if (progress != C.PERCENTAGE_UNSET) {
                                    Log.d("down-state1", String.valueOf(progress));
                                    // Assuming textView is already defined and accessible here
                                    textView.setText(String.format("Downloaded: %.2f%%", progress));
                                }
                            } else {
                                Log.d("down-state2", "Download state changed: " + download.state);
                            }
//                            for (Download download : downloadManager.getCurrentDownloads()) {
//
//                                if (download.state == Download.STATE_DOWNLOADING) {
//                                    float progress = download.getPercentDownloaded();
//                                    if (progress != C.PERCENTAGE_UNSET) {
//                                        Log.d("down-state", String.valueOf(progress));
//                                        textView.setText(String.format("Downloaded: %.2f%%", progress));
//                                    }
//                                }
//                            }
                        }





                    });

            DownloadRequest downloadRequest = new DownloadRequest.Builder(contentId, uri).build();
            DownloadService.sendAddDownload(
                    context, OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);



//            DownloadService.sendSetStopReason(
//                    context,
//                    OfflineVideoDownloadService.class,  // Your DownloadService class
//                    "ttz",
//                    1,
//                    /* foreground = */ true  // Can be true if you want to perform it in foreground
//            );

        }
        else if (item.getName()=="Offline Tes"){

            String videoUrl="https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Foutput.mp4?alt=media&token=caeb84ea-974a-4415-89e3-2819af819ca6" ;

//            Intent intent = new Intent(getApplicationContext(), offline_vid.class);
//            Intent intent = new Intent(getApplicationContext(), Offline_vid2.class);
//            intent.putExtra("src", videoUrl);
//            intent.putExtra("title", "valueg");
//            startActivity(intent);

        }
        else if (item.getName()=="Download Manager"){

            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), Downloads.class);
            intent.putExtra("sample_name", value);
            startActivity(intent);






        }
        else if (item.getName()=="ok"){

            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), act.class);
//            Intent intent = new Intent(getApplicationContext(), subject.class);
            intent.putExtra("sample_name", value);
            startActivity(intent);






        }
        else if (item.getName()=="Lec 1"){
            ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

            Glide.with(allow_access.this)
                    .load("https://bitdash-a.akamaihd.net/content/MI201109210084_1/thumbnails/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.jpg")
//                    .apply(new RequestOptions().transform(new GlideThumbnailTransformation(100)))
//                    .transform(new GlideThumbnailTransformation(1600))
                    .into(imageView);

        }
        else if (item.getName()=="xogr"){


            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), xor.class);

            startActivity(intent);

        }
        else if (item.getName()=="z"){

            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), z.class);

            startActivity(intent);

        }
        else {


            String value = item.getEmail();
            Intent intent = new Intent(getApplicationContext(), video.class);
            intent.putExtra("playlist", "no");
            intent.putExtra("src", "valueUrl");
            intent.putExtra("title", "Additional Text");
            //String jsonString='"Lec1": { "title": "Lec 1", "src": "http://192.168.0.101:3003/m2/6/1.mp4" }, "Lec2": { "title": "Lec 2", "src": "http://192.168.0.101:3003/m2/6/2.mp4" }'
            //intent.putExtra("json", jsonString);
            startActivity(intent);

        }





    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable); // Stop updates when the activity is destroyed
    }
}