package at.md.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;


public class IOHandler {

    public static ArrayList<String> readFile(String fileName) throws IOException {
        // Convert the file name to a Path
        Path filePath = Paths.get(fileName);

        // If the path is not absolute, make it relative to the current working directory
        if (!filePath.isAbsolute()) {
            // Get the current working directory
            String currentDirectory = System.getProperty("user.dir");

            // Resolve the relative path against the current working directory
            filePath = Paths.get(currentDirectory).resolve(filePath);
        }

        // Open the file using the resolved path
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            ArrayList<String> lines = new ArrayList<>();
            String newLine;
            while ((newLine = br.readLine()) != null) {
                lines.add(newLine.trim());
            }
            return lines;
        }
    }
}

