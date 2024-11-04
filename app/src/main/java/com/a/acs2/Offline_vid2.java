package com.a.acs2;


import static androidx.fragment.app.FragmentManager.TAG;
import static com.a.acs2.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.C;
import androidx.media3.common.PlaybackException;
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
import androidx.media3.ui.DefaultTimeBar;
import androidx.media3.ui.PlayerControlView;
import androidx.media3.ui.PlayerView;
import androidx.media3.common.util.UnstableApi;

import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.media3.common.MediaItem;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.otaliastudios.zoom.ZoomSurfaceView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@UnstableApi
public class Offline_vid2 extends AppCompatActivity implements SelectListenermini {

    private ExoPlayer mediaPlayer;
    private ZoomSurfaceView surfaceView;
    private PlayerControlView playerControlView;
    private ProgressBar progressBar;
    private LottieAnimationView loading;
    private GestureDetector gestureDetector;
    private SimpleCache cache;

    private SimpleCache simpleCache;
    private File cacheDir;
    private boolean isLandscape = false;

    private Handler handler;

    private Runnable updateRunnable;
    RecyclerView recyclerView;
    private List<playlist_mini> items;
    private List<playlist_mini> selectedItems = new ArrayList<playlist_mini>();
    Context context = this;
    DataSource.Factory cacheDataSourceFactory;
    private TextView selectCountText;
    //    RelativeLayout topbar;
    String srcc4;
    String preTitle;


    private String selectState = "normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(getResources().getColor(color.title_bg));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(layout.activity_offline_vid2);

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container01, new videoCachePlayer())
//                .commit();


//        selectCountText = (TextView) findViewById(R.id.num0);
//        topbar = findViewById(id.topbar);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<playlist_mini>();
//        items.add(new playlist_mini("ids", "ids", R.drawable.math, ContextCompat.getColor(this, color.title_bg)));


        loading = findViewById(R.id.loading);

        View touch_r = findViewById(R.id.touch_r);


        Bundle bundle = getIntent().getExtras();
        String ids = bundle.getString("ids");
        //   Toast.makeText(context, ids, Toast.LENGTH_SHORT).show();
        String lecs = bundle.getString("lecs");
        String title010 = bundle.getString("title");
        preTitle = bundle.getString("preTitle");
        srcc4 = bundle.getString("src");
        try {
            DownloadManager downloadManager = DemoUtil.getDownloadManager(this);

            DownloadIndex downloadIndex = downloadManager.getDownloadIndex();


            JSONObject jsonObject = new JSONObject(lecs);

//            JSONObject subObject = jsonObject.getJSONObject(book);
//            JSONObject chapObject = subObject.getJSONObject(sub);
//            JSONObject lec = chapObject.getJSONObject(lecc);
//            String srcc = lec.getString("src");
//            src = srcc;
////                 showToast(String.valueOf(chapObject));
//            tosub=chapObject;


            // Iterate over the keys of the JSONObject
            Iterator<String> keys = jsonObject.keys();
            Log.d(TAG, "keys--------------------------------------------------- " + keys);
            int counter = 0;
            while (keys.hasNext()) {
                String key = keys.next();

                Log.d(TAG, "keyyyyyyyyyyyy---------------- " + key);
                String value = jsonObject.getString(key);
                JSONObject jsonObject00 = new JSONObject(value);
                Log.d(TAG, "val---------------- " + jsonObject00);
                String src = jsonObject00.getString("src");
                String id = jsonObject00.getString("id");
                String lecTitle = jsonObject00.getString("title");
//                 items.add(new playlist_mini("key","src", R.drawable.math));
                //items.add(new playlist_mini(key, "src", R.drawable.math, ContextCompat.getColor(this, color.title_bg)));

                String num = String.valueOf(counter);
//                if (key.equals(lecc)){
//                    //showToast("eq");
//                    items.add(new playlist_mini(key, num, R.drawable.math,  ContextCompat.getColor(this, color.download)));
//                }else {
//                    items.add(new playlist_mini(key, num, R.drawable.math, ContextCompat.getColor(this, color.title_bg)));
//                }

                int clr = R.color.title_bg;
                if (ids.matches(key)) {
                    clr = color.download;
                } else {
                    clr = color.title_bg;
                }

//                if (ids.contains(id)) {

                Download download = downloadIndex.getDownload(id);
                if (2 == 2) {
                    if (download != null) {
                        if (download.getPercentDownloaded() == 100) {
                            items.add(new playlist_mini(lecTitle, num, R.drawable.math, ContextCompat.getColor(this, clr), "max", 100, "0", src, "title_bg", "", key, id, lecTitle, drawable.play));

                        } else {
                            if (download.state == 2) {
                                items.add(new playlist_mini(lecTitle, num, R.drawable.math, ContextCompat.getColor(this, clr), String.valueOf((int) download.getPercentDownloaded() + "%"), (int) download.getPercentDownloaded(), "state2", src, "title_bg", "", key, id, lecTitle, drawable.play));

                            } else if (download.getPercentDownloaded() > 0) {
                                items.add(new playlist_mini(lecTitle, num, R.drawable.math, ContextCompat.getColor(this, clr), String.valueOf((int) download.getPercentDownloaded() + "%"), (int) download.getPercentDownloaded(), "state1", src, "title_bg", "", key, id, lecTitle, drawable.pause));

                            } else {

                            }

                        }
                    } else {
                        //  items.add(new playlist_mini(id, num, R.drawable.math, ContextCompat.getColor(this, color.offline_bg), "down", -2, "0", src, "offline_bg", "", key, id, lecTitle));

                    }
                } else {
                    //  items.add(new playlist_mini(id, num, R.drawable.math, ContextCompat.getColor(this, color.offline_bg), "down", -2, "0", src, "offline_bg", "", key, id, lecTitle));

                }


                counter++;
            }

            recyclerView.setAdapter(new playlist_mini_adapter(getApplicationContext(), items, this, selectState));


        } catch (Exception e) {
            e.printStackTrace();
        }

