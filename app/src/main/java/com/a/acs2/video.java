package com.a.acs2;


import static androidx.fragment.app.FragmentManager.TAG;
import static com.a.acs2.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.media3.common.C;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.VideoSize;
import androidx.media3.database.ExoDatabaseProvider;
import androidx.media3.datasource.AesCipherDataSource;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultDataSourceFactory;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadIndex;
import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadRequest;
import androidx.media3.exoplayer.offline.DownloadService;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.DefaultAllocator;
import androidx.media3.ui.PlayerControlView;
import androidx.media3.ui.PlayerView;
import androidx.media3.common.util.UnstableApi;

import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.media3.common.MediaItem;
import androidx.media3.ui.TimeBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a.acs2.databinding.ActivityMainBinding;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.otaliastudios.zoom.ZoomSurfaceView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.HandlerThread;

@UnstableApi
public class video extends AppCompatActivity implements SelectListenermini {

    private ExoPlayer mediaPlayer;
    private ZoomSurfaceView surfaceView;
    private PlayerControlView playerControlView;

    private SimpleCache cache;
    private Handler handler;

    private Runnable updateRunnable;

    private SimpleCache simpleCache;
    private File cacheDir;
    private boolean isLandscape = false;
    private GestureDetector gestureDetector;
    private GestureDetector gestureDetector2;
    private int newHeight = 0;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog speedBottomSheet;
    BottomSheetDialog qualityBottomSheet;
    private ActivityMainBinding binding;
    private TimeBar timeBar;

    private JSONObject tosub;
    DownloadIndex downloadIndex;
    DownloadManager downloadManager;
    List<playlist_mini> items;
    RecyclerView recyclerView;
    String posii0;
    private String selectState = "normal";
    private List<playlist_mini> selectedItems = new ArrayList<playlist_mini>();
    private HandlerThread handlerThread;
    TextView btn;
    String preTitle;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().setStatusBarColor(getResources().getColor(color.title_bg));


//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(layout.activity_video);

        downloadManager = DemoUtil.getDownloadManager(getApplicationContext());
        downloadIndex = downloadManager.getDownloadIndex();
        btn = (TextView) findViewById(id.btnpercent);


        String jsonn = act.jsonn0;
        Log.d("MyFragment89", jsonn);

        recyclerView = findViewById(R.id.recyclerview);
        String lecs;
        Bundle bundlelecs = getIntent().getExtras();
        lecs = bundlelecs.getString("json");
        String playlist = bundlelecs.getString("playlist");


//        String value111 = bundle33.getString("src");
        String value111 = "src";
//        String value111;
        String lecc;
        String sub;
        String book;
        String src = "";
        String title01 = "";
        posii0 = "";
        System.out.println("cheakkkl: " + playlist);


