package store.core.util;

import store.core.constant.ExceptionMessage;
import store.core.constant.FilePath;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class FileReader {
    private static final int ZERO = 0;
    private static final String DELIMITER = "\\.";
    private static final String RUNNING_FOLDER_PATH = "user.dir";

    public static List<String> getInformation(String fileName) {
        List<String> information;
        try {
            information = Files.readAllLines(Paths.get(getFilePath(fileName)));
            skipFirstLine(information);
            removeEmptyValue(information);
        } catch (IllegalStateException | IOException e) {
            throw new IllegalStateException(ExceptionMessage.FILE_READER_IO_EXCEPTION.errorNotification());
        }

        return information;
    }

    private static void skipFirstLine(List<String> information) {
        information.removeFirst();
    }

    private static void removeEmptyValue(List<String> information) {
        information.removeIf(value -> value.strip().isBlank());
    }

    private static String getFilePath(String fileName) {
        String[] fileNameExtension = fileName.split(DELIMITER);
        String filePath = System.getProperty(RUNNING_FOLDER_PATH);

        if (verifyFileNameExist(fileNameExtension[ZERO])) {
            FilePath filePathConstant = getFilePathConstant(fileNameExtension[ZERO]);
            return filePath + filePathConstant.getFilePath();
        }

        throw new IllegalStateException();
    }

    private static boolean verifyFileNameExist(String fileName) {
        for (FilePath enumConstant : FilePath.values()) {
            if (enumConstant.getFileNameWithoutExtension().equalsIgnoreCase(fileName)) {
                return true;
            }
        }
        return false;
    }

    private static FilePath getFilePathConstant(String fileName) {
        return FilePath.valueOf(fileName.toUpperCase());
    }
}
