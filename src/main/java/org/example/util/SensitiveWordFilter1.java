package org.example.util;


public class SensitiveWordFilter1 {

    private TrieNode rootNode = new TrieNode();

    public void addWord(String word) {
        TrieNode node = rootNode;
        for (char c : word.toCharArray()) {
            int index = c - 'A';
            if (node.getChildren()[index] == null) {
                node.getChildren()[index] = new TrieNode();
            }
            node = node.getChildren()[index];
        }
        node.setKeywordEnd(true);
    }

    public boolean contains(String text) {
        TrieNode node = rootNode;
        for (char c : text.toCharArray()) {
            int index = c - 'A';
            if (node.getChildren()[index] == null) {
                return false;
            }
            node = node.getChildren()[index];
        }
        return node.isKeywordEnd();
    }

    public static void main(String[] args) {
        SensitiveWordFilter1 filter = new SensitiveWordFilter1();
        filter.addWord("BAD");
        filter.addWord("CRAP");

        boolean containsBadWord = filter.contains("ABCDABCD"); // 应该返回 false，因为文本中没有敏感词
        boolean containsSensitiveWord = filter.contains("BADCRAP"); // 应该返回 true，因为文本中包含敏感词 "BAD" 和 "CRAP"

        System.out.println("Contains sensitive word: " + containsSensitiveWord);
        System.out.println("Contains bad word: " + containsBadWord);
    }
}

class TrieNode {
    private boolean isKeywordEnd = false;
    private TrieNode[] children = new TrieNode[26];

    public boolean isKeywordEnd() {
        return isKeywordEnd;
    }

    public void setKeywordEnd(boolean keywordEnd) {
        isKeywordEnd = keywordEnd;
    }

    public TrieNode[] getChildren() {
        return children;
    }
}