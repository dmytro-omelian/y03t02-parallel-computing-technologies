package org.assignment.task_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/java/org/assignment/data/text.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> sentences = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                sentences.add(line);
            }

            String[] sentenceArray = sentences.toArray(new String[0]);

            ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
            SentenceProcessor task = new SentenceProcessor(sentenceArray, 0, sentenceArray.length);

            long start = System.currentTimeMillis();
            Integer result = forkJoinPool.invoke(task);
            long end = System.currentTimeMillis();

            System.out.println("Result: " + result);
            System.out.println("Time: " + (end - start) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}