        if (playlist.equals("yes")) {
            book = bundlelecs.getString("book");
            sub = bundlelecs.getString("sub");
            lecc = bundlelecs.getString("lec");
            posii0 = bundlelecs.getString("posi");


            try {

                JSONObject jsonObject = new JSONObject(jsonn);
                JSONObject subObject = jsonObject.getJSONObject(book);
                JSONObject chapObject0 = subObject.getJSONObject(sub);
                JSONObject chapObject = chapObject0.getJSONObject(chapObject0.keys().next());


                preTitle = chapObject0.keys().next();

                JSONObject lec = chapObject.getJSONObject(lecc);
                String srcc = lec.getString("src");
                src = srcc;
                title01 = lec.getString("title");
                tosub = chapObject;
//                String ids = bundle.getString("ids");

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                items = new ArrayList<playlist_mini>();

                Iterator<String> keys = chapObject.keys();
                int counter = 0;
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = chapObject.getString(key);
                    JSONObject jsonObject00 = new JSONObject(value);
                    String id = jsonObject00.getString("id");
                    String src0 = jsonObject00.getString("src");
                    String title4 = jsonObject00.getString("title");
                    String lecTitle = title4;

                    String num = String.valueOf(counter);

                    Download download;
                    download = downloadIndex.getDownload(id);

                    String active = "";
                    int clr = color.download;
                    //  showToast(String.valueOf(posii0)+" "+String.valueOf(num));
                    if (posii0.matches(String.valueOf(num))) {
                        active = "active";
                    } else {
                        active = "";
                    }

                    if (download != null) {


                        if (download.getPercentDownloaded() == 100) {
                            items.add(new playlist_mini(lecTitle, num, R.drawable.math, ContextCompat.getColor(this, clr), "100%", 100, "0", src0, "title_bg", active, key, id, lecTitle, drawable.play));

                        } else {
                            if (download.state == 2) {
                                items.add(new playlist_mini(lecTitle, num, R.drawable.math, ContextCompat.getColor(this, clr), String.valueOf((int) download.getPercentDownloaded() + "%"), (int) download.getPercentDownloaded(), "state2", src0, "title_bg", active, key, id, lecTitle, drawable.play));

                            } else if (download.getPercentDownloaded() > 0) {
                                items.add(new playlist_mini(lecTitle, num, R.drawable.math, ContextCompat.getColor(this, clr), String.valueOf((int) download.getPercentDownloaded() + "%"), (int) download.getPercentDownloaded(), "state1", src0, "title_bg", active, key, id, lecTitle, drawable.pause));

                            } else {

                            }

                        }

//
//                        if (key.equals(lecc)) {
//                            //  items.add(new playlist_mini(title4, num, R.drawable.math, ContextCompat.getColor(this, color.download), (int) download.getPercentDownloaded() + "%", (int) download.getPercentDownloaded(), id, src0, "download", "active",key,"","", drawable.pause));
//                        } else {
//                            items.add(new playlist_mini(title4, num, R.drawable.math, ContextCompat.getColor(this, color.title_bg), (int) download.getPercentDownloaded() + "%", (int) download.getPercentDownloaded(), id, src0, "title_bg", "", key, id, "", drawable.pause));
//                        }
                    } else {
                        items.add(new playlist_mini(lecTitle, num, R.drawable.math, ContextCompat.getColor(this, clr), "", -3, "state1", src0, "title_bg", active, key, id, lecTitle, drawable.pause));

                        if (key.equals(lecc)) {
                            //   items.add(new playlist_mini(title4, num, R.drawable.math, ContextCompat.getColor(this, color.download), "down", -3, id, src0, "download", "active",key,"","", drawable.pause));
                        } else {
                            //  items.add(new playlist_mini(title4, num, R.drawable.math, ContextCompat.getColor(this, color.title_bg), "down", -3, id, src0, "title_bg", "", key, id, "", drawable.pause));
                        }

                    }

                    counter++;
                }


                recyclerView.setAdapter(new playlist_mini_adapter0(getApplicationContext(), items, this, ""));
                recyclerView.scrollToPosition(Integer.parseInt(posii0));


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else {
            src = bundlelecs.getString("src");
            showToast(src);
        }


//        recyclerView.scrollToPosition(6);


        // showToast(lecs);


//        try {
//            // Convert the JSON string to a JSONObject
//            JSONObject jsonObject = new JSONObject(lecs);
//            List<playlist_mini> items = new ArrayList<playlist_mini>();
//
//            // Iterate over the keys of the JSONObject
//            Iterator<String> keys = jsonObject.keys();
//            while (keys.hasNext()) {
//                String key = keys.next();
//                String value = jsonObject.getString(key);
//                JSONObject jsonObject00 = new JSONObject(value);
//                String src = jsonObject00.getString("src");
//               items.add(new playlist_mini("hh"," src", R.drawable.math));
//               items.add(new playlist_mini(key, src, R.drawable.math));
//            }
//
//            // Set the adapter for the RecyclerView
//            recyclerView.setAdapter(new playlist_mini_adapter(getApplicationContext(),items,this));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "JSON parsing error", Toast.LENGTH_SHORT).show();
//        }


//
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//        List<playlist_mini> items = new ArrayList<playlist_mini>();
//        items.add(new playlist_mini("Lec 1","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 2","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 3","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 4","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 5","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 6","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 7","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 8","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 9","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 10","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 11","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//        items.add(new playlist_mini("Lec 12","https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2F0331.mp4?alt=media&token=1a5690a6-d1d7-4b95-954b-1b900c615b4e",R.drawable.math));
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new playlist_mini_adapter(getApplicationContext(),items,this));


        // setContentView(binding.getRoot());

        FrameLayout frameLayout = findViewById(R.id.videeo_view);


//////////////////---------------------0
//
//        File cacheDir0 = new File(getFilesDir(), "media");
//        File cacheDir = new File(cacheDir0,src);
//
//        if (!cacheDir.exists()) {
//            cacheDir.mkdirs();
//        }
//        simpleCache = new SimpleCache(cacheDir, new LeastRecentlyUsedCacheEvictor(300 * 1024 * 1024)); // 300 MB max
//
//        // Create HTTP data source factory
//        DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
//                .setAllowCrossProtocolRedirects(true);
//
//        // Create default data source factory
//        DefaultDataSource.Factory defaultDataSourceFactory = new DefaultDataSource.Factory(this, httpDataSourceFactory);
//
//        // Create cache data source factory
//        CacheDataSource.Factory cacheDataSourceFactory = new CacheDataSource.Factory()
//                .setCache(simpleCache)
//                .setUpstreamDataSourceFactory(defaultDataSourceFactory)
//                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
//
//
//
//
//
//
//        /*        progressBar = findViewById(R.id.exo_buffering);*/
//        loading = findViewById(R.id.loading);
//
//        View touch_r = findViewById(R.id.touch_r);
//
//
//
//
//
//        LoadControl loadControl = new DefaultLoadControl.Builder()
//                .setPrioritizeTimeOverSizeThresholds(true)
//                .setBufferDurationsMs(
//                        1000 , 20000,  1000, 1000
//                )
////                .setAllocator(new DefaultAllocator(true, 16))  // Set the DefaultAllocator
////                .setTargetBufferBytes(-1)
//                .build();
//
//
//        mediaPlayer = new ExoPlayer.Builder(this)
//                .setLoadControl(loadControl).setMediaSourceFactory(new DefaultMediaSourceFactory(cacheDataSourceFactory))
//                .build();
/////////////////////////////////////////////---------------------0

