package com.pop.demo.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by pengfu on 04/03/2018.
 */

@DatabaseTable
public class PopMedia {


    @DatabaseField
    private String path ;

    @DatabaseField
    private String fileName ;

    public PopMedia(){}

    public PopMedia(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "PopMedia{" +
                "path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
