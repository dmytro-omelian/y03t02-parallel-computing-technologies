package org.assignment.task3;

import java.io.File;

/**

 Завдання:

 3. Розробіть та реалізуйте алгоритм пошуку спільних слів в текстових документах
 з використанням ForkJoinFramework.

 */

public class Main {
    public static void main(String[] args) {
        final File folder = new File("src/main/java/org/assignment/data/");
        listFilesForFolder(folder);

        /*
        We can process task with processing directory (with threads)
        it can be recursion task ForkJoin and on threshold 1 we should process file and wait until it finished.
        then just return result;

        1. there can be folders
        2. there can be some files inside each folder
        3. we should have hashmap with key (word) and values (paths where word located)
        4. then we can by word find documents where we have met this word
        5. there is possibility that we could have several same words in document,
        so value in hashmap should be Set

        6. what if to process newly create files? BlockingQueue
         */
    }

    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry: folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

}