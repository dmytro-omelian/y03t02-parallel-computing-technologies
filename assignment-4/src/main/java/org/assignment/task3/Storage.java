package org.assignment.task3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Storage {

    private final Map<String, Set<String>> words;

    public Storage() {
        this.words = new HashMap<>();
    }

    public void add(String word, String value) {
        words.computeIfAbsent(word, k -> new HashSet<>()).add(value);
    }

    public Set<String> getDirsFor(String word) {
        return words.getOrDefault(word, new HashSet<>());
    }

    public Storage merge(Storage temp) {
        for (Map.Entry<String, Set<String>> entry: temp.getWords().entrySet()) {
            String word = entry.getKey();
            Set<String> values = entry.getValue();
            for (String value: values) {
                this.add(word, value);
            }
        }
        return this;
    }

    public Map<String, Set<String>> getWords() {
        return words;
    }
}
