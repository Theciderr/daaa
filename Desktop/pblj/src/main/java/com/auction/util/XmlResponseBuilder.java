package com.auction.util;

import java.util.Stack;

public class XmlResponseBuilder {

    private final StringBuilder xml;
    private final Stack<String> elementStack;
    private boolean started;

    public XmlResponseBuilder() {
        this.xml = new StringBuilder();
        this.elementStack = new Stack<>();
        this.started = false;
    }

    public XmlResponseBuilder startDocument(String rootElement) {
        if (started) {
            throw new IllegalStateException("XML document already started");
        }
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        openElement(rootElement);
        started = true;
        return this;
    }

    public XmlResponseBuilder openElement(String name) {
        xml.append('<').append(name).append('>');
        elementStack.push(name);
        return this;
    }

    public XmlResponseBuilder openElement(String name, String attributeName, String attributeValue) {
        xml.append('<').append(name).append(' ')
                .append(attributeName).append("=\"").append(escape(attributeValue)).append("\">");
        elementStack.push(name);
        return this;
    }

    public XmlResponseBuilder element(String name, String value) {
        xml.append('<').append(name).append('>')
                .append(escape(value))
                .append("</").append(name).append('>');
        return this;
    }

    public XmlResponseBuilder element(String name, Number value) {
        return element(name, value != null ? value.toString() : "");
    }

    public XmlResponseBuilder closeElement() {
        if (elementStack.isEmpty()) {
            throw new IllegalStateException("No open elements to close");
        }
        String name = elementStack.pop();
        xml.append("</").append(name).append('>');
        return this;
    }

    public String build() {
        while (!elementStack.isEmpty()) {
            closeElement();
        }
        return xml.toString();
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}