//        Log.d(TAG, "ids---------------- " +ids+lecs );


/*
        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                        1000 , 20000,  1000, 1000
                )
//                .setAllocator(new DefaultAllocator(true, 16))  // Set the DefaultAllocator
                .setPrioritizeTimeOverSizeThresholds(true)
//                .setTargetBufferBytes(-1)
                .build();*/


        mediaPlayer = new ExoPlayer.Builder(this)
                /* .setLoadControl(loadControl)*/
                .build();

        surfaceView = findViewById(R.id.surface_view);
        playerControlView = findViewById(R.id.player_control_view);
        //-------------------------------------------------------------


        View fullscreen = playerControlView.findViewById(R.id.exo_fullscreen_icon);

        FrameLayout frameLayout = findViewById(R.id.videeo_view);


        FrameLayout timebar = playerControlView.findViewById(androidx.media3.ui.R.id.exo_bottom_bar);

        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLandscape) {
                   // Toast.makeText(context, "kkkk", Toast.LENGTH_SHORT).show();
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
//
//
//
                    LinearLayout downloadbtn = findViewById(id.downloadbtn);
                    downloadbtn.setVisibility(View.INVISIBLE);

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


        // Add the custom button to the player control view
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.END | Gravity.BOTTOM; // Adjust the gravity as per your requirement
        /*        playerControlView.addView(customButton, params);*/


        //-------------------------------------------


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

        // Set the player to the PlayerControlView
        playerControlView.setPlayer(mediaPlayer);


        String aesKeyString = "1234567890abcdef";
        byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);


        mediaPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC);
        DataSource.Factory cacheDataSourceFactory = DemoUtil.getCacheAesDataSourceFactory(this, aesKey);

        ProgressiveMediaSource mediaSource0 = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(MediaItem.fromUri(srcc4));


        mediaPlayer.setMediaSource(mediaSource0);


        // MediaItem mediaItem = new MediaItem.Builder().setUri(value).build();


        //  mediaPlayer.setMediaItem(mediaItem);

        ProgressBar load = findViewById(R.id.exo_buffering_progress_bar0);
        ImageButton play_pause = playerControlView.findViewById(androidx.media3.ui.R.id.exo_play_pause);
        mediaPlayer.addListener(new ExoPlayer.Listener() {
            @Override
            public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
                //  Toast.makeText(getApplicationContext(), "This is a toast message", Toast.LENGTH_SHORT).show();
                surfaceView.setContentSize(videoSize.width, videoSize.height);
            }


            @Override
            public void onPlayerError(PlaybackException error) {
                mediaPlayer.seekTo(0);
                mediaPlayer.prepare();
                mediaPlayer.play();
                Log.w("Exo-err-----------", error.getMessage());

            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {

                if (mediaPlayer.getCurrentPosition() == 0) {

                    // mediaPlayer.play();
                }

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

        TextView title55 = playerControlView.findViewById(R.id.myTextView);
        TextView title102 = findViewById(R.id.title);

        title55.setText(preTitle + " | " + title010);
        title102.setText(preTitle + " | " + title010);


//        surfaceView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (playerControlView.isShown()) {
//                    loading.setVisibility(View.GONE);
//                    playerControlView.hide();
//                } else {
//                    loading.setVisibility(View.GONE);
//                    playerControlView.show();
//                }
//            }
//        });

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
                return false;


            }


        });


        // Set onTouchListener for the view
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


