package io.github.emergentorganization.cellrpg.tools;

import io.github.emergentorganization.cellrpg.tools.mapeditor.map.MapTools;

import java.io.File;


public class FileListNode {
    public final File file;

    public FileListNode(String path) {
        this.file = new File(path);
    }

    public FileListNode(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        String name = file.getName();
        return name.substring(0, name.length() - MapTools.EXTENSION.length());
    }
}
