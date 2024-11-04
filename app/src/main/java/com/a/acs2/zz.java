
package com.a.acs2;
 //               Log.d(TAG, "------- ");

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.AesCipherDataSink;
import androidx.media3.datasource.AesCipherDataSource;
import androidx.media3.datasource.DataSink;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class zz extends AppCompatActivity {

    PlayerView playerView;
    ExoPlayer exoPlayer;



    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zz);

        playerView=findViewById(R.id.vide);
        exoPlayer=new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

         String aesKeyString = "1234567890abcdef";
        byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);








//        Uri fileUri = Uri.fromFile(file);
//        Uri fileUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid.mp4?alt=media&token=af591276-adcc-4b37-a258-f08581a56b15");
//        MediaItem mediaItem = new MediaItem.Builder().setUri(fileUri).build();
//                ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(aesDataSourceFactory)
//                .createMediaSource(mediaItem);

       // exoPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC);
        DataSource.Factory  cacheDataSourceFactory = DemoUtil.getCacheNHttpAesDataSourceFactory(this,aesKey);

        //DataSource.Factory aesDataSourceFactory = () -> new AesCipherDataSource(aesKey, cacheDataSourceFactory.createDataSource());

        ProgressiveMediaSource mediaSource0 = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_encryptVid.mp4?alt=media&token=af591276-adcc-4b37-a258-f08581a56b15"));


        exoPlayer.setMediaSource(mediaSource0);










      //  exoPlayer.setMediaSource(mediaSource);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.play();

    }



}