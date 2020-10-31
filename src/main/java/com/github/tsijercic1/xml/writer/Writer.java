package com.github.tsijercic1.xml.writer;

import com.github.tsijercic1.xml.common.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class Writer {
    public enum Indentation {
        NONE(""),
        SPACE(" "),
        TAB("\t"),
        SPACE2("  "),
        SPACE4("    ");

        private String indentation;

        Indentation(String indentation) {
            this.indentation = indentation;
        }

        public String getIndentation() {
            return indentation;
        }
    }

    private OutputStream outputStream;

    public Writer(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public boolean writeNode(Node node) throws IOException {
        return writeNode(node, Indentation.NONE);
    }

    public boolean writeNode(Node node, Indentation indentation) throws IOException {
        String meta = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        this.outputStream.write(meta.getBytes());
        boolean success = writeNodeWithIndentation(node, indentation.getIndentation(), 0);
        this.outputStream.flush();
        return success;
    }

    private boolean writeNodeWithIndentation(Node node, String indentation, int level) throws IOException {
        String prepend = "\n";
        for (int i = 0; i < level; i++) {
            prepend += indentation;
        }
        if (!node.getName().isEmpty()) {
            Map<String, String> attributes = node.getAttributes();
            StringBuilder attributeRow = new StringBuilder();
            for (Map.Entry<String, String> attribute : attributes.entrySet()) {
                attributeRow.append(" ").append(attribute.getKey()).append("=\"").append(attribute.getValue()).append("\"");
            }
            String opening = prepend + "<" + node.getName() + attributeRow;
            if (node.getChildNodesWithContentAsChildNodes().size() > 0) {
                opening += ">";
                this.outputStream.write(opening.getBytes());
                for (Node child : node.getChildNodesWithContentAsChildNodes()) {
                    writeNodeWithIndentation(child, indentation, level + 1);
                }
                String closing = prepend + "</" + node.getName() + ">";
                this.outputStream.write(closing.getBytes());
            } else if (node.getContent() != null && !node.getContent().isEmpty()) {
                opening += ">";
                this.outputStream.write(opening.getBytes());
                String prependContent = prepend + indentation;
                this.outputStream.write((prependContent + node.getContent()).getBytes());
                String closing = prepend + "</" + node.getName() + ">";
                this.outputStream.write(closing.getBytes());
            } else {
                opening += "/>";
                this.outputStream.write(opening.getBytes());
            }
        } else {
            prepend += indentation;
            this.outputStream.write((prepend + node.getContent()).getBytes());
        }
        return true;
    }
}
