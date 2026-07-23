package com.mason.mapgen.paint.components.panes.topPane.resources;

import java.io.File;

public class SaveLocation{


    private File file = null;


    public SaveLocation(){}


    public void setFile(File file){
        this.file = file;
    }

    public void unsetFilePath(){
        file = null;
    }

    public boolean isFileSet(){
        return file != null;
    }

    public File getFile(){
        if(!isFileSet()){
            throw new IllegalStateException("Paint save file path unset!");
        }
        return file;
    }

}
