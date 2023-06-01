package org.assignment.task1;

import java.util.concurrent.RecursiveTask;

public class SentenceProcessor extends RecursiveTask<Integer> {

    private final String[] sentences;
    private final int start;
    private final int end;

    public SentenceProcessor(String[] sentences, int start, int end) {
        this.sentences = sentences;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= 30) {
            return processSentences(sentences, start, end);
        }

        int mid = (start + end) / 2;
        SentenceProcessor leftTask = new SentenceProcessor(sentences, start, mid);
        SentenceProcessor rightTask = new SentenceProcessor(sentences, mid, end);

        SentenceProcessor.invokeAll(leftTask, rightTask);

        int leftMaxValue = leftTask.join();
        int rightMaxValue = rightTask.join();
        return Math.max(leftMaxValue, rightMaxValue);
    }

    private int processSentences(String[] sentences, int start, int end) {
        int result = 0;
        for (int i = start; i < end; ++i) {
            int temp = processSentence(sentences[i]);
            result = Math.max(result, temp);
        }
        return result;
    }

    private int processSentence(String sentence) {
        int result = 0;
        String[] words = sentence.split(" ");
        for (String word : words) {
            int currentWordLength = word.length();
            result = Math.max(result, currentWordLength);
        }
        return result;
    }

}
