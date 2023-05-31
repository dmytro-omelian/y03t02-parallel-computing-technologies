package org.assignment.task3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class FileProcessor extends RecursiveAction {

    private final File file;

    public FileProcessor(File file) {
        this.file = file;
    }

    @Override
    protected void compute() {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                List<FileProcessor> tasks = new ArrayList<>();
                for (File f : files) {
                    FileProcessor task = new FileProcessor(f);
                    tasks.add(task);
                    task.fork();
                }
                for (FileProcessor task : tasks) {
                    task.join();
                }
            }
        } else {
            // Process the file here
            System.out.println("Processing file: " + file.getAbsolutePath());
        }
    }

}
