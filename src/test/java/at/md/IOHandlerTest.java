package at.md;

import at.md.Util.IOHandler;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IOHandlerTest {

    @Test
    void testReadFile() {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("testfile", ".txt");
            Files.write(tempFile, "Line 1\nLine 2\nLine 3".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary file for testing.", e);

        }

        // Test reading the file
        try {
            ArrayList<String> lines = IOHandler.readFile(tempFile.toString());
            assertEquals(3, lines.size());
            assertEquals("Line 1", lines.get(0));
            assertEquals("Line 2", lines.get(1));
            assertEquals("Line 3", lines.get(2));
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file.", e);
        }

        // Test reading a non-existent file
        assertThrows(IOException.class, () -> IOHandler.readFile("nonexistentfile.txt"));

        try {
            Files.deleteIfExists(tempFile);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting the temporary file.", e);
        }
    }

    @Test
    void testReadFileWithAbsolutePath() {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("testfile_absolute", ".txt");
            Files.write(tempFile, "Absolute Path Test".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary file for testing.", e);
        }

        // Test reading the file with an absolute path
        try {
            ArrayList<String> lines = IOHandler.readFile(tempFile.toAbsolutePath().toString());
            assertEquals(1, lines.size());
            assertEquals("Absolute Path Test", lines.get(0));
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file with an absolute path.", e);
        }

        try {
            Files.deleteIfExists(tempFile);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting the temporary file.", e);
        }
    }

    @Test
    void testReadFileWithRelativePath() {

        Path tempDirectory;
        try {
            tempDirectory = Files.createTempDirectory("testdir_relative");
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary directory for testing.", e);
        }

        // Create a temporary file within the directory
        Path tempFile = tempDirectory.resolve("testfile_relative.txt");
        try {
            Files.write(tempFile, "Relative Path Test".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error creating temporary file for testing.", e);
        }

        // Test reading the file with a relative path
        try {
            ArrayList<String> lines = IOHandler.readFile(tempFile.toString());
            assertEquals(1, lines.size());
            assertEquals("Relative Path Test", lines.get(0));
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file with a relative path.", e);
        }

// Clean up: delete the temporary directory and its contents
        try (Stream<Path> pathStream = Files.walk(tempDirectory)) {
            pathStream
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Error deleting file/directory: " + path, e);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException("Error walking through the temporary directory.", e);
        }
    }

}

