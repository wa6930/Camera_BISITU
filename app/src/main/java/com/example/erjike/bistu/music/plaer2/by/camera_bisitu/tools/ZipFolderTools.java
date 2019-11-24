package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools;

import android.system.ErrnoException;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.example.erjike.bistu.music.plaer2.by.camera_bisitu.ShowAllImageInTag.TAG;

public class ZipFolderTools {
    /**
     * 压缩文件和文件夹
     *
     * @param srcFileString 要压缩的文件和文件夹
     * @param zipFileString 压缩完成的Zip路径
     * @throws Exception
     */
    public static void ZipFolder(String srcFileString,String zipFileString) throws Exception{
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
        File file = new File(srcFileString);
        Log.i(TAG, "ZipFolder: zip路径为："+zipFileString+"  file.getPath():"+file.getPath()+File.separator+"  file.getPath().absolutePath:"+file.getAbsolutePath());
        //至少内置存储，getPath与absolutePath输出内容一致。
        if(file.isFile()){
            //如果不为文件夹时
            Log.e(TAG, "ZipFolder: 压缩文件夹时不输入文件");
        }else if(file.isDirectory()){//如果是文件夹
            //如果是文件夹的话
            List<File> fileList = FileControl.getFile(file);//获取所有文件的的链表
            //Log.i(TAG, "ZipFolder: fileList.size:"+fileList.size());
            String base = "";
            if(fileList.size()!=0){
                base += "/";

            }else{
                base = "";
            }
            for(File f :fileList){
                outZip.putNextEntry(new ZipEntry(base+f.getName()));
                FileInputStream inputStream = new FileInputStream(f);
                if(f.isFile()){
                    int len;
                    byte[] buffer = new byte[4096];
                    while((len = inputStream.read(buffer))!=-1){//如果没有读取完毕
                        outZip.write(buffer,0,len);
                    }
                    inputStream.close();
                }
            }
        }
        //ZipFiles(file.getPath()+File.separator,file.getName(),outZip);//可能不使用该方法，仅留作备份

        outZip.finish();
        outZip.close();
    }

    /**
     * 压缩文件（未使用）
     *
     * @param folderString 要压缩的文件
     * @param fileString zip对应的路径
     * @param zipOutputStream Zip输出流
     * @throws Exception
     */
    private static void ZipFiles(String folderString,String fileString,ZipOutputStream zipOutputStream) throws Exception{
        if(zipOutputStream==null){
            return;
        }
        File file = new File(folderString+fileString);
        if(file.isFile()){
            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputStream.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while((len = inputStream.read(buffer)) != -1){
                zipOutputStream.write(buffer, 0,len);
            }
            zipOutputStream.closeEntry();
        }else {
            String fileList[] = file.list();
            if(fileList.length<=0){
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.closeEntry();
            }
            for(int i = 0;i<fileList.length;i++){
                ZipFiles(folderString+fileString+"/",fileList[i],zipOutputStream);
            }
        }

    }


}
