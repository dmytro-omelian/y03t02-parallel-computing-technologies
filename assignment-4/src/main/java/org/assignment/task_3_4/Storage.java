package org.assignment.task_3_4;

import java.util.*;

public class Storage {

    private final Map<String, Set<String>> words;

    public Storage() {
        this.words = new HashMap<>();
    }

    public Set<String> getDirsFor(String word) {
        return words.getOrDefault(word, new HashSet<>());
    }

    public synchronized Storage merge(Storage temp) {
        for (Map.Entry<String, Set<String>> entry : temp.getWords().entrySet()) {
            String word = entry.getKey();
            Set<String> values = entry.getValue();
            addAll(word, values);
        }
        return this;
    }

    public synchronized void add(String word, String value) {
        words.computeIfAbsent(word, k -> new HashSet<>()).add(value);
    }

    private void addAll(String word, Set<String> values) {
        int wasTokens = words.size();
        words.computeIfAbsent(word, k -> new HashSet<>()).addAll(values);
        int isTokens = words.size();
//        System.out.println("There were added " + (isTokens - wasTokens) + " new tokens");
    }

    public Map<String, Set<String>> getWords() {
        return words;
    }

    public void print() {
        for (var entry : words.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(Arrays.toString(entry.getValue().toArray()));
        }
    }

    public int getNumberOfUniqueWords() {
        return words.size();
    }
}
