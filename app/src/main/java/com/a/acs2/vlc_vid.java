package com.a.acs2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.databinding.adapters.LinearLayoutBindingAdapter;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.IVLCVout;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.os.Handler;
import android.os.Looper;
import android.app.AlertDialog;

import java.util.List;

public class vlc_vid extends AppCompatActivity {
    private static final String TAG = "VlcPlayer";
    private static final float MIN_SCALE = 1.0f;
    private static final float MAX_SCALE = 8.0f;

    private LibVLC libVLC;
    private MediaPlayer mediaPlayer;
    private TextureView textureView;
    private Uri videoUri;

    // Zoom/pan variables
    private ScaleGestureDetector mScaleDetector;
    private Matrix mMatrix = new Matrix();
    private float mScaleFactor = 1.0f;
    private float mFocusX, mFocusY;
    private float mLastTouchX, mLastTouchY;
    private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;

    private int mVideoWidth, mVideoHeight;
    private String videoPath;
    private String orii;
    private SeekBar seekBar;
    private    View layoutProgress;
    private TextView tvCurrentTime, tvTotalTime;

    private Handler handler = new Handler();
    private Runnable updateRunnable;


    private GestureDetectorCompat mGestureDetector;
    private boolean mDoubleTapHandled = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vlc_vid);

        // Initialize UI components
        textureView = findViewById(R.id.textureView);
        ImageButton btnPlay = findViewById(R.id.btnPlay);
        ImageButton btnPause = findViewById(R.id.btnPause);
        ImageButton sub = findViewById(R.id.sub);
      layoutProgress = findViewById(R.id.layoutProgress);

        seekBar = findViewById(R.id.seekBar);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);



        // Get video URI
          videoPath = getIntent().getStringExtra("videoPath");
        if (videoPath == null) {
            Toast.makeText(this, "No video path provided", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mVideoWidth = displayMetrics.widthPixels;
        mVideoHeight = displayMetrics.heightPixels;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);

        String heightStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String widthStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);

        int height = Integer.parseInt(heightStr);
        int width = Integer.parseInt(widthStr);

        if (width > height) {
            orii="landscape";

        } else if (height > width) {
            orii="potrait";
        } else {
            orii="potrait";
        }



        // Handle URI creation
        videoUri = videoPath.startsWith("smb://") ?
                Uri.parse(videoPath) : Uri.fromFile(new File(videoPath));

        // Initialize VLC
        initVLC();

        // Initialize gesture detectors
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());
        // Add this right after your mScaleDetector initialization
        mGestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                int viewWidth = textureView.getWidth();
                float touchX = e.getX();


                if (touchX < viewWidth / 2) {


                    long newTime=mediaPlayer.getTime()-10000;
                            newTime=Math.max(0,Math.min(newTime,mediaPlayer.getLength()));
                                    mediaPlayer.setTime(newTime);
                } else {
                    long newTime=mediaPlayer.getTime()+10000;
                    newTime=Math.max(0,Math.min(newTime,mediaPlayer.getLength()));
                    mediaPlayer.setTime(newTime);
                }



                mDoubleTapHandled = true;
                return true;
            }
        });
        setupTouchListeners();

        // Button listeners
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.play();
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.GONE);
            }
        });




        // Handler & progress updater
        handler = new Handler(Looper.getMainLooper());
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    long current = mediaPlayer.getTime();
                    long total = mediaPlayer.getLength();


                    seekBar.setMax((int) total);
                    seekBar.setProgress((int) current);

                    tvCurrentTime.setText(formatTime(current));
                    tvTotalTime.setText(formatTime(total));
                }
                handler.postDelayed(this, 500);
            }
        };


        handler.post(updateRunnable);



        // SeekBar interaction
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.setTime(progress);
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });




        sub.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            showTrackSelectionDialog();
            }
        });



    }


    private void showTrackSelectionDialog() {
        if (mediaPlayer == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Track");

        // Get available tracks
        MediaPlayer.TrackDescription[] subtitles = mediaPlayer.getSpuTracks();
        MediaPlayer.TrackDescription[] audioTracks = mediaPlayer.getAudioTracks();

        List<CharSequence> items = new ArrayList<>();
        final List<Integer> trackIds = new ArrayList<>();
        final List<Boolean> isSubtitleList = new ArrayList<>();

        // Add subtitles section
        if (subtitles != null && subtitles.length > 0) {
            items.add("-- Subtitles --");
            trackIds.add(-1);
            isSubtitleList.add(false);

            for (MediaPlayer.TrackDescription track : subtitles) {
                items.add("Sub: " + track.name);
                trackIds.add(track.id);
                isSubtitleList.add(true);
            }
        }

        // Add audio tracks section
        if (audioTracks != null && audioTracks.length > 0) {
            items.add("-- Audio Tracks --");
            trackIds.add(-1);
            isSubtitleList.add(false);

            for (MediaPlayer.TrackDescription track : audioTracks) {
                items.add("Audio: " + track.name);
                trackIds.add(track.id);
                isSubtitleList.add(false);
            }
        }

        // Convert to array for dialog
        final CharSequence[] itemsArray = items.toArray(new CharSequence[0]);

        builder.setItems(itemsArray, (dialog, which) -> {
            int trackId = trackIds.get(which);
            boolean isSubtitle = isSubtitleList.get(which);

            if (trackId >= 0) {
                if (isSubtitle) {
                    mediaPlayer.setSpuTrack(trackId);
                } else {
                    mediaPlayer.setAudioTrack(trackId);
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private String formatTime(long timeMs) {
        int totalSeconds = (int) (timeMs / 1000);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private void initVLC() {
        try {
            ArrayList<String> options = new ArrayList<>();
            options.add("--avcodec-codec=h264");
            options.add("--file-caching=500");
            libVLC = new LibVLC(this, options);
            mediaPlayer = new MediaPlayer(libVLC);


            // Add event listener to get video dimensions
            mediaPlayer.setEventListener(event -> {
                if (event.type == MediaPlayer.Event.LengthChanged) {
//                    mVideoWidth = event.;
//                    mVideoHeight = event.getArgs().getInt("height");
//                    runOnUiThread(this::adjustVideoLayout);
                }  else    if (event.type == MediaPlayer.Event.Vout) {



               //     mediaPlayer.setScale(0);
    //mediaPlayer.setVideoScale(MediaPlayer.ScaleType.SURFACE_16_9);



                  //  "yes".equalsIgnoreCase(hasVideo)
                    if(orii=="landscape") {

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

                    }else {

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);



                    }





//                    if (videoWidth > 0 && videoHeight > 0) {
//                        runOnUiThread(() -> {
//                            int fixedHeight = dpToPx(200); // Match your layout height
//                            float aspectRatio = (float) videoWidth / videoHeight;
//                            int newWidth = (int) (fixedHeight * aspectRatio);
//
//                            ViewGroup.LayoutParams layoutParams = textureView.getLayoutParams();
//                            layoutParams.width = newWidth;
//                            layoutParams.height = fixedHeight;
//                            textureView.setLayoutParams(layoutParams);
//                        });
//                    }



                }



            });

            // Attach to TextureView
            mediaPlayer.getVLCVout().setVideoView(textureView);


            mediaPlayer.getVLCVout().setWindowSize(mVideoWidth,mVideoHeight);



            mediaPlayer.getVLCVout().attachViews();

            playVideo();
        } catch (Exception e) {
            Toast.makeText(this, "VLC initialization failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "VLC init error", e);
            finish();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupTouchListeners() {
        textureView.setOnTouchListener((v, event) -> {
            // Pass events to scale detector first
            mScaleDetector.onTouchEvent(event);


            final int action = event.getActionMasked();



            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    final int pointerIndex = event.getActionIndex();
                    final float x = event.getX(pointerIndex);
                    final float y = event.getY(pointerIndex);

                    // Remember where we started for dragging
                    mLastTouchX = x;
                    mLastTouchY = y;
                    mActivePointerId = event.getPointerId(pointerIndex);
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    // Only move if we're not in the middle of a scale gesture
                    if (!mScaleDetector.isInProgress()) {
                        final int pointerIndex = event.findPointerIndex(mActivePointerId);
                        if (pointerIndex != -1) {
                            final float x = event.getX(pointerIndex);
                            final float y = event.getY(pointerIndex);

                            // Calculate pan distance
                            final float dx = x - mLastTouchX;
                            final float dy = y - mLastTouchY;

                            // Apply translation to matrix
                            mMatrix.postTranslate(dx, dy);
                            textureView.setTransform(mMatrix);

                            // Remember this touch position
                            mLastTouchX = x;
                            mLastTouchY = y;

                            mMatrix.postTranslate(dx, dy);
                            textureView.setTransform(mMatrix);
                            clampTranslation();
                        }

                    }
                    break;
                }

                case MotionEvent.ACTION_UP: {
                // Handle tap if all conditions met
                if (  !mScaleDetector.isInProgress() && event.getPointerCount() == 1) {
                    if (layoutProgress.getVisibility()==View.GONE ){
                        layoutProgress.setVisibility(View.VISIBLE);
                    }else {
                        layoutProgress.setVisibility(View.GONE);
                    }
                }
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }
                case MotionEvent.ACTION_CANCEL: {
                    mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                    break;
                }

                case MotionEvent.ACTION_POINTER_UP: {
                    final int pointerIndex = event.getActionIndex();
                    final int pointerId = event.getPointerId(pointerIndex);
                    if (pointerId == mActivePointerId) {
                        // This was our active pointer going up. Choose a new active pointer.
                        final int newPointerIndex = (pointerIndex == 0) ? 1 : 0;
                        mLastTouchX = event.getX(newPointerIndex);
                        mLastTouchY = event.getY(newPointerIndex);
                        mActivePointerId = event.getPointerId(newPointerIndex);

                    }
                    break;
                }
            }
            return true;
        });
    }


    private void clampTranslation() {
        if (textureView.getWidth() == 0 || textureView.getHeight() == 0) return;

        float[] values = new float[9];
        mMatrix.getValues(values);
        float scale = values[Matrix.MSCALE_X];
        float tx = values[Matrix.MTRANS_X];
        float ty = values[Matrix.MTRANS_Y];

        int viewWidth = textureView.getWidth();
        int viewHeight = textureView.getHeight();

        // Calculate scaled dimensions
        float scaledWidth = viewWidth * scale;
        float scaledHeight = viewHeight * scale;

        // Calculate allowed translation ranges
        float minX = viewWidth - scaledWidth;
        float maxX = 0;
        float minY = viewHeight - scaledHeight;
        float maxY = 0;

        // Clamp translation values
        float clampedTx = Math.max(minX, Math.min(tx, maxX));
        float clampedTy = Math.max(minY, Math.min(ty, maxY));

        // Apply corrections if needed
        if (clampedTx != tx || clampedTy != ty) {
            values[Matrix.MTRANS_X] = clampedTx;
            values[Matrix.MTRANS_Y] = clampedTy;
            mMatrix.setValues(values);
            textureView.setTransform(mMatrix);
        }
    }
    private void applyZoom() {
        // Reset matrix to apply new transformations
        mMatrix.reset();
        // Apply scaling
        mMatrix.postScale(mScaleFactor, mScaleFactor, mFocusX, mFocusY);
        // Apply existing translation
        float[] values = new float[9];
        mMatrix.getValues(values);
        textureView.setTransform(mMatrix);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mFocusX = detector.getFocusX();
            mFocusY = detector.getFocusY();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // Calculate potential new scale
            float newScale = mScaleFactor * detector.getScaleFactor();

            // Constrain scale between 1.0f (original size) and 8.0f (max zoom)
            newScale = Math.max(MIN_SCALE, Math.min(newScale, MAX_SCALE));

            // Only apply scaling if it changes the current scale
            if (newScale != mScaleFactor) {
                // Calculate relative scale factor
                float scaleFactor = newScale / mScaleFactor;

                // Update scale factor
                mScaleFactor = newScale;

                // Apply scaling
                mMatrix.postScale(scaleFactor, scaleFactor, mFocusX, mFocusY);
                textureView.setTransform(mMatrix);

                // Prevent overscrolling
                clampTranslation();
            }
            return true;
        }
    }

    // MODIFIED: Include clamping in reset
    private void resetZoom() {
        mScaleFactor = 1.0f;
        mMatrix.reset();
        textureView.setTransform(mMatrix);
        clampTranslation(); // Ensure proper initial position
    }
    private void playVideo() {
        if (mediaPlayer == null) return;

        try {
            if (mediaPlayer.getMedia() != null) {
                mediaPlayer.stop();
            }

            Media media = new Media(libVLC, videoUri);
            mediaPlayer.setMedia(media);
            media.release();
            mediaPlayer.play();

            // Reset zoom when new video starts
            resetZoom();
        } catch (Exception e) {
            Toast.makeText(this, "Playback failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Playback error", e);
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Let gesture detector process double-taps first
        if (mGestureDetector.onTouchEvent(ev)) {
            return true;
        }

        // Reset double-tap flag on new touch sequence
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mDoubleTapHandled = false;
        }

        // Block single-tap action if double-tap occurred
        if (ev.getActionMasked() == MotionEvent.ACTION_UP && mDoubleTapHandled) {
            return true;
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.play();
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.getVLCVout().detachViews();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (libVLC != null) {
            libVLC.release();
            libVLC = null;
        }
    }
}