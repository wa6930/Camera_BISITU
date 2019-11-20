package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.Model.ImageModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.erjike.bistu.music.plaer2.by.camera_bisitu.ShowAllImageInTag.TAG;

public class FileControl {
    /***
     * 创建文件
     *
     * @param filePath 文件地址
     * @param fileName 文件名
     */
    public static boolean createFile(String filePath, String fileName, Bitmap bitmap) {
        //保存bitmap成为一个图片文件
        File file = new File(filePath);
        if (!file.exists()) {//如果该文件夹不存在，则创建该文件夹
            file.mkdirs();//创建文件夹
            //创建了一个文件夹
        }
        if (fileName == null || bitmap == null) {
            Log.i("createFile", "createFile: 创建文件失败（如果是添加标签则忽略）");
            return false;
        }
        //TODO 如果输入bitmap与fileName 则创建图片
        String strFilePath = fileName + ".jpg";
        File subfile = new File(filePath, strFilePath);
        if (!subfile.exists()) {
            try {
                boolean b = subfile.createNewFile();
                FileOutputStream fos = new FileOutputStream(subfile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                Log.i("createFile", "createFile: 图保存成功");
                return b;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                FileOutputStream fos = null;
                fos = new FileOutputStream(subfile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                Log.i("createFile", "createFile: 图保存成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    /**
     * @param file 地址
     */
    public static List<File> getFile(File file) {
        List<File> list = new ArrayList<>();
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return null;
        } else {
            for (File f : fileArray) {
//                if(f.isFile()){
                list.add(f);//这里不检测是否是文件夹，因为文件夹也要计算
//                }else{
//                    //getFile(f);//不是文件就是文件夹，继续向下一层遍历,这里就不添加下一层的文件了
//                }
            }
        }
        return list;

    }

    /**
     * 获得所有ImageModel形式的链表
     *
     * @param list getFile生成的文件链表
     */
    public static List<ImageModel> getImage(List<File> list) {

        List<ImageModel> bitList = new ArrayList<>();
        for (File f : list) {
            if (f == null) {
                Log.i(TAG, "getImage: 有空文件");
                continue;
            }

            Bitmap bitmap = BitmapFactory.decodeFile(f.getPath());//该步出了问题
            String mapName = f.getName();
            Log.i(TAG, "getImage: Name:" + f.getName());
            Log.i(TAG, "getImage:Bitmap" + bitmap.toString());
            bitList.add(new ImageModel(bitmap, mapName));
            Log.i(TAG, "getImage: 成功读取bitmap:" + f.getName());
        }
        //TODO 实现List<File>转List<Bitmap>
        return bitList;
    }

    ;

    /**
     * 删除文件（包括自身）
     * 删除对应目录下的所有的文件(包括自身)
     *
     * @param filePath 文件地址
     */
    public static boolean deleteFiles(String filePath) {
        List<File> files = getFile(new File(filePath));
        if (files.size() != 0) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
//                if(file.isFile()){//这里不检测是否为文件，删除文件夹下的所有内容
                file.delete();//删除目录下的所有文件
//                }
            }
        }
        File pathFile = new File(filePath);
        pathFile.delete();//删除自身
        return true;

    }

    /**
     * 删除文件夹下的所有子文件（不包括自身）
     * 删除对应目录下的所有的文件（不包括自身）
     *
     * @param filePath 文件地址
     */

    public static boolean deleteChildFiles(String filePath) {
        List<File> files = getFile(new File(filePath));
        if (files.size() != 0) {
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
//                if(file.isFile()){//这里不检测是否为文件，删除文件夹下的所有内容
                file.delete();//删除目录下的所有文件
//                }
            }
        }
        return true;

    }

    /**
     * 重命名文件
     *
     * @param oldPath 原文件名
     * @param newPath 修改后文件名
     */
    public static void renameFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        oldFile.renameTo(newFile);
    }

    /**
     * 将ImageView转化为Bitmap
     *
     * @param imageView 要转化的图片
     */
    public static Bitmap ImageToBitmap(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return bitmap;
    }


}
