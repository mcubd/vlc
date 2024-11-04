package com.a.acs2;


import static com.a.acs2.DemoUtil.DOWNLOAD_NOTIFICATION_CHANNEL_ID;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.NotificationUtil;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.offline.Download;
import androidx.media3.exoplayer.offline.DownloadManager;
import androidx.media3.exoplayer.offline.DownloadNotificationHelper;
import androidx.media3.exoplayer.offline.DownloadService;
import androidx.media3.exoplayer.scheduler.PlatformScheduler;
import androidx.media3.exoplayer.scheduler.Scheduler;
import android.util.Log;



import java.util.List;


/**
 * A service for downloading media.
 */
@UnstableApi
public class OfflineVideoDownloadService extends DownloadService {

    private static final int JOB_ID = 1;
    private static final int FOREGROUND_NOTIFICATION_ID = 1;

    public OfflineVideoDownloadService() {
        super(
                FOREGROUND_NOTIFICATION_ID,
                DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
                DOWNLOAD_NOTIFICATION_CHANNEL_ID,
                R.string.exo_download_notification_channel_name,
                /* channelDescriptionResourceId= */ 0);
    }

    @Override
    protected DownloadManager getDownloadManager() {
        // This will only happen once, because getDownloadManager is guaranteed to be called only once
        // in the life cycle of the process.
        DownloadManager downloadManager = DemoUtil.getDownloadManager(/* context= */ this);
        DownloadNotificationHelper downloadNotificationHelper =
                DemoUtil.getDownloadNotificationHelper(/* context= */ this);
        downloadManager.addListener(
                new TerminalStateNotificationHelper(
                        this, downloadNotificationHelper, FOREGROUND_NOTIFICATION_ID + 1));
        return downloadManager;
    }

    @Override
    protected Scheduler getScheduler() {
        return Util.SDK_INT >= 21 ? new PlatformScheduler(this, JOB_ID) : null;
    }

    @Override
    protected Notification getForegroundNotification(List<Download> downloads, int notMetRequirements) {

        return DemoUtil.getDownloadNotificationHelper(this)
                .buildProgressNotification(
                        this,
                        R.drawable.acs,


                        /* contentIntent= */ null,
                        /* message= */ null,
                        downloads,
                        notMetRequirements
                );
    }


    /**
     * Creates and displays notifications for downloads when they complete or fail.
     *
     * <p>This helper will outlive the lifespan of a single instance of {@link OfflineVideoDownloadService}.
     * It is static to avoid leaking the first {@link OfflineVideoDownloadService} instance.
     */
    private static final class TerminalStateNotificationHelper implements DownloadManager.Listener {

        private final Context context;
        private final DownloadNotificationHelper notificationHelper;

        private int nextNotificationId;

        public TerminalStateNotificationHelper(
                Context context, DownloadNotificationHelper notificationHelper, int firstNotificationId) {
            this.context = context.getApplicationContext();
            this.notificationHelper = notificationHelper;
            nextNotificationId = firstNotificationId;
        }

        @Override
        public void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception finalException) {
            DownloadManager.Listener.super.onDownloadChanged(downloadManager, download, finalException);
            Notification notification;

//              if (download.state == Download.STATE_DOWNLOADING) {
//                float progress = download.getPercentDownloaded();
//                Log.d("down-state0", "downloading");
//                if (progress != C.PERCENTAGE_UNSET) {
//                    Log.d("down-state", String.valueOf(progress));
//
//                }
//
//                  notification =
//                          notificationHelper.buildDownloadFailedNotification(
//                                  context,
//                                  R.drawable.acs,
//                                  /* contentIntent= */ null,
//                                  Util.fromUtf8Bytes(download.request.data));
//
//            }
//           else
           if (download.state == Download.STATE_COMPLETED) {
                notification =
                        notificationHelper.buildDownloadCompletedNotification(
                                context,
                                R.drawable.acs,
                                /* contentIntent= */ null,
                                Util.fromUtf8Bytes(download.request.data));

                MediaItem s =download.request.toMediaItem();
                String uriString = s.toString();
                Log.d("TAG", "Uri: " + uriString);
                DemoUtil.setLastDownloadedMediaItem(s);

            } else if (download.state == Download.STATE_FAILED) {
                notification =
                        notificationHelper.buildDownloadFailedNotification(
                                context,
                                R.drawable.acs,
                                /* contentIntent= */ null,
                                Util.fromUtf8Bytes(download.request.data));
            }
            else {
                MediaItem s =download.request.toMediaItem();
                String uriString = s.toString();
                Log.d("TAG", "mediaitem-added--: " + uriString);
                DemoUtil.setLastDownloadedMediaItem(s);
                return;
            }
            Log.d("TAG", "downloading bro--: "  );

            NotificationUtil.setNotification(context, nextNotificationId++, notification);
        }
    }
}
