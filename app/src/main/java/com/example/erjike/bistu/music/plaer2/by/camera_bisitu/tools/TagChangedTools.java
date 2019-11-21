package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TagChangedTools {
    public static List<String> getTagList(Context context) {//查询所有tag的方法
        List<String> stringList = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        int size = sharedPreferences.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            String s = sharedPreferences.getString("tag_" + i, "空" + i);
            stringList.add(s);
        }
        return stringList;
    }

    public static void deleteAllTag(Context context) {//删除所有tag的方法
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();//删除其中所有元素
        editor.commit();//保存
    }

    public static void addTag(Context context, String tag) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int size = sharedPreferences.getInt("size", 0);
        editor.putInt("size", size + 1);//长度+1
        editor.putString("tag_" + size, tag);//第长度-1的位置添加一个元素
        editor.commit();
        Log.i("addTag", "addTag: Path():"+context.getExternalCacheDir().getPath());
        FileControl.createFile(context.getExternalCacheDir().getPath()+"/"+tag,null,null);//TODO 此处的bitmap需要修改
//        List<File> fileList = FileControl.getFile(new File(context.getExternalCacheDir().getPath()));
//        Log.i("fileList", "addTag:fileList.size :"+fileList.size());
//        for(File f:fileList){
//            Log.i("fileList", "addTag: f："+f.toString());
//        }
    }


    public static void deleteTag(Context context, int position) {
        List<String> stringList = getTagList(context);//调用上方查询方法
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        deleteAllTag(context);//删除所有
        String midstr = stringList.get(position);
        stringList.remove(position);
        editor.putInt("size",stringList.size());//设定长度
        for (int i = 0; i<stringList.size();i++){
            editor.putString("tag_"+i,stringList.get(i));//依次赋值
        }
        FileControl.deleteFiles(context.getExternalCacheDir().getPath()+"/"+midstr);
        //删除对应的标签文件夹
        editor.commit();//提交修改


    }

}
