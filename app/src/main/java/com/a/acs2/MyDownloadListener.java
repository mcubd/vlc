package com.a.acs2;

import android.util.Log;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadManager;

public class MyDownloadListener implements DownloadManager.Listener {
//
//    @UnstableApi
//    @Override
//    public void onDownloadChanged(DownloadManager downloadManager, Download download) {
//        // Handle changes in download state or progress here
//        if (download.state == Download.STATE_DOWNLOADING) {
//            long progress = (download.bytesDownloaded * 100) / download.expectedLength;
//            Log.d("DownloadProgress", "Download ID: " + download.id + " Progress: " + progress + "%");
//        }
//    }
//
//    @UnstableApi
//    @Override
//    public void onDownloadChanged(DownloadManager downloadManager, Download download) {
//        // Handle other download state changes if needed
//        if (download.state == Download.STATE_COMPLETED) {
//            Log.d("DownloadProgress", "Download ID: " + download.id + " completed.");
//        } else if (download.state == Download.STATE_FAILED) {
//            Log.d("DownloadProgress", "Download ID: " + download.id + " failed.");
//        }
//    }
//
//    @UnstableApi
//    @Override
//    public void onDownloadRemoved(DownloadManager downloadManager, Download download) {
//        // Handle download removal if needed
//        Log.d("DownloadProgress", "Download ID: " + download.id + " removed.");
//    }
//
//    @Override
//    public void onDownloadsPausedChanged(DownloadManager downloadManager, Download download) {
//        // Handle download removal if needed
//        Log.d("DownloadProgress", "Download ID: " + download.id + " removed.");
//    }
//


}
