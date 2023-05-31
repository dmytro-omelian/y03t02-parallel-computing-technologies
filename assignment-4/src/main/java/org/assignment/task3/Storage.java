package org.assignment.task3;

import java.util.*;

public class Storage {

    private final Map<String, Set<String>> words;

    public Storage() {
        this.words = new HashMap<>();
    }

    public Set<String> getDirsFor(String word) {
        return words.getOrDefault(word, new HashSet<>());
    }

    public Storage merge(Storage temp) {
        for (Map.Entry<String, Set<String>> entry : temp.getWords().entrySet()) {
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

    public Map<String, Set<String>> getWords() {
        return words;
    }

    public void print() {
        for (var entry: words.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(Arrays.toString(entry.getValue().toArray()));
        }
    }
}
