package org.assignment.task_3_4;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Завдання:
 * <p>
 * 3. Розробіть та реалізуйте алгоритм пошуку спільних слів в текстових документах
 * з використанням ForkJoinFramework.

 * 4. Розробіть та реалізуйте алгоритм пошуку текстових документів, які відповідають
 * заданим ключовим словам (належать до області «Інформаційні технології»),
 * з використанням ForkJoinFramework.
 */

public class Main {
    public static void main(String[] args) {
        final File folder = new File("src/main/java/org/assignment/data/");

        Storage sharedStorage = new Storage();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        FileProcessor task = new FileProcessor(folder, sharedStorage);

        long start = System.currentTimeMillis();
        forkJoinPool.invoke(task);
        long end = System.currentTimeMillis();

        try {
            forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            forkJoinPool.shutdown();
        }

        System.out.println();
        System.out.println("Time: " + (end - start) + " ms");

        System.out.println("Total unique tokens: " + sharedStorage.getNumberOfUniqueWords());


        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input keywords: ");
        String line = scanner.nextLine();
        String[] keywords = line.split(" ");
        for (String keyword: keywords) {
            System.out.print(keyword + " : ");
            System.out.println(sharedStorage.getDirsFor(keyword));
        }

        String name1 = "src/main/java/org/assignment/data/school.txt";
        String name2 = "src/main/java/org/assignment/data/university.txt";

        System.out.println(sharedStorage.getCommonWords(name1, name2));
    }
}