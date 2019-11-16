package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileControl {
    /***
     * 创建文件
     *
     * @param filePath 文件地址
     * @param fileName 文件名
     */
    public static boolean createFile(String filePath,String fileName){
        String strFilePath = filePath+fileName;
        File file = new File(filePath);
        if(!file.exists()){//如果该文件夹不存在，则创建该文件夹
            file.mkdirs();//创建文件夹
        }
        File subfile = new File(strFilePath);

        if(!subfile.exists()){
            try {
                boolean b =subfile.createNewFile();
                return b;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return true;
        }
        return false;
    }
    /**
     *
     * @param file 地址
     */
    public static List<File> getFile(File file){
        List<File> list = new ArrayList<>();
        File[] fileArray = file.listFiles();
        if(fileArray==null){
            return null;
        }else{
            for(File f :fileArray){
                if(f.isFile()){
                    list.add(f);
                }else{
                    //getFile(f);//不是文件就是文件夹，继续向下一层遍历,这里就不添加下一层的文件了
                }
            }
        }
        return list;

    }

    /**
     * 删除文件
     * 删除对应目录下的所有的文件
     *
     * @param filePath 文件地址
     */
    public static boolean deleteFiles(String filePath){
        List<File> files = getFile(new File(filePath));
        if(files.size()!=0){
            for(int i=0;i<files.size();i++){
                File file = files.get(i);
                if (file.isFile()){
                    file.delete();
                }
            }
        }
        return true;

    }
    /**
     * 重命名文件
     *
     * @param oldPath 原文件名
     * @param newPath 修改后文件名
     *
     */
    public static void renameFile(String oldPath,String newPath){
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        oldFile.renameTo(newFile);
    }

}
