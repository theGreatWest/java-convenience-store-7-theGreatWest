package store.util;

import store.constant.ExceptionMessage;
import store.constant.Files;
import store.constant.Number;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public static List<String> productsInformation() {
        return getInformation(Files.PRODUCTS.getFileNameWithExtension());
    }

    public static List<String> promotionsInformation() {
        return getInformation(Files.PROMOTIONS.getFileNameWithoutExtension());
    }

    public static List<String> getInformation(String fileName) {
        List<String> information;

        try {
            information = java.nio.file.Files.readAllLines(Paths.get(getFilePath(fileName)));
            skipFirstLine(information);
        } catch (IOException e) {
            throw new IllegalStateException(ExceptionMessage.FILE_READER_IO_EXCEPTION.getExceptionMessage());
        }

        return information;
    }

    private static String getFilePath(String fileName) {
        String filePath = System.getProperty("user.dir");
        filePath += Files.PRODUCTS.getFilePath(fileName);
        filePath += Files.PROMOTIONS.getFilePath(fileName);

        return filePath;
    }

    private static void skipFirstLine(List<String> information) {
        information.remove(Number.ZERO.getValue());
    }
}
