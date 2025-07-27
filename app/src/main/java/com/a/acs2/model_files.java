package com.a.acs2;

public class model_files {
    private final String name;
    private final String path;
    private final boolean isDirectory;
    private final long size;
    private final String formattedSize;

    public model_files(String name, String path, boolean isDirectory, long size) {
        this.name = name;
        this.path = path;
        this.isDirectory = isDirectory;
        this.size = size;
        this.formattedSize = formatSize(size);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public long getSize() {
        return size;
    }

    public String getFormattedSize() {
        return formattedSize;
    }

    private String formatSize(long size) {
        if (size <= 0) return "0 MB";
        double sizeInMB = size / (1024.0 * 1024.0);
        return String.format("%.2f MB", sizeInMB);
    }
}