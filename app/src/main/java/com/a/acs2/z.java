
package com.a.acs2;
 //               Log.d(TAG, "------- ");

import android.graphics.drawable.GradientDrawable;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSink;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerView;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.FileDataSource;
import androidx.media3.datasource.HttpDataSource;

import androidx.media3.datasource.AesCipherDataSource;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.FileDataSource;

import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.AesCipherDataSink;


import androidx.media3.common.util.Util;
import java.io.InputStream;
import android.content.res.AssetManager;

import com.google.android.material.imageview.ShapeableImageView;


public class z extends AppCompatActivity {

    PlayerView playerView;
    ExoPlayer exoPlayer;



    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z);

        playerView=findViewById(R.id.vide);
        exoPlayer=new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

         String aesKeyString = "1234567890abcdef";
        byte[] aesKey = aesKeyString.getBytes(StandardCharsets.UTF_8);
        //-------------------------------------Top----------------------------------------------------
        //-----------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------





        AssetManager am = this.getAssets();
            File externalDir = new File(Environment.getExternalStorageDirectory(), "Download");
            File file = new File(externalDir, "1.html"); // Specify the file name

            try {


                // Fully read the input stream.
                InputStream inputStream = am.open("1.html");
                byte[] inputStreamBytes = Util.toByteArray(inputStream);
                inputStream.close();

                // Create a sink that will encrypt and write to file.
                AesCipherDataSink encryptingDataSink = new AesCipherDataSink(
                        Util.getUtf8Bytes(aesKeyString),
                        new DataSink() {
                            private FileOutputStream fileOutputStream;

                            @Override
                            public void open(@NonNull DataSpec dataSpec) throws IOException {
                                fileOutputStream = new FileOutputStream(file);
                            }

                            @Override
                            public void write(@NonNull byte[] buffer, int offset, int length) throws IOException {
                                fileOutputStream.write(buffer, offset, length);
                            }

                            @Override
                            public void close() throws IOException {
                                fileOutputStream.close();
                            }
                        });

                // Push the data through the sink, and close everything.
                encryptingDataSink.open(new DataSpec(Uri.fromFile(file)));
                encryptingDataSink.write(inputStreamBytes, 0, inputStreamBytes.length);
                encryptingDataSink.close();

                Toast.makeText(this, "File encrypted", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Encryption failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }




        //-----------------------------------------End------------------------------------------------
        //-----------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------



        DataSource.Factory  cacheDataSourceFactory = DemoUtil.getCacheAesDataSourceFactory(this,aesKey);

        ProgressiveMediaSource mediaSource0 = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fexo_aes_faststart_p.mp4?alt=media&token=9a1017b4-f8f4-4a98-9917-69b299a9e5fc"));


        exoPlayer.setMediaSource(mediaSource0);

                exoPlayer.setSeekParameters(SeekParameters.CLOSEST_SYNC);

        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.play();



        RelativeLayout relativeLayout = findViewById(R.id.re);

        // Create a GradientDrawable with rounded corners
        GradientDrawable roundedDrawable = new GradientDrawable();
        roundedDrawable.setColor(0xFFFFFFFF); // Background color (white)
        roundedDrawable.setCornerRadius(16 * getResources().getDisplayMetrics().density); // Corner radius in pixels

        relativeLayout.setBackground(roundedDrawable);





        ShapeableImageView imageView = findViewById(R.id.imageview0);
        imageView.setShapeAppearanceModel(
                imageView.getShapeAppearanceModel()
                        .toBuilder()
                        .setAllCornerSizes(22f) // Set radius in pixels or dp
                        .build()
        );





    }



}