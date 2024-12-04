package com.example.manttoprev.Modelo;

public class PDFItem {
    private final String name;
    private final String url;

    public PDFItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

