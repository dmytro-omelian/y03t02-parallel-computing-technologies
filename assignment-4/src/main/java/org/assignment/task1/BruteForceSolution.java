package org.assignment.task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BruteForceSolution {

    public static void main(String[] args) {
        String filePath = "src/main/java/org/assignment/data/text.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            List<String> sentences = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                sentences.add(line);
            }

            long start = System.currentTimeMillis();
            int result = findLongestWord(sentences);
            long end = System.currentTimeMillis();

            System.out.println("Result: " + result);
            System.out.println("Time: " + (end - start) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int findLongestWord(List<String> sentences) {
        int result = 0;
        for (String sentence: sentences) {
            String[] words = sentence.split(" ");
            for (String word: words) {
                result = Math.max(result, word.length());
            }
        }
        return result;
    }

}
