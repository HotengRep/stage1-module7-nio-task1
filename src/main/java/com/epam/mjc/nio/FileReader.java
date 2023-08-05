package com.epam.mjc.nio;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {

        StringBuilder content = new StringBuilder();

        try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = accessFile.getChannel();) {

            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();

            for (int i = 0; i < fileSize; i++ )
            {
                content.append((char)buffer.get(i));
            }
            buffer.clear();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] keyValueString = getParseString(content.toString());

        String name = getValueByKey(keyValueString, "Name");
        String email = getValueByKey(keyValueString, "Email");
        Long phone = Long.parseLong(getValueByKey(keyValueString, "Phone"));
        Integer age = Integer.parseInt(getValueByKey(keyValueString, "Age"));


        return new Profile(name,age,email,phone);
    }


    private String[] getParseString(String inputString) {
        if (inputString != null) {
            return inputString.split(System.lineSeparator());
        }
        return new String[]{"empty"};

    }

    private String getValueByKey(String[] inputString, String key) {
        if (inputString != null && key != null) {
            for (int i = 0; i < inputString.length; i++) {
                if (inputString[i].contains(key)) {
                    return inputString[i].substring(key.length() + 2);
                }
            }
            return key;
        }
        return "error";


    }
}
