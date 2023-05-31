package org.assignment.task3;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SentenceProcessor extends RecursiveTask<Storage> {

    private final String filePath;
    private final String[] sentences;
    private final int start;
    private final int end;

    public SentenceProcessor(String filePath, String[] sentences, int start, int end) {
        this.filePath = filePath;
        this.sentences = sentences;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Storage compute() {
        if (end - start <= 30) {
            return processSentences(sentences, start, end);
        }

        int mid = (start + end) / 2;
        SentenceProcessor leftTask = new SentenceProcessor(filePath, sentences, start, mid);
        SentenceProcessor rightTask = new SentenceProcessor(filePath, sentences, mid, end);

        ForkJoinTask.invokeAll(leftTask, rightTask);

        Storage leftStorage = leftTask.join();
        Storage rightStorage = rightTask.join();

        return leftStorage.merge(rightStorage);
    }

    private Storage processSentence(String sentence) {
        Storage result = new Storage();
        String[] words = sentence.split(" ");
        for (String word: words) {
            result.add(word, filePath);
        }
        return result;
    }

    private Storage processSentences(String[] sentences, int start, int end) {
        Storage storage = new Storage();
        for (int i = start; i < end; ++ i) {
            Storage temp = processSentence(sentences[i]);
            storage.merge(temp);
        }
        return storage;
    }
}
