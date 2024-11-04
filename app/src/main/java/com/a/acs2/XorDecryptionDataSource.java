package com.a.acs2;

import android.net.Uri;

import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.TransferListener;

import java.io.IOException;

@UnstableApi
public class XorDecryptionDataSource implements DataSource {

    private final DataSource upstream;
    private final byte[] xorKey;
    private int keyIndex = 0;

    public XorDecryptionDataSource(DataSource upstream, byte[] xorKey) {
        this.upstream = upstream;
        this.xorKey = xorKey;
    }

    @Override
    public long open(DataSpec dataSpec) throws IOException {
        return upstream.open(dataSpec);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        int bytesRead = upstream.read(buffer, offset, length);
        if (bytesRead != -1) {
            for (int i = offset; i < offset + bytesRead; i++) {
                buffer[i] = (byte) (buffer[i] ^ xorKey[keyIndex % xorKey.length]);
                keyIndex++;
            }
        }
        return bytesRead;
    }

    @Override
    public Uri getUri() {
        return upstream.getUri();
    }

    @Override
    public void close() throws IOException {
        upstream.close();
    }

    @Override
    public void addTransferListener(TransferListener transferListener) {
        upstream.addTransferListener(transferListener);
    }
}
