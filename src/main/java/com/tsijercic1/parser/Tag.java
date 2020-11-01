package com.tsijercic1.xml.parser;

import java.util.ArrayList;

class Tag {
    private String tag;
    private ArrayList<Tag> children;

    Tag(String tag) {
        this.tag = tag;
        children = new ArrayList<>();
    }

    void addChild(Tag tag) {
        children.add(tag);
    }

    String getTag() {
        return tag;
    }

    ArrayList<Tag> getChildren() {
        return children;
    }

    void setTag(String tag) {
        this.tag = tag;
    }

    void setChildren(ArrayList<Tag> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(tag);
        for (Tag child : children) {
            result.append("\n\t").append(child.toString("\t\t"));
        }
        return result.toString();
    }

    private String toString(String space) {
        StringBuilder result = new StringBuilder(tag);
        for (Tag child : children) {
            result.append("\n").append(space).append(child.toString("\t" + space));
        }
        return result.toString();
    }

    String getName() {
        StringBuilder name = new StringBuilder();
        for (int i = 1; i < tag.length(); i++) {
            if (tag.charAt(i) == ' '
                    || tag.charAt(i) == '\t'
                    || tag.charAt(i) == '\n'
                    || tag.charAt(i) == '\r'
                    || tag.charAt(i) == '>'
                    || tag.charAt(i) == '/') {
                break;
            }
            name.append(tag.charAt(i));
        }
        return name.toString();
    }
}