        surfaceView = findViewById(R.id.surface_view);
        playerControlView = findViewById(R.id.player_control_view);
        //-------------------------------------------------------------


        View fullscreen = playerControlView.findViewById(R.id.exo_fullscreen_icon);
        FrameLayout timebar = playerControlView.findViewById(androidx.media3.ui.R.id.exo_bottom_bar);


        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLandscape) {

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setStatusBarColor(getResources().getColor(color.title_bg));


                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fullscreen.getLayoutParams();
                    int dpValue = 20;
                    float density = getResources().getDisplayMetrics().density;
                    int pixelValue = (int) (dpValue * density);
                    layoutParams.bottomMargin = pixelValue;
                    fullscreen.setLayoutParams(layoutParams);


                    ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) timebar.getLayoutParams();
                    int dpValue1 = 20;
                    float density1 = getResources().getDisplayMetrics().density;
                    int pixelValue1 = (int) (dpValue1 * density1);
                    layoutParams1.bottomMargin = pixelValue1;
                    timebar.setLayoutParams(layoutParams1);


                    LinearLayout downloadbtn = findViewById(id.downloadbtn);
                    downloadbtn.setVisibility(View.VISIBLE);
  /*                   View download = playerControlView.findViewById(R.id.download);
                    download.setVisibility(View.INVISIBLE);
                    View settings = playerControlView.findViewById(R.id.setting);
                    settings.setVisibility(View.INVISIBLE);*/
                    View myTextView = playerControlView.findViewById(R.id.myTextView);
                    myTextView.setVisibility(View.INVISIBLE);

                    View ghost = playerControlView.findViewById(R.id.ghost);
                    ghost.setVisibility(View.GONE);


                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    int heightInDp = 200; // Set your desired height in dp here
                    int heightInPixels = (int) (heightInDp * getResources().getDisplayMetrics().density);
                    frameLayout.getLayoutParams().height = heightInPixels;
                    frameLayout.requestLayout();

                    isLandscape = false;
                } else {

                    if (selectState.contains("select")) {
                        unSelect();
                    }

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);


                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fullscreen.getLayoutParams();
                    int dpValue = 42;
                    float density = getResources().getDisplayMetrics().density;
                    int pixelValue = (int) (dpValue * density);
                    layoutParams.bottomMargin = pixelValue;
                    fullscreen.setLayoutParams(layoutParams);


                    ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) timebar.getLayoutParams();
                    int dpValue1 = 42;
                    float density1 = getResources().getDisplayMetrics().density;
                    int pixelValue1 = (int) (dpValue1 * density1);
                    layoutParams1.bottomMargin = pixelValue1;
                    timebar.setLayoutParams(layoutParams1);


                    LinearLayout downloadbtn = findViewById(id.downloadbtn);
                    downloadbtn.setVisibility(View.INVISIBLE);
/*                    View download = playerControlView.findViewById(R.id.download);
                   download.setVisibility(View.INVISIBLE);
                    View settings = playerControlView.findViewById(R.id.setting);
                    settings.setVisibility(View.INVISIBLE);*/
                    View myTextView = playerControlView.findViewById(R.id.myTextView);
                    myTextView.setVisibility(View.VISIBLE);

                    View ghost = playerControlView.findViewById(R.id.ghost);
                    ghost.setVisibility(View.VISIBLE);

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    ViewGroup.LayoutParams params = frameLayout.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    frameLayout.setLayoutParams(params);

                    isLandscape = true;
                }
            }
        });


        View setting = playerControlView.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*                                              PlaybackParameters playbackParameters = new PlaybackParameters(4f);
                  mediaPlayer.setPlaybackParameters(playbackParameters);*/
                bottomSheetDialog = new BottomSheetDialog(video.this, R.style.BottomSheetTheme);
                View bsView = LayoutInflater.from(video.this).inflate(R.layout.bottom_sheet_layout,
                        v.findViewById(R.id.bottom_sheet));
                bsView.findViewById(id.speed).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // setting.itemView.performClick();
                        bottomSheetDialog.dismiss();
                        speedBottomSheet = new BottomSheetDialog(video.this, R.style.BottomSheetTheme);
                        View bsViewspeed = LayoutInflater.from(video.this).inflate(layout.speed,
                                v.findViewById(R.id.bottom_sheet));
                        speedBottomSheet.setContentView(bsViewspeed);
                        speedBottomSheet.show();
                        // bottomSheetDialog.dismiss();
                        // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                });

                bottomSheetDialog.setContentView(bsView);

                bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        BottomSheetDialog dialog = (BottomSheetDialog) dialogInterface;
                        FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        BottomSheetBehavior.from(bottomSheet)
                                .setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });


                bottomSheetDialog.show();


            }
        });


