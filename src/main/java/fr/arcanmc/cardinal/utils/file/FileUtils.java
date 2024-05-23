package fr.arcanmc.cardinal.utils.file;

import java.io.*;

public class FileUtils {
    private static final String EMPTY_CONTENT = "";

    public static void createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            createParentDirsIfNotExists(file);
            file.createNewFile();
        }
    }

    private static void createParentDirsIfNotExists(File file) {
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
    }

    public static boolean deleteFile(File file) {
        return file.exists() && file.delete();
    }

    public static void writeToFile(File file, String text) throws IOException {
        createFileIfNotExists(file);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
            writer.flush();
        }
    }

    public static String readFromFile(File file) throws IOException {
        if (!file.exists()) {
            return EMPTY_CONTENT;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        }
    }
}