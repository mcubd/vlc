package com.a.acs2;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.FileDataSource;
import androidx.media3.datasource.HttpDataSource;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.CacheDataSource;

@UnstableApi
public class CacheOnlyDataSource implements DataSource.Factory {
    private final Cache cache;
//    private final DataSource.Factory upstreamDataSourceFactory;

    public CacheOnlyDataSource(Cache cache ) {
        this.cache = cache;
//        this.upstreamDataSourceFactory = upstreamDataSourceFactory;
    }

    @Override
    public DataSource createDataSource() {
        return new CacheDataSource(
                cache,
                new FileDataSource(),
                new FileDataSource(),
                null,
                CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
                null
        );
    }
}
