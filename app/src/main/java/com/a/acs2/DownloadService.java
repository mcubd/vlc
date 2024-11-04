package com.a.acs2;


import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.database.DatabaseProvider;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.datasource.cache.NoOpCacheEvictor;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.cache.SimpleCache;
import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadManager;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@UnstableApi
public class DownloadService  {
    private static DatabaseProvider databaseProvider;

    private static Cache downloadCache;
    private static File downloadDirectory;
    private static DownloadManager downloadManager;
    private static DataSource.Factory httpDataSourceFactory;
    private Map<String, Download> downloads = new HashMap<>();








    public static synchronized DownloadManager getDownloadManager(Context context) {
        ensureDownloadManagerInitialized(context);
        return downloadManager;
    };


    private static synchronized File getDownloadDirectory(Context context) {
        if (downloadDirectory == null) {
            downloadDirectory = context.getExternalFilesDir(/* type= */ null);
            if (downloadDirectory == null) {
                downloadDirectory = context.getFilesDir();
            }
        }
        return downloadDirectory;
    }



    @UnstableApi
    private static synchronized DatabaseProvider getDatabaseProvider(Context context) {
        if (databaseProvider == null) {
            databaseProvider = new StandaloneDatabaseProvider(context);
        }
        return databaseProvider;
    }


// A download cache should not evict media, so should use a NoopCacheEvictor.
private static synchronized Cache getDownloadCache(Context context) {
    if (downloadCache == null) {
        File downloadContentDirectory =
                new File(getDownloadDirectory(context), "downloads");
        downloadCache =
                new SimpleCache(
                        downloadContentDirectory, new NoOpCacheEvictor(), getDatabaseProvider(context));
    }
    return downloadCache;
}

// Create a factory for reading the data from the network.
public static synchronized DataSource.Factory getHttpDataSourceFactory(Context context) {

        if (httpDataSourceFactory == null) {
            // We don't want to use Cronet, or we failed to instantiate a CronetEngine.
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
            CookieHandler.setDefault(cookieManager);
            httpDataSourceFactory = new DefaultHttpDataSource.Factory();
        }

    return httpDataSourceFactory;
}

// Choose an executor for downloading data. Using Runnable::run will cause each download task to
// download data on its own thread. Passing an executor that uses multiple threads will speed up
// download tasks that can be split into smaller parts for parallel execution. Applications that
// already have an executor for background downloads may wish to reuse their existing executor.
        Executor downloadExecutor = Runnable::run;

// Create the download manager.



private static synchronized void ensureDownloadManagerInitialized(Context context) {
    if (downloadManager == null) {
        Toast.makeText(context.getApplicationContext(), "555", Toast.LENGTH_SHORT).show();

        downloadManager =
                new DownloadManager(
                        context,
                        getDatabaseProvider(context),
                        getDownloadCache(context),
                        getHttpDataSourceFactory(context),
                        Executors.newFixedThreadPool(/* nThreads= */ 6));


    }
}
    private static CacheDataSource.Factory buildReadOnlyCacheDataSource(
            DataSource.Factory upstreamFactory, Cache cache) {
        return new CacheDataSource.Factory()
                .setCache(cache)
                .setUpstreamDataSourceFactory(upstreamFactory)
                .setCacheWriteDataSinkFactory(null)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
    }






    private class DownloadManagerListener implements DownloadManager.Listener {


        @Override
        public void onDownloadChanged(
                DownloadManager downloadManager, Download download, @Nullable Exception finalException) {
            String downloadUriString = download.request.uri.toString();
            downloads.put(downloadUriString, download);

            Log.d(ContentValues.TAG, "download changing in downloadservice class");


        }

/*        @Override
        public void onDownloadChanged(DownloadManager downloadManager, Download download) {

            Log.d(ContentValues.TAG, "Download state changed: " + download.state);

        }*/

        @Override
        public void onIdle(DownloadManager downloadManager) {
            // This method will be called when all downloads are finished
            Log.d(ContentValues.TAG, "All downloads completed");
        }

    }


}
