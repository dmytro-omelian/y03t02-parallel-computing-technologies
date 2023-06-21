package org.assignment.task_3_4;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

    private final ConcurrentHashMap<String, Set<String>> words;

    public Storage() {
        this.words = new ConcurrentHashMap<>();
    }

    public Set<String> getDirsFor(String word) {
        return words.getOrDefault(word, new HashSet<>());
    }

    public Storage merge(Storage temp) {
        for (ConcurrentHashMap.Entry<String, Set<String>> entry : temp.getWords().entrySet()) {
            String word = entry.getKey();
            Set<String> values = entry.getValue();
            addAll(word, values);
        }
        return this;
    }

    public void add(String word, String value) {
        words.computeIfAbsent(word, k -> new HashSet<>()).add(value);
    }

    private void addAll(String word, Set<String> values) {
        words.computeIfAbsent(word, k -> new HashSet<>()).addAll(values);
    }

    public ConcurrentHashMap<String, Set<String>> getWords() {
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

    public List<String> getCommonWords(String name1, String name2) {
        List<String> commons = new ArrayList<>();
        for (var entry: words.entrySet()) {
            if (entry.getValue().contains(name1) && entry.getValue().contains(name2)) {
                commons.add(entry.getKey());
            }
        }
        return commons;
    }
}
