package com.contacts.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Alexander Vashurin 07.02.2017
 */
public abstract class FileParser {
    public static String parseFile(String path) {
        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return "";
        }
        return new String(encoded);
    }
}
