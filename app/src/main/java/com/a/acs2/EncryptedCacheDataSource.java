package com.a.acs2;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.cache.CacheDataSource;
import java.io.IOException;
import java.io.InputStream;

/*
@UnstableApi
public class EncryptedCacheDataSource extends CacheDataSource {

    public static class Factory extends CacheDataSource.Factory {

        @Override
        public CacheDataSource createDataSource() {
            return new EncryptedCacheDataSource(
                    getCache(),
                    getUpstreamDataSourceFactory().createDataSource(),
                    getCacheWriteDataSinkFactory(),
                    getFlags(),
                    getEventListener(),
                    getKey());
        }
    }

    private EncryptedCacheDataSource(
            Cache cache,
            DataSource upstream,
            DataSink.Factory cacheWriteDataSinkFactory,
            int flags,
            EventListener eventListener,
            byte[] key) {
        super(cache, upstream, cacheWriteDataSinkFactory, flags, eventListener, key);
    }

    @Override
    protected InputStream getInputStreamForCacheWrite() throws IOException {
        InputStream inputStream = super.getInputStreamForCacheWrite();
        return DemoUtil.encryptInputStream(inputStream);
    }
}
*/
