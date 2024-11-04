package com.a.acs2;

import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DataSource.Factory;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class Aesfactory implements Factory {
    private final Factory baseFactory;

    public Aesfactory(Factory baseFactory) {
        this.baseFactory = baseFactory;
    }

    @OptIn(markerClass = UnstableApi.class)
    @Override
    public DataSource createDataSource() {
        DataSource baseDataSource = baseFactory.createDataSource();
        try {
            return new AesCtrDataSource(baseDataSource);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
