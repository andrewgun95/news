package com.example.news.service.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipHelper {

    private static final int BUFFER_SIZE = 4096; // in 4 bytes

    public static byte[] zip(Map<String, byte[]> fileData) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ZipOutputStream zipOut = new ZipOutputStream(output);
        for (Map.Entry<String, byte[]> fileEntry : fileData.entrySet()) {
            ZipEntry zipEntry = new ZipEntry(fileEntry.getKey());
            zipOut.putNextEntry(zipEntry);

            ByteArrayInputStream inputData = new ByteArrayInputStream(fileEntry.getValue());
            byte[] bytes = new byte[BUFFER_SIZE];
            int length = 0;
            while ((length = inputData.read(bytes)) != -1) {
                zipOut.write(bytes, 0, length);
            }
            inputData.close();
        }
        zipOut.close();

        return output.toByteArray();
    }

    public static Map<String, byte[]> unzip(byte[] zip) throws IOException {

        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(zip));

        Map<String, byte[]> fileData = new HashMap<>();

        ZipEntry zipEntry = zipIn.getNextEntry();
        while (zipEntry != null) {
            ByteArrayOutputStream outputData = new ByteArrayOutputStream();
            byte[] bytes = new byte[BUFFER_SIZE];
            int length = 0;
            while((length = zipIn.read(bytes)) != -1) {
                outputData.write(bytes, 0, length);
            }
            outputData.close();

            fileData.put(zipEntry.getName(), outputData.toByteArray());

            zipIn.closeEntry();
            zipEntry = zipIn.getNextEntry();
        }

        return fileData;
    }

}
