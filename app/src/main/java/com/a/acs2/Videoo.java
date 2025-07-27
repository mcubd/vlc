package com.a.acs2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
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


import com.otaliastudios.zoom.ZoomSurfaceView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.media.MediaMetadataRetriever;
@UnstableApi
public class Videoo extends AppCompatActivity {

    private ExoPlayer mediaPlayer;
    private ZoomSurfaceView surfaceView;
    private PlayerControlView playerControlView;
    private ProgressBar progressBar;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        setContentView(R.layout.activity_videoo);

        String videoPath = getIntent().getStringExtra("videoPath");



        mediaPlayer = new ExoPlayer.Builder(this)
                /* .setLoadControl(loadControl)*/
                .build();

        surfaceView = findViewById(R.id.surface_view);
        playerControlView = findViewById(R.id.player_control_view);
        //-------------------------------------------------------------


        View fullscreen = playerControlView.findViewById(R.id.exo_fullscreen_icon);



      /*  fullscreen.setOnClickListener(new View.OnClickListener() {
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

*/



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



        Uri videoUri = Uri.parse(videoPath);


        MediaItem mediaItem = MediaItem.fromUri(videoUri);


        mediaPlayer.setMediaItem(mediaItem);


        // MediaItem mediaItem = new MediaItem.Builder().setUri(value).build();


        //  mediaPlayer.setMediaItem(mediaItem);



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



        });


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
                return false;


            }


        });


    }
}