package com.feng.demo.data;

/**
 * Created by feng.jiang on 2016/1/20.
 */
public class FileInfo {
    String name;
    String path;
    public FileInfo(String name,String path){
        this.name = name;
        this.path = path;
    }
    public String getName(){
        return name;
    }
    public String getPath(){
        return path;
    }
}
