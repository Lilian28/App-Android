package com.example.user.proyecto_final.util;

import android.content.res.Resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileUtils {
    private FileUtils() {
    }

    public static String readRawFile(Resources resources, int rawId) {
        InputStream inputStream = resources.openRawResource(rawId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int in;
        try {
            in = inputStream.read();
            while (in != -1) {
                byteArrayOutputStream.write(in);
                in = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toString();
    }
}
