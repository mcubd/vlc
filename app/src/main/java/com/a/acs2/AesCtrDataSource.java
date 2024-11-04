package com.a.acs2;

import android.net.Uri;
import androidx.annotation.NonNull;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import androidx.media3.common.C;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.TransferListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;

@UnstableApi
public class AesCtrDataSource implements DataSource {

    private final DataSource baseDataSource;
    private final Cipher cipher;
    private CipherInputStream cipherInputStream;
    private TransferListener transferListener; // Store the transfer listener

    // Provide the AES key and IV (initialization vector)


    private final byte[] encryptionKey = "123456789abcdefg".getBytes("UTF-8"); // Replace with your key
    private final byte[] iv = new byte[] {-13, -49, -46, -104, 37, 102, -10, -121, -74, -39, 71, -27, -29, -76, -42, 120};

    public AesCtrDataSource(DataSource baseDataSource) throws GeneralSecurityException, UnsupportedEncodingException {
        this.baseDataSource = baseDataSource;
        cipher = Cipher.getInstance("AES/CTR/NoPadding");
        Key key = new SecretKeySpec(encryptionKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
    }

    @Override
    public long open(DataSpec dataSpec) throws IOException {
        // Open the base data source and get the data length
        long dataLength = baseDataSource.open(dataSpec);

        // Create a buffer to read the data into
        byte[] buffer = new byte[1024];
        int bytesRead;

        // Create a temporary array to hold the data for decryption
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Read the data from the base data source
        while ((bytesRead = baseDataSource.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Convert the output stream to an input stream for decryption
        cipherInputStream = new CipherInputStream(new ByteArrayInputStream(outputStream.toByteArray()), cipher);

        return dataLength; // Return the length of the data
    }

    @Override
    public int read(@NonNull byte[] buffer, int offset, int readLength) throws IOException {
        if (cipherInputStream == null) {
            throw new IOException("CipherInputStream not initialized.");
        }

        int bytesRead = cipherInputStream.read(buffer, offset, readLength);
        if (bytesRead == -1) {
            return C.RESULT_END_OF_INPUT; // Properly signal end of input
        } else {
            return bytesRead;
        }
    }

    @Override
    public Uri getUri() {
        return baseDataSource.getUri();
    }

    @Override
    public void close() throws IOException {
        if (cipherInputStream != null) {
            cipherInputStream.close();
            cipherInputStream = null;
        }
        baseDataSource.close();
    }

    @Override
    public void addTransferListener(@NonNull TransferListener listener) {
        this.transferListener = listener; // Store the transfer listener for notifications
        baseDataSource.addTransferListener(listener); // Forward the listener to the base data source
    }
}
