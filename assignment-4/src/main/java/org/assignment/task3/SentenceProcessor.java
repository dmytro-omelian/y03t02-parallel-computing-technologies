package org.assignment.task3;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class SentenceProcessor extends RecursiveAction {

    private final Storage sharedStorage;
    private final String filePath;
    private final String[] sentences;
    private final int start;
    private final int end;

    public SentenceProcessor(String filePath, String[] sentences, int start, int end, Storage sharedStorage) {
        this.filePath = filePath;
        this.sentences = sentences;
        this.start = start;
        this.end = end;
        this.sharedStorage = sharedStorage;
    }

    @Override
    protected void compute() {
        if (end - start <= 30) {
            processSentences(sentences, start, end);
            return;
        }

        int mid = (start + end) / 2;
        SentenceProcessor leftTask = new SentenceProcessor(filePath, sentences, start, mid, sharedStorage);
        SentenceProcessor rightTask = new SentenceProcessor(filePath, sentences, mid, end, sharedStorage);

        ForkJoinTask.invokeAll(leftTask, rightTask);

        leftTask.join();
        rightTask.join();
    }

    private void processSentence(String sentence) {
        Storage result = new Storage();
        String regex = "([^a-zA-Z']+)'*\\1*";
        String[] words = sentence.split(regex);
        for (String word : words) {
            result.add(word, filePath);
        }
        sharedStorage.merge(result);
    }

    private void processSentences(String[] sentences, int start, int end) {
        for (int i = start; i < end; ++i) {
            processSentence(sentences[i]);
        }
    }
}
