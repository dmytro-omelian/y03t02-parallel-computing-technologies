package org.assignment.task_3_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FileProcessor extends RecursiveAction {

    private final File file;
    private final Storage sharedStorage;

    public FileProcessor(File file, Storage sharedStorage) {
        this.file = file;
        this.sharedStorage = sharedStorage;
    }

    @Override
    protected void compute() {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                List<FileProcessor> tasks = new ArrayList<>();
                for (File f : files) {
                    FileProcessor task = new FileProcessor(f, sharedStorage);
                    tasks.add(task);
                    task.fork();
                }
                for (FileProcessor task : tasks) {
                    task.join();
                }
            }
        } else {
            String filePath = file.getPath();
            String[] sentences = processFile(filePath);

            ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
            SentenceProcessor task = new SentenceProcessor(filePath, sentences, 0, sentences.length, sharedStorage);

            forkJoinPool.invoke(task);

            System.out.println(filePath + " was processed!");
        }
    }

    private String[] processFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> sentences = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                sentences.add(line);
            }

            return sentences.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

}
