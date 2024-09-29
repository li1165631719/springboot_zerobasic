package org.example.util;

class SensitiveWordFilter {



    private class Node {
        private boolean isKeyword;
        private Node[] next = new Node[26];
    }

    private Node root = new Node();

    public void addSensitiveWord(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (current.next[index] == null) {
                current.next[index] = new Node();
            }
            current = current.next[index];
        }
        current.isKeyword = true;
    }

    public boolean containsSensitiveWord(String text) {
        Node current = root;
        int index;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'a' && c <= 'z') {
                index = c - 'a';
                if (current.next[index] == null) {
                    break;
                }
                current = current.next[index];
                if (current.isKeyword) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SensitiveWordFilter filter = new SensitiveWordFilter();
        filter.addSensitiveWord("badword");
        filter.addSensitiveWord("anotherbadword");

        boolean contains = filter.containsSensitiveWord("This is a test string with badword.");
        System.out.println("Contains sensitive word: " + contains); // 应该输出 "Contains sensitive word: true"
    }
}