//        ImageButton customButton = new ImageButton(this);
//        customButton.setImageResource(drawable.fullscreen_exit); // Set your button icon herr
//        customButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle button click
//                showToast("Custom button clicked!");
//                int rotation = getWindowManager().getDefaultDisplay().getRotation();
//                showToast(String.valueOf(rotation));
////                if (isLandscape){
////                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
////                    boolean    isLandscape=false;
////                } else  {
////                    boolean  isLandscape=true;
////                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
////
////                }
//
//            }
//        });

        // Add the custom button to the player control view
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.END | Gravity.BOTTOM; // Adjust the gravity as per your requirement
        /*        playerControlView.addView(customButton, params);*/


        //-------------------------------------------

        mediaPlayer = new ExoPlayer.Builder(this).build();


        String aesKeyString = "1234567890abcdef";
        byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);

        mediaPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC);
        DataSource.Factory cacheDataSourceFactory = DemoUtil.getCacheNHttpAesDataSourceFactory(getApplicationContext(),aesKey);

        //DataSource.Factory aesDataSourceFactory = () -> new AesCipherDataSource(aesKey, cacheDataSourceFactory.createDataSource());

        ProgressiveMediaSource mediaSource0 = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(MediaItem.fromUri(src));


        mediaPlayer.setMediaSource(mediaSource0);


        playerControlView.setPlayer(mediaPlayer);


        surfaceView.addCallback(new ZoomSurfaceView.Callback() {
            @Override
            public void onZoomSurfaceCreated(@NotNull ZoomSurfaceView view) {
                Surface surface = view.getSurface();
                mediaPlayer.setVideoSurface(surface);


            }

            @Override
            public void onZoomSurfaceDestroyed(@NotNull ZoomSurfaceView view) {
            }
        });


        //  Uri rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.m2);
        // MediaItem mediaItem = MediaItem.fromUri(rawUri);
        // MediaItem mediaItem = MediaItem.fromUri(value);


        //     MediaItem mediaItem = MediaItem.fromUri("https://flame.flameriser78.workers.dev/0:/mcubd/0331.mp4");
/*        DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true);
        DefaultDataSource.Factory defaultDataSourceFactory = new DefaultDataSource.Factory(this, httpDataSourceFactory);
        CacheDataSource.Factory cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(simpleCache)
                .setUpstreamDataSourceFactory(defaultDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(mediaItem);*/
//        MediaItem mediaItem = new MediaItem.Builder().setUri(src).build();
//
//
//
//        // Set the media item to be played
//
//
//
//
//        mediaPlayer.setMediaItem(mediaItem);

        ProgressBar load = findViewById(R.id.exo_buffering_progress_bar0);
        ImageButton play_pause = playerControlView.findViewById(androidx.media3.ui.R.id.exo_play_pause);
        mediaPlayer.addListener(new ExoPlayer.Listener() {
            @Override
            public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
                surfaceView.setContentSize(videoSize.width, videoSize.height);
                newHeight = videoSize.height;
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {

                if (playbackState == Player.STATE_BUFFERING) {
                    play_pause.setVisibility(View.INVISIBLE);
//                    loading.setVisibility(View.GONE);
                    load.setVisibility(View.VISIBLE);
                } else {
                    play_pause.setVisibility(View.VISIBLE);
                    load.setVisibility(View.GONE);
//                    loading.setVisibility(View.GONE);
                }
            }

        });

        // Start playback
        mediaPlayer.prepare();
        mediaPlayer.play();


        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTap(MotionEvent event) {
                int viewWidth = surfaceView.getWidth();
                float touchX = event.getX();
                playerControlView.show();

                if (touchX < viewWidth / 2) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                } else {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                if (playerControlView.isShown()) {
                    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                    fadeOut.setDuration(200); // Set your desired duration
                    playerControlView.startAnimation(fadeOut);
                    playerControlView.hide();
                } else {
                    AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                    fadeIn.setDuration(200); // Set your desired duration
                    playerControlView.startAnimation(fadeIn);
                    playerControlView.show();
                }
                return true;
            }


        });


        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {

                    return true;
                }
//                if (event.getAction() == MotionEvent.ACTION_UP && surfaceView.getZoom() < 1) {
//                    surfaceView.setMinZoom(1f, 1);
//                    surfaceView.moveTo(1f, .0001f, 200f, true);
//                    return true;
//                }
                return false;


            }


        });


        TextView title = playerControlView.findViewById(R.id.myTextView);
        TextView title10 = findViewById(R.id.title);
//        String titlevid;
//        titlevid = "title";
        title.setText(preTitle + " | " + title01);
        title10.setText(preTitle + " | " + title01);

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(500);
        playerControlView.startAnimation(fadeIn);


        View touch_r = findViewById(R.id.touch_r);
        touch_r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Touching the view, show a toast
                        showToast("Touched!");
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Released the touch, show another toast
                        showToast("Touch Released!");
                        return true;
                }
                return false;
            }
        });

        View touchView = findViewById(R.id.touch_r11);
        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "View clicked!", Toast.LENGTH_SHORT).show();
                //int controlViewHeight = surfaceView.getLayoutParams().height;
                //Toast.makeText(getApplicationContext(), "ggggggg  ------  "+newHeight, Toast.LENGTH_SHORT).show();
