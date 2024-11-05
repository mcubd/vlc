package com.a.acs2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.cache.SimpleCache;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerControlView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.otaliastudios.zoom.ZoomSurfaceView;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link videoCachePlayer#newInstance} factory method to
 * create an instance of this fragment.
 */
@UnstableApi
public class videoCachePlayer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ExoPlayer mediaPlayer;
    private ZoomSurfaceView surfaceView;
    private PlayerControlView playerControlView;
    private ProgressBar progressBar;

    private SimpleCache cache;

    private SimpleCache simpleCache;
    private File cacheDir;
    private boolean isLandscape = true;



    DataSource.Factory cacheDataSourceFactory;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public videoCachePlayer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment videoCachePlayer.
     */
    // TODO: Rename and change types and number of parameters
    public static videoCachePlayer newInstance(String param1, String param2) {
        videoCachePlayer fragment = new videoCachePlayer();
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
        View view = inflater.inflate(R.layout.fragment_video_cache_player, container, false);

        mediaPlayer = new ExoPlayer.Builder(getContext())
                /* .setLoadControl(loadControl)*/
                .build();

        surfaceView = view.findViewById(R.id.surface_view);

        playerControlView = view.findViewById(R.id.player_control_view);



        View fullscreen = playerControlView.findViewById(R.id.exo_fullscreen_icon);


        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLandscape){
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    isLandscape=false;
                } else  {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    isLandscape=true;
                }
            }
        });








        ImageButton customButton = new ImageButton(getContext());
        customButton.setImageResource(R.drawable.fullscreen_exit); // Set your button icon here
        /*customButton.setBackgroundColor(Color.TRANSPARENT);*/
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
//                showToast("Custom button clicked!");
//                int rotation =  getWindowManager().getDefaultDisplay().getRotation();
//                showToast(String.valueOf(rotation));
//                if (isLandscape){
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//                    boolean    isLandscape=false;
//                } else  {
//                    boolean  isLandscape=true;
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//
//                }

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
//        playerControlView.setPlayer(mediaPlayer);
//        mediaPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC);
//        cacheDataSourceFactory = DemoUtil.getOnlyCacheDataSourceFactory(getContext());
//
//        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
//                .createMediaSource(MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Foutput.mp4?alt=media&token=caeb84ea-974a-4415-89e3-2819af819ca6"));
//
//
//        mediaPlayer.setMediaSource(mediaSource);












        mediaPlayer.addListener(new ExoPlayer.Listener() {
            @Override
            public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
                // Toast.makeText(video.this, "This is a toast message", Toast.LENGTH_SHORT).show();
                surfaceView.setContentSize(videoSize.width, videoSize.height);
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
//                    loading.setVisibility(View.GONE);
                    //  Log.w("--------buffering--",   "Exo-err----------buffering----------"+mediaPlayer.getBufferedPosition());

                } else if (playbackState == Player.STATE_READY){
//                    loading.setVisibility(View.GONE);
                    // Log.w("--------STATE_READY",   "Exo-err----------STATE_READY----------"+mediaPlayer.getBufferedPosition());
                }
            }



            @Override
            public void onPlayerError(PlaybackException error) {
                // Log the error and current position when an error occurs

                Log.w("Exo-err-----------",   error.getMessage());
            }

        });

        // Start playback
//        mediaPlayer.prepare();
//        mediaPlayer.play();


        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerControlView.isShown()) {
//                    loading.setVisibility(View.GONE);
                    playerControlView.hide();
                } else {
//                    loading.setVisibility(View.GONE);
                    playerControlView.show();
                }
            }
        });


        // Set onTouchListener for the view
//        touch_r.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Touching the view, show a toast
//                        showToast("Touched!");
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        // Released the touch, show another toast
//                        showToast("Touch Released!");
//                        return true;
//                }
//                return false;
//            }
//        });




return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();

    }
}