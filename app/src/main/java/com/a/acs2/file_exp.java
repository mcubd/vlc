package com.a.acs2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class file_exp extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final String TAG = "FileExplorer";

    private RecyclerView recyclerView;
    private TextView tvCurrentPath;
    private File currentDir;
    private final Stack<File> dirStack = new Stack<>();
    private adapter_files adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_exp);

        recyclerView = findViewById(R.id.recyclerView);
        tvCurrentPath = findViewById(R.id.tvCurrentPath);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        checkStoragePermission();
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            startFileExplorer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startFileExplorer();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startFileExplorer() {
        currentDir = Environment.getExternalStorageDirectory();
        loadDirectory(currentDir);
    }

    private void loadDirectory(File directory) {
        tvCurrentPath.setText(directory.getAbsolutePath());
        currentDir = directory;

        List<model_files> fileItems = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                try {
                    // This will throw an exception for files with invalid encoding
                    file.getCanonicalPath();

                    fileItems.add(new model_files(
                            file.getName(),
                            file.getAbsolutePath(),
                            file.isDirectory(),
                            file.isDirectory() ? 0 : file.length()
                    ));
                } catch (Exception e) {
                    // Handle files with problematic names
                    Log.w(TAG, "Skipping file with invalid encoding: " + file.getAbsolutePath());
                }
            }
        }

        Collections.sort(fileItems, (item1, item2) -> {
            if (item1.isDirectory() && !item2.isDirectory()) return -1;
            if (!item1.isDirectory() && item2.isDirectory()) return 1;
            return item1.getName().compareToIgnoreCase(item2.getName());
        });

        adapter = new adapter_files(fileItems, item -> {
            if (item.isDirectory()) {
                dirStack.push(currentDir);
                loadDirectory(new File(item.getPath()));
            } else {
                handleFileSelection(item);
            }
        });
        recyclerView.setAdapter(adapter);
    }




    private boolean isVideoFile(String filePath) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            String hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);


            return "yes".equalsIgnoreCase(hasVideo);
        } catch (Exception e) {
            e.printStackTrace(); // or log
            return false;
        } finally {
            retriever.release();
        }
    }


    @OptIn(markerClass = UnstableApi.class)
    private void handleFileSelection(model_files file) throws IOException {
        // For Android 5 compatibility, we'll use the file path directly
     //   Toast.makeText(this, "Selected: " + file.getName(), Toast.LENGTH_SHORT).show();

        // Example media playback would go here
        // MediaPlayer mediaPlayer = new MediaPlayer();

        // mediaPlayer.setDataSource(file.getPath());
        // mediaPlayer.prepare();

        // mediaPlayer.start();
        Uri fileUri = Uri.fromFile(new File(file.getPath()));

        if(isVideoFile( file.getPath())){
            String filePath = file.getPath();
            Intent intent = new Intent(this, vlc_vid.class);
            intent.putExtra("videoPath", filePath );
            startActivity(intent);
        }else{
        Toast.makeText(this, "Not a video: " + fileUri, Toast.LENGTH_SHORT).show();



   }
    }

    @Override
    public void onBackPressed() {
        if (!dirStack.isEmpty()) {
            loadDirectory(dirStack.pop());
        } else {
            super.onBackPressed();
        }
    }
}