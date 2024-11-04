package com.a.acs2;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.datasource.DefaultDataSourceFactory;
import androidx.media3.common.MediaItem;
import androidx.media3.ui.PlayerView;

import android.content.Context;


public class xor  extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xor);

        playerView = findViewById(R.id.vid);
        playEncryptedVideo(this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/india-f7d05.appspot.com/o/chat%2Fennnnh.mp4?alt=media&token=026a9146-7684-4011-9a28-0c3d7ce36d64"));
    }

    @OptIn(markerClass = UnstableApi.class)
    private void playEncryptedVideo(Context context, Uri videoUri) {
        player = new ExoPlayer.Builder(context).build();
        playerView.setPlayer(player);

        // Convert the integer key 1221 to a byte array
        byte[] xorKey = intToByteArray(1221);

        // DataSource.Factory with XOR decryption
        DataSource.Factory dataSourceFactory = new XorDecryptionDataSourceFactory(
                new DefaultDataSourceFactory(context),
                xorKey
        );

        // Media source creation
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoUri));

        // Prepare and start playback
        player.setMediaSource(mediaSource);
        player.prepare();
        player.play();
    }

    // Helper method to convert int to byte array
    private byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >> 24),
                (byte)(value >> 16),
                (byte)(value >> 8),
                (byte)(value)
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}