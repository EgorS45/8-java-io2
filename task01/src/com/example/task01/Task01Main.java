package com.example.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Task01Main {
    public static void main(String[] args) throws IOException {
        // пример ручного теста
        // System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
    }

    public static String extractSoundName(File file) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "ffprobe",
                "-v", "error",
                "-of", "flat",
                "-show_format",
                file.getAbsolutePath()
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title=")) {
                    int start = line.indexOf('"');
                    int end = line.lastIndexOf('"');
                    if (start >= 0 && end > start) {
                        return line.substring(start + 1, end);
                    }
                }
            }
        }

        return null;
    }
}