/*                surfaceView.setScaleX(.9f);
                surfaceView.setScaleY(.8f);*/
                //surfaceView.zoomTo(zoom, true);
                // surfaceView.zoomBy(factor, true);
                //surfaceView.moveTo(.8f, .001f, 200f, true);
                // surfaceView.moveTo(.98f, .0001f, 200f, true);

                // showToast(String.valueOf(surfaceView.getZoom()));
                //  int screenHeight = getResources().getDisplayMetrics().heightPixels;

                // Animate the view to move down to out of the screen
                // surfaceView.animate().y(screenHeight).setDuration(4000).start();
                surfaceView.setTranslationY(100f);


/*                int width = 800;
                int height = 800;
                surfaceView.getLayoutParams().width = width;
                surfaceView.getLayoutParams().height = height;
                surfaceView.requestLayout();*/
            }
        });

///----------------------Dont--cut--------------------
//        ImageView imageView = (ImageView) playerControlView.findViewById(R.id.my_image_view);
//        String jpg = "http://192.168.0.101:3004/sprite_sheet.png";
//
//        timeBar = playerControlView.findViewById(androidx.media3.ui.R.id.exo_progress);
//        timeBar.addListener(new TimeBar.OnScrubListener() {
//            @Override
//            public void onScrubStart(TimeBar timeBar, long position) {
//
//            }
//
//            @Override
//            public void onScrubMove(TimeBar timeBar, long position) {
//                imageView.setVisibility(View.VISIBLE);
//
//                Glide.with(video.this)
//                        .load(jpg)
////                    .apply(new RequestOptions().transform(new GlideThumbnailTransformation(100)))
//                        .transform(new GlideThumbnailTransformation(position))
//                        .diskCacheStrategy(DiskCacheStrategy.DATA)
//                        .into(imageView);
//
//            }
//
//            @Override
//            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
//                imageView.setVisibility(View.GONE);
//
//
//            }
//        });


        LinearLayout DownlayoutBtnDown = findViewById(id.layoutBtnDown);
        DownlayoutBtnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                String positionm0 = ((playlist_mini_adapter0) recyclerView.getAdapter()).getActiveItem();
                Log.d("----1-----", positionm0);

                try {
                    if (positionm0.contains("notClicked")) {
                        int positionm = ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(Integer.parseInt(posii0)).percenOnly;
                        String id = ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(Integer.parseInt(posii0)).getVidId();

                        Log.d("----11-----", id);
                        if (positionm != -3) {

                            Log.d("----11-----", "is -3 ");
                            DownloadIndex downloadIndex = downloadManager.getDownloadIndex();
                            Download download = downloadIndex.getDownload(id);
                            //     showToast(String.valueOf(positionm + download.state));
                            if (download.state == 1) {
                                DownloadService.sendSetStopReason(
                                        getApplicationContext(), OfflineVideoDownloadService.class, id, 0, /* foreground= */ true);


                            } else if (download.state == 2) {
                                DownloadService.sendSetStopReason(
                                        getApplicationContext(), OfflineVideoDownloadService.class, id, 1, /* foreground= */ true);


                            } else {

                            }


//                        }catch (IOException e) {throw new RuntimeException(e);}

                        } else if (positionm == 100) {
                            Log.d("----1-----", "ig");
                        } else {
                            Log.d("----11-----", "else only  ");
                            ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(Integer.parseInt(posii0)).setpercenOnly(0);
                            ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(Integer.parseInt(posii0)).setPercent0("Downloading... 0%");
                            ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(Integer.parseInt(posii0));
                              String uri = ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(Integer.parseInt(posii0)).getUrl();
                            DownloadRequest downloadRequest = new DownloadRequest.Builder(id, Uri.parse(uri)).build();
                            DownloadService.sendAddDownload(
                                    getApplicationContext(), OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);


                        }

                    } else {
                                                    String id_Clicked = ((playlist_mini_adapter0) recyclerView.getAdapter()).getActiveItemId();
                            DownloadIndex downloadIndex = downloadManager.getDownloadIndex();
                            Download download = downloadIndex.getDownload(id_Clicked);
                        if (Integer.parseInt(positionm0) != -3) {


                            Log.d("----2id11-----", id_Clicked);

                            if (download.state == 1) {
                                DownloadService.sendSetStopReason(
                                        getApplicationContext(), OfflineVideoDownloadService.class, id_Clicked, 0, /* foreground= */ true);


                            } else if (download.state == 2) {
                                DownloadService.sendSetStopReason(
                                        getApplicationContext(), OfflineVideoDownloadService.class, id_Clicked, 1, /* foreground= */ true);


                            } else {
                                Log.d("----2id11-----", "ig");
                            }


                        } else if (Integer.parseInt(positionm0) == 100) {
    Log.d("----2id11-----", "ig");
                        } else {
                            int posi0 = ((playlist_mini_adapter0) recyclerView.getAdapter()).getActiveItemPosi();

                            String uri4 = ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(posi0).getUrl();
                            ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(posi0).setpercenOnly(0);
                            ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(posi0).setPercent0("Downloading... 0%");
                            ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(posi0);
                            Log.d("----2id11-----", posi0+" "+id_Clicked +" "+uri4);

                            DownloadRequest downloadRequest = new DownloadRequest.Builder(id_Clicked, Uri.parse(uri4)).build();
                            DownloadService.sendAddDownload(
                                    getApplicationContext(), OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);


                        }


                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        });


        RelativeLayout relativeLayout0 = findViewById(id.topbar);
        ImageView dlt01 = (ImageView) findViewById(id.dlt);
        ImageView down01 = (ImageView) findViewById(id.downloa);
        ImageView select01 = (ImageView) findViewById(id.selectall);
        ImageView back01 = (ImageView) findViewById(R.id.cut);
        TextView num = (TextView) findViewById(R.id.num0);

        dlt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Toast.makeText( getApplicationContext(),"dlt", Toast.LENGTH_SHORT).show();
                num.setText("");
                for (playlist_mini item : selectedItems) {

                    if (!item.getPercent0().contains("down")) {
                        // Log.d(TAG, "-----------item-mk----------------------- "+item.getName()+"  "+item.getpercenOnly() );


                        DownloadService.sendRemoveDownload(
                                getApplicationContext(), OfflineVideoDownloadService.class, item.msg, /* foreground= */ true);

                        ((playlist_mini_adapter0) recyclerView.getAdapter()).updateItemPercent0AndPerent(Integer.valueOf(item.getEmail()), "down", -3);
//                        ((playlist_mini_adapter) recyclerView.getAdapter()).updateItemPercentAndState(Integer.valueOf( item.getEmail()),"down", -2);

                        ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));


                    }


                }

                unSelect();

            }
        });
        select01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText( getApplicationContext(),"select all", Toast.LENGTH_SHORT).show();


                num.setText(String.valueOf(items.size()));
                ((playlist_mini_adapter0) recyclerView.getAdapter()).updateSelectState(selectState);
                for (playlist_mini item : items) {
                    item.setBackgroundColor(color.download);
                    ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));

                    if (selectedItems.contains(item)) {
                    } else {
                        selectedItems.add(item);
                    }
                }

            }
        });
        back01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelect();
            }
        });
        down01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText( getApplicationContext(),"download", Toast.LENGTH_SHORT).show();
                num.setText("");
                for (playlist_mini item : selectedItems) {


                    if (item.getPercent0().contains("down")) {
                        Log.d(TAG, "-----------item-mk----------------------- " + item.getName() + "  " + item.getEmail());
                        Log.d(TAG, "-----------item-mk-------cc---------------- " + item.msg + "  " + item.url);


                        int positionm = ((playlist_mini_adapter0) recyclerView.getAdapter()).getPositionByName(item.msg);
                        ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(positionm).setpercenOnly(0);
                        ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(positionm).setPercent0("Downloading... 0%");
                        ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(positionm);

                        DownloadRequest downloadRequest = new DownloadRequest.Builder(item.msg, Uri.parse(item.url)).build();
                        DownloadService.sendAddDownload(
                                getApplicationContext(), OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);


                    }
                }
                unSelect();

            }
        });


        handlerThread = new HandlerThread("UpdateHandlerThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        updateRunnable = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateIndex(); // This runs in the HandlerThread
                    }
                });
                handler.postDelayed(this, 100); // Update every 500 ms
            }


        };
        handler.post(updateRunnable);