//        RelativeLayout relativeLayout0 = findViewById(id.topbar);
//        ImageView dlt01 = (ImageView) findViewById(id.dlt);
//        ImageView down01 = (ImageView) findViewById(id.downloa);
//        ImageView select01 = (ImageView) findViewById(id.selectall);
//        ImageView back01 = (ImageView) findViewById(R.id.cut);
//        TextView num = (TextView) findViewById(R.id.num0);
//
//        dlt01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                    Toast.makeText( getApplicationContext(),"dlt", Toast.LENGTH_SHORT).show();
//                num.setText("");
//                for (playlist_mini item : selectedItems) {
//
//                    if (!item.getPercent0().contains("down")) {
//                        // Log.d(TAG, "-----------item-mk----------------------- "+item.getName()+"  "+item.getpercenOnly() );
//
//
//                        DownloadService.sendRemoveDownload(
//                                context, OfflineVideoDownloadService.class, item.getName(), /* foreground= */ true);
//
//                        ((playlist_mini_adapter) recyclerView.getAdapter()).updateItemPercent0AndPerent(Integer.valueOf(item.getEmail()), "down", -2);
////                        ((playlist_mini_adapter) recyclerView.getAdapter()).updateItemPercentAndState(Integer.valueOf( item.getEmail()),"down", -2);
//
//                        ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));
//
//
//                    }
//
//
//                }
//
//                unSelect();
//
//            }
//        });
//        select01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText( getApplicationContext(),"select all", Toast.LENGTH_SHORT).show();
//
//
//                num.setText(String.valueOf(items.size()));
//                ((playlist_mini_adapter) recyclerView.getAdapter()).updateSelectState(selectState);
//                for (playlist_mini item : items) {
//                    item.setBackgroundColor(color.download);
//                    ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));
//
//                    if (selectedItems.contains(item)) {
//                    } else {
//                        selectedItems.add(item);
//                    }
//                }
//
//            }
//        });
//        back01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                unSelect();
//            }
//        });
//        down01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText( getApplicationContext(),"download", Toast.LENGTH_SHORT).show();
//                num.setText("");
//                for (playlist_mini item : selectedItems) {
//
//
//                    if (item.getPercent0().contains("down")) {
//                        Log.d(TAG, "-----------item-mk----------------------- " + item.getName() + "  " + item.getEmail());
//
//
//                        ((playlist_mini_adapter) recyclerView.getAdapter()).updateItemPercentAndState(Integer.valueOf(item.getEmail()), 0, "state2");
//
//
//                        DownloadRequest downloadRequest = new DownloadRequest.Builder(item.getName(), Uri.parse(item.getUrl())).build();
//                        DownloadService.sendAddDownload(
//                                context, OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);
//
//
////                    playlistMini.setpercenOnly(0);
////                    playlistMini.setPercent0("starting...");
////
////                    playlistMini.setMsg("state2");
//                        ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));
//
//
//                    }
//                }
//                unSelect();
//
//            }
//        });
//


