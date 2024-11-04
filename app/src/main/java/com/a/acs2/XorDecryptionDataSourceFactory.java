package com.a.acs2;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.TransferListener;

import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.TransferListener;

@UnstableApi
public class XorDecryptionDataSourceFactory implements DataSource.Factory {

    private final DataSource.Factory upstreamFactory;
    private final byte[] xorKey;

    public XorDecryptionDataSourceFactory(DataSource.Factory upstreamFactory, byte[] xorKey) {
        this.upstreamFactory = upstreamFactory;
        this.xorKey = xorKey;
    }

    @Override
    public DataSource createDataSource() {
        return new XorDecryptionDataSource(upstreamFactory.createDataSource(), xorKey);
    }
}