//        recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();

        //-------------------------------------------------------------------------
        //--------------------------the-end--------------------------------------------
    }


    @UnstableApi
    private void updateIndex() {

        Log.d("ThreadCheck", "Running on thread--------------------------: " + Thread.currentThread().getName());
        List<String> newList = new ArrayList<>();


        for (playlist_mini item : items) {
            if (String.valueOf(item.getpercenOnly()).matches("\\d+")) {
                if (item.getpercenOnly() < 100) {
                    newList.add(item.id);

                }
            } else if (item.getpercenOnly() == -1) {
                if (item.getpercenOnly() < 100) {
                    newList.add(item.id);

                }
            }

        }

        String positionm0 = ((playlist_mini_adapter0) recyclerView.getAdapter()).getActiveItem();


        if (positionm0.contains("notClicked")) {
            int positionm = ((playlist_mini_adapter0) recyclerView.getAdapter()).items.get(Integer.parseInt(posii0)).percenOnly;

            //   Log.d(TAG, "500---------------------main---------- "+positionm  );
//


            runOnUiThread(() -> {

                if (positionm == -3) {
                    btn.setText("Download");
                } else {
                    btn.setText(String.valueOf(positionm) + "%");
                }

            });


        } else {

            runOnUiThread(() -> {
                if (Integer.parseInt(positionm0) == -3) {
                    btn.setText("Download");
                } else {
                    btn.setText(positionm0 + "%");
                }
            });


        }


        ArrayList<String> idlist = new ArrayList<>();
        downloadManager = DemoUtil.getDownloadManager(getApplicationContext());
        List<Download> downloads = downloadManager.getCurrentDownloads();
        for (Download item : downloads) {
            idlist.add(item.request.id);
        }


        for (Download item : downloads) {

            if (newList.contains(item.request.id)) {

                for (int i = 0; i < items.size(); i++) {

                    if (items.get(i).getVidId().equals(item.request.id)) {


                        if ((int) item.getPercentDownloaded() != items.get(i).getpercenOnly()) {

                            final int finalIndex = i;

                            runOnUiThread(() -> {
                                if (item.state == 1) {
                                    ((playlist_mini_adapter0) recyclerView.getAdapter()).updateDownloadPercentage(finalIndex, "Paused.... " + String.valueOf((int) item.getPercentDownloaded()) + "%", (int) item.getPercentDownloaded());

                                } else if (item.state == 2) {
                                    ((playlist_mini_adapter0) recyclerView.getAdapter()).updateDownloadPercentage(finalIndex, "Downloading... " + String.valueOf((int) item.getPercentDownloaded()) + "%", (int) item.getPercentDownloaded());

                                } else {

                                }

                            });


                        }
                    }

                }


            }

        }


        for (String fruit : newList) {
            if (!idlist.contains(fruit)) {
                //   Log.d(TAG, "newList " +newList);
                downloadIndex = downloadManager.getDownloadIndex();


                try {
                    Download download = downloadIndex.getDownload(fruit);
                    if (download != null) {
                        Log.d(TAG, "stuck at 99% fixing it now " + fruit + " " + download.state);
                        if (download.state == 3) {


                            int positionm = ((playlist_mini_adapter0) recyclerView.getAdapter()).getPositionByName(download.request.id);

                            runOnUiThread(() -> {
                                ((playlist_mini_adapter0) recyclerView.getAdapter()).updateDownloadPercentage0(positionm, "100%", 100); // Assuming item.getPercentDownloaded() returns an int


                                ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(positionm); // Assuming item.getPercentDownloaded() returns an int

                            });

                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }


    }


    @Override
    @UnstableApi
    public void onLongClicked(playlist_mini playlistMini, int position) {

//        TextView num = (TextView) findViewById(R.id.num0);
//        if (selectState.contains("normal")) {
//            num.setText("1");
//            RelativeLayout topbar = findViewById(id.topbar);
//            topbar.setVisibility(View.VISIBLE);
//            selectState = "select";
//            playlistMini.setBackgroundColor(color.green);
//            for (playlist_mini item : items) {
//
//
//                ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));
//            }
//            ((playlist_mini_adapter0) recyclerView.getAdapter()).updateSelectState(selectState); // Assuming item.getPercentDownloaded() returns an int
//
////            playlistMini.setBackgroundColor();
//            selectedItems.add(playlistMini);
//
//            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//            if (vibrator != null) {
//                vibrator.vibrate(80);
//
//            }
//
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handlerThread.quitSafely();
        handler.removeCallbacks(updateRunnable);


        if (simpleCache != null) {
            simpleCache.release();
            simpleCache = null;
        }
        deleteCacheDir(cacheDir);
    }


    @Override
    public void onBackPressed() {
        View fullscreen = playerControlView.findViewById(R.id.exo_fullscreen_icon);
        FrameLayout timebar = playerControlView.findViewById(androidx.media3.ui.R.id.exo_bottom_bar);
        FrameLayout frameLayout = findViewById(R.id.videeo_view);


        if (isLandscape) {

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(color.title_bg));


            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fullscreen.getLayoutParams();
            int dpValue = 20;
            float density = getResources().getDisplayMetrics().density;
            int pixelValue = (int) (dpValue * density);
            layoutParams.bottomMargin = pixelValue;
            fullscreen.setLayoutParams(layoutParams);


            ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) timebar.getLayoutParams();
            int dpValue1 = 20;
            float density1 = getResources().getDisplayMetrics().density;
            int pixelValue1 = (int) (dpValue1 * density1);
            layoutParams1.bottomMargin = pixelValue1;
            timebar.setLayoutParams(layoutParams1);


            LinearLayout downloadbtn = findViewById(id.downloadbtn);
            downloadbtn.setVisibility(View.VISIBLE);
  /*                   View download = playerControlView.findViewById(R.id.download);
                    download.setVisibility(View.INVISIBLE);
                    View settings = playerControlView.findViewById(R.id.setting);
                    settings.setVisibility(View.INVISIBLE);*/
            View myTextView = playerControlView.findViewById(R.id.myTextView);
            myTextView.setVisibility(View.INVISIBLE);

            View ghost = playerControlView.findViewById(R.id.ghost);
            ghost.setVisibility(View.GONE);


            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            int heightInDp = 200; // Set your desired height in dp here
            int heightInPixels = (int) (heightInDp * getResources().getDisplayMetrics().density);
            frameLayout.getLayoutParams().height = heightInPixels;
            frameLayout.requestLayout();

            isLandscape = false;
        } else if (selectState.contains("select")) {
            unSelect();
        } else {
            super.onBackPressed();
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void deleteCacheDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteCacheDir(child);
                }
            }
            dir.delete();
        } else if (dir != null && dir.isFile()) {
            dir.delete();
        }
    }


    public void unSelect() {
        TextView selectCountText = (TextView) findViewById(R.id.num0);
        RelativeLayout topbar = findViewById(id.topbar);

        selectCountText.setText("");
        selectState = "normal";
        ((playlist_mini_adapter0) recyclerView.getAdapter()).updateSelectState(selectState);
        topbar.setVisibility(View.GONE);
        for (playlist_mini item : items) {
            item.setBackgroundColor(color.title_bg);
            ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));
        }
        selectedItems.clear();
    }


    @Override
    public void onIteamClicked(playlist_mini playlistMini) {



        TextView num = (TextView) findViewById(R.id.num0);


        if (selectState.contains("select")) {

            if (selectedItems.contains(playlistMini)) {
                String str = num.getText().toString();
                num.setText(String.valueOf(Integer.valueOf(str) - 1));
                playlistMini.setBackgroundColor(color.title_bg);
                ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(Integer.valueOf(playlistMini.getEmail()));
                ((playlist_mini_adapter0) recyclerView.getAdapter()).updateSelectState(selectState);
                selectedItems.remove(playlistMini);
                Log.d(TAG, "ids------------select-clicked------------------- " + selectedItems);
                if (selectedItems.size() == 0) {
                    num.setText("");
                    selectState = "normal";
                    ((playlist_mini_adapter0) recyclerView.getAdapter()).updateSelectState(selectState);
                    RelativeLayout topbar = findViewById(id.topbar);
                    topbar.setVisibility(View.GONE);

                }

            } else {
                String str = num.getText().toString();
                num.setText(String.valueOf(Integer.valueOf(str) + 1));
                playlistMini.setBackgroundColor(color.red);
                ((playlist_mini_adapter0) recyclerView.getAdapter()).refresh(Integer.valueOf(playlistMini.getEmail()));
                ((playlist_mini_adapter0) recyclerView.getAdapter()).updateSelectState(selectState);
                selectedItems.add(playlistMini);
                Log.d(TAG, "ids------------select-2-clicked------------------- " + selectedItems);

            }


        } else {


            String title = playlistMini.getLecInt();
//        showToast(String.valueOf(tosub));
            try {
                JSONObject lecy = tosub.getJSONObject(title);
                String srccy = lecy.getString("src");
                String title010 = lecy.getString("title");

                TextView title55 = playerControlView.findViewById(R.id.myTextView);
                TextView title102 = findViewById(R.id.title);

                title55.setText(preTitle + " | " + title010);
                title102.setText(preTitle + " | " + title010);


                TextView btn = (TextView) findViewById(id.btnpercent);
                if (playlistMini.getPercent0().contains("down")) {
                    btn.setText("Download");
                } else {
                    btn.setText(playlistMini.getPercent0());
                }


                String aesKeyString = "1234567890abcdef";
                byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);

                mediaPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC);
                DataSource.Factory cacheDataSourceFactory = DemoUtil.getCacheNHttpAesDataSourceFactory(getApplicationContext(),aesKey);

        //        DataSource.Factory aesDataSourceFactory = () -> new AesCipherDataSource(aesKey, cacheDataSourceFactory.createDataSource());

                ProgressiveMediaSource mediaSource0 = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(srccy));


                mediaPlayer.setMediaSource(mediaSource0);


                mediaPlayer.prepare();

                mediaPlayer.play();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "JSON parsing error", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    @UnstableApi
    public void onDownClicked(playlist_mini playlistMini, int position) {


//        DownloadService.sendRemoveDownload(
//                context, OfflineVideoDownloadService.class, playlistMini.getName(), /* foreground= */ true);


        //((dmanager_adapter) recyclerView.getAdapter()).removeItem(position); // Assuming item.getPercentDownloaded() returns an int

    }

    @Override
    public void onPauseClicked(playlist_mini playlistMini, int position) {


    }


    public void cmnt(View v) {

        bottomSheetDialog = new BottomSheetDialog(video.this, R.style.BottomSheetTheme);
        View bsView = LayoutInflater.from(video.this).inflate(R.layout.bottom_sheet_layout,
                v.findViewById(R.id.bottom_sheet));
        bsView.findViewById(id.speed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setting.itemView.performClick();
                bottomSheetDialog.dismiss();
                speedBottomSheet = new BottomSheetDialog(video.this, R.style.BottomSheetTheme);
                View bsViewspeed = LayoutInflater.from(video.this).inflate(layout.speed,
                        v.findViewById(R.id.bottom_sheet));
                speedBottomSheet.setContentView(bsViewspeed);
                speedBottomSheet.show();
                // bottomSheetDialog.dismiss();
                // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        bottomSheetDialog.setContentView(bsView);

        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog dialog = (BottomSheetDialog) dialogInterface;
                FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        bottomSheetDialog.show();

    }

    ;
}

//