//        Button online0 = findViewById(R.id.btn2);
//        Button online01 = findViewById(R.id.btn);
//        online0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FrameLayout fr=findViewById(R.id.videeo_view);
////                    fr.setVisibility(View.GONE);
//                Button b4 = findViewById(R.id.btn);
//                Button online = findViewById(R.id.btn2);
//                online.setVisibility(View.GONE);
//                b4.setVisibility(View.GONE);
//
//
//                String url = ((playlist_mini_adapter) recyclerView.getAdapter()).lastClickedUrl();
//
//                Offline_vid3 Offline_vid3 = new Offline_vid3();
//
//
//                Bundle bundle = new Bundle();
////                bundle.putStringArray("keysArray", keysArray);
//                bundle.putString("src", url);
//
//
//                Offline_vid3.setArguments(bundle);
//
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container01, Offline_vid3)
//                        .commit();
////
////                surfaceView.setVisibility(View.VISIBLE);
////                playerControlView.setVisibility(View.VISIBLE);
////                online0.setVisibility(View.GONE);
////                online01.setVisibility(View.GONE);
////
////                mediaPlayer=new ExoPlayer.Builder(getApplicationContext()).build();
////                playerControlView.setPlayer(mediaPlayer);
////                MediaItem mediaItem= MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fvideoplayback.mp4?alt=media&token=db7395ee-3e7f-4709-9ba9-305743b5db91");
////                mediaPlayer.setMediaItem(mediaItem);
////                mediaPlayer.prepare();
////
////                mediaPlayer.play();
//
//
////
////                MediaItem mediaItem = new MediaItem.Builder().setUri(srccy).build();
////                mediaPlayer.setMediaItem(mediaItem);
////                mediaPlayer.prepare();
////                mediaPlayer.play();
//
//
//            }
//
//
//        });
//

        handler = new Handler();
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateIndex();
                handler.postDelayed(this, 100); // Update every second
            }
        };
        handler.post(updateRunnable);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateRunnable);
        mediaPlayer.release();

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIteamClicked(playlist_mini playlistMini) {
        TextView num = (TextView) findViewById(R.id.num0);


        if (selectedItems.size() == 0) {

        }
        if (selectState.contains("select")) {
//
//            if (selectedItems.contains(playlistMini)) {
//                String str = num.getText().toString();
//                num.setText(String.valueOf(Integer.valueOf(str) - 1));
//                playlistMini.setBackgroundColor(color.title_bg);
//                ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(Integer.valueOf(playlistMini.getEmail()));
//                ((playlist_mini_adapter) recyclerView.getAdapter()).updateSelectState(selectState);
//                selectedItems.remove(playlistMini);
//                Log.d(TAG, "ids------------select-clicked------------------- " + selectedItems);
//                if (selectedItems.size() == 0) {
//                    num.setText("");
//                    selectState = "normal";
//                    ((playlist_mini_adapter) recyclerView.getAdapter()).updateSelectState(selectState);
//                    RelativeLayout topbar = findViewById(id.topbar);
//                    topbar.setVisibility(View.GONE);
//
//                }
//
//            } else {
//                String str = num.getText().toString();
//                num.setText(String.valueOf(Integer.valueOf(str) + 1));
//                playlistMini.setBackgroundColor(color.download);
//                ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(Integer.valueOf(playlistMini.getEmail()));
//                ((playlist_mini_adapter) recyclerView.getAdapter()).updateSelectState(selectState);
//                selectedItems.add(playlistMini);
//                Log.d(TAG, "ids------------select-2-clicked------------------- " + selectedItems);
//
//            }


        } else {


//
//        Bundle bundle = getIntent().getExtras();
//        String ids = bundle.getString("ids");
            if (String.valueOf(playlistMini.getpercenOnly()).matches("\\d+")) {
                int fragmentCount = getSupportFragmentManager().getFragments().size();
                Log.w("FragmentCount", "Current fragment count: " + fragmentCount);


                if (fragmentCount == 0) {
                    mediaPlayer.pause();


                    mediaPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC);


                    String aesKeyString = "1234567890abcdef";
                    byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);


                    DataSource.Factory cacheDataSourceFactory = DemoUtil.getCacheAesDataSourceFactory(this, aesKey);

                    ProgressiveMediaSource mediaSource0 = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(playlistMini.getUrl()));


                    mediaPlayer.setMediaSource(mediaSource0);


                    mediaPlayer.prepare();
                    mediaPlayer.setPlayWhenReady(true);
                    mediaPlayer.play();


                    TextView title55 = playerControlView.findViewById(R.id.myTextView);
                    TextView title102 = findViewById(R.id.title);

                    title55.setText(preTitle + " | " + playlistMini.getLectitle());
                    title102.setText(preTitle + " | " + playlistMini.getLectitle());
                } else {
//

                    TextView title55 = playerControlView.findViewById(R.id.myTextView);
                    TextView title102 = findViewById(R.id.title);
                    title55.setText(preTitle + " | " + playlistMini.getLectitle());
                    title102.setText(preTitle + " | " + playlistMini.getLectitle());

                    ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(playlistMini.getUrl()));


                    mediaPlayer.setMediaSource(mediaSource);


                    mediaPlayer.prepare();
                    mediaPlayer.setPlayWhenReady(true);
                    mediaPlayer.play();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    List<Fragment> fragments = fragmentManager.getFragments();

                    if (fragments != null) {
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        for (Fragment fragment : fragments) {
                            if (fragment != null) {
                                transaction.remove(fragment);  // Remove each fragment
                            }
                        }
                        transaction.commitAllowingStateLoss();  // Commit the transaction
                    }


//                    surfaceView.setVisibility(View.VISIBLE);
//                    playerControlView.setVisibility(View.VISIBLE);
//                    Button b4 = findViewById(R.id.btn);
//                    Button online = findViewById(R.id.btn2);
//                    online.setVisibility(View.GONE);
//                    b4.setVisibility(View.GONE);
//                    FrameLayout fr0 = findViewById(R.id.videeo_view);
//                    fr0.setVisibility(View.VISIBLE);


                }


            } else {
                int fragmentCount = getSupportFragmentManager().getFragments().size();

                if (fragmentCount == 0) {
                    mediaPlayer.pause();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();

                if (fragments != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    for (Fragment fragment : fragments) {
                        if (fragment != null) {
                            transaction.remove(fragment);  // Remove each fragment
                        }
                    }
                    transaction.commitAllowingStateLoss();  // Commit the transaction
                }


                surfaceView.setVisibility(View.GONE);
                playerControlView.setVisibility(View.GONE);
                Button b4 = findViewById(R.id.btn);
                Button online = findViewById(R.id.btn2);
                online.setVisibility(View.VISIBLE);
                b4.setVisibility(View.VISIBLE);


            }
        }


    }

    @Override
    @UnstableApi
    public void onLongClicked(playlist_mini playlistMini, int position) {
//        TextView num = (TextView) findViewById(R.id.num0);
//        if (selectedItems.size() == 0) {
//
//        }
//        if (selectState.contains("normal")) {
//            num.setText("1");
//            RelativeLayout topbar = findViewById(id.topbar);
//            topbar.setVisibility(View.VISIBLE);
//            selectState = "select";
//            playlistMini.setBackgroundColor(color.download);
//            for (playlist_mini item : items) {
//
//
//                ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));
//            }
//            ((playlist_mini_adapter) recyclerView.getAdapter()).updateSelectState(selectState); // Assuming item.getPercentDownloaded() returns an int
//
////            playlistMini.setBackgroundColor();
//            selectedItems.add(playlistMini);
//
//            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//            if (vibrator != null) {
//                vibrator.vibrate(80);
//
//            }
//
//        }

        //showToast("so long");

    }

    @Override
    @UnstableApi
    public void onDownClicked(playlist_mini playlistMini, int position) {

//        if (playlistMini.getPercent0().contains("down")) {
//            // showToast(String.valueOf(playlistMini.getUrl()));
//
//            // Initialize the download manager and download tracker
//            playlistMini.setpercenOnly(0);
//            playlistMini.setPercent0("starting...");
//
//            playlistMini.setMsg("state2");
//            ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(position); // Assuming item.getPercentDownloaded() returns an int
//
//            DownloadManager downloadManager = DemoUtil.getDownloadManager(this);
//
//            DownloadRequest downloadRequest = new DownloadRequest.Builder(playlistMini.getName(), Uri.parse(playlistMini.getUrl())).build();
//            DownloadService.sendAddDownload(
//                    context, OfflineVideoDownloadService.class, downloadRequest, /* foreground= */ true);
//
//            ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(position); // Assuming item.getPercentDownloaded() returns an int
//
//
//        }


    }

    @Override
    public void onPauseClicked(playlist_mini playlistMini, int position) {


        try {
            DownloadManager downloadManager = DemoUtil.getDownloadManager(this);
            DownloadIndex downloadIndex0 = downloadManager.getDownloadIndex();
            Download download = downloadIndex0.getDownload(playlistMini.getVidId());

            int positionm = ((playlist_mini_adapter) recyclerView.getAdapter()).getPositionByName(playlistMini.getVidId());


            if (download != null) {
                Log.d(TAG, "ids------------0000000000000000000000000000---- ");


                if (download.state == 1) {
                    if (positionm != -1) {
                        Log.d(TAG, "ids----------1111111111111111111111111111111111------ ");

                        // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                        ((playlist_mini_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, drawable.pause); // Assuming item.getPercentDownloaded() returns an int

                    }
                    DownloadService.sendSetStopReason(
                            getApplicationContext(), OfflineVideoDownloadService.class, playlistMini.getVidId(), 0, /* foreground= */ true);

                } else if (download.state == 2) {
                    if (positionm != -1) {
                        Log.d(TAG, "ids---------2222222222222222222222222222222222222------- ");

                        // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                        ((playlist_mini_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int

                    }
                    DownloadService.sendSetStopReason(
                            getApplicationContext(), OfflineVideoDownloadService.class, playlistMini.getVidId(), 1, /* foreground= */ true);

                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @UnstableApi
    private void updateIndex() {
        List<String> newList = new ArrayList<>();


        for (playlist_mini item : items) {
            if (String.valueOf(item.getpercenOnly()).matches("\\d+")) {
                if (item.getpercenOnly() < 100) {
                    newList.add(item.getVidId());

                }
            } else if (item.getpercenOnly() == -1) {
                if (item.getpercenOnly() < 100) {
                    newList.add(item.getVidId());

                }
            }

        }
        //Log.d(TAG, "500---------------------main---------- "  );
//

        ArrayList<String> idlist = new ArrayList<>();
        DownloadManager downloadManager = DemoUtil.getDownloadManager(this);
        List<Download> downloads = downloadManager.getCurrentDownloads();
        for (Download item : downloads) {
            idlist.add(item.request.id);
        }
//        System.out.println("276...---"+newList);


        for (Download item : downloads) {

            if (newList.contains(item.request.id)) {

                for (int i = 0; i < items.size(); i++) {

                    if (items.get(i).getVidId().equals(item.request.id)) {


                        if ((int) item.getPercentDownloaded() != items.get(i).getpercenOnly()) {

                            ((playlist_mini_adapter) recyclerView.getAdapter()).updateDownloadPercentage(i, "Downloading... " + String.valueOf((int) item.getPercentDownloaded()) + "%", (int) item.getPercentDownloaded());

                        }
                    }

                }


            }

        }

        for (String fruit : newList) {
            if (!idlist.contains(fruit)) {
                Log.d(TAG, "stuck at 99% fixing it now ");
                DownloadIndex downloadIndex = downloadManager.getDownloadIndex();


                try {
                    Download download = downloadIndex.getDownload(fruit);
                    if (download != null) {
                        if (download.state == 3) {


                            int positionm = ((playlist_mini_adapter) recyclerView.getAdapter()).getPositionByName(download.request.id);
                            if (positionm != -1) {
                                // Toast.makeText(getApplicationContext(),  String.valueOf(positionm), Toast.LENGTH_SHORT).show();
                                ((playlist_mini_adapter) recyclerView.getAdapter()).updateDownloadPercentage(positionm, "Downloading... 100%", 100); // Assuming item.getPercentDownloaded() returns an int

                            }

                            ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(positionm); // Assuming item.getPercentDownloaded() returns an int


                            //  ((dmanager_adapter) recyclerView.getAdapter()).updateDownloadPercentage(0, String.valueOf(100)); // Assuming item.getPercentDownloaded() returns an int


                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }







              DownloadIndex  downloadIndex = downloadManager.getDownloadIndex();
        for (playlist_mini itemm : items) {
            try {
            Download download = downloadIndex.getDownload(itemm.getVidId());

                int positionm = ((playlist_mini_adapter) recyclerView.getAdapter()).getPositionByName(itemm.getVidId());

                if(download.state==2){
            ((playlist_mini_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.play); // Assuming item.getPercentDownloaded() returns an int

        }else if(download.state==1){
            ((playlist_mini_adapter) recyclerView.getAdapter()).updatePauseImg(positionm, R.drawable.pause); // Assuming item.getPercentDownloaded() returns an int

        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        }













    }


    public void unSelect() {

//        selectCountText.setText("");
//        selectState = "normal";
//        ((playlist_mini_adapter) recyclerView.getAdapter()).updateSelectState(selectState);
//        topbar.setVisibility(View.GONE);
//        for (playlist_mini item : items) {
//            item.setBackgroundColor(color.title_bg);
//            ((playlist_mini_adapter) recyclerView.getAdapter()).refresh(Integer.valueOf(item.getEmail()));
//        }
//        selectedItems.clear();
    }

    @Override
    public void onBackPressed() {
        View fullscreen = playerControlView.findViewById(R.id.exo_fullscreen_icon);

        FrameLayout frameLayout = findViewById(R.id.videeo_view);


        FrameLayout timebar = playerControlView.findViewById(androidx.media3.ui.R.id.exo_bottom_bar);

        if (selectState.contains("select")) {
            unSelect();
        }else   if (isLandscape) {
          //  Toast.makeText(context, "kkkk", Toast.LENGTH_SHORT).show();
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
        }
        else {
            super.onBackPressed();
        }

    }


}
