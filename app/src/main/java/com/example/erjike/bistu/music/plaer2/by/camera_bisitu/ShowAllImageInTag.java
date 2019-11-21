package com.example.erjike.bistu.music.plaer2.by.camera_bisitu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.Model.ImageModel;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter.ImageShowAdapter;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.FileControl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowAllImageInTag extends AppCompatActivity {
    RecyclerView recyclerView;
    public static final String TAG = "ShowImage";
    List<ImageModel> imageModelLsit;
    ImageShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_image_in_tag);
        recyclerView = (RecyclerView) findViewById(R.id.show_all_image_recyclerView);
        imageModelLsit = new ArrayList<>();
        Intent intent = getIntent();//获取intent
        String pathName = intent.getStringExtra("foldName");//显示成功
        String chacheDir = getExternalCacheDir().getPath();///storage/emulated/0/Android/data/com.example.erjike.bistu.music.plaer2.by.camera_bisitu/cache
        //Log.i(TAG, "onCreate: chacheDir:"+chacheDir+"  pathName:"+pathName);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            int color = Color.parseColor("#14BFF3");//设为亮蓝色
            ColorDrawable drawable = new ColorDrawable(color);
            actionBar.setBackgroundDrawable(drawable);//隐藏标题栏
            actionBar.setTitle("标签：" + pathName);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //>21时，修改导航栏颜色
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorLightBlue));
            window.setTitle("标签：" + pathName);
        }
        imageModelLsit.addAll(FileControl.getImage(FileControl.getFile(new File(getExternalCacheDir() + "/" + pathName))));
        //获取读取的图片内容
        for (ImageModel imageModel : imageModelLsit) {
            Log.i(TAG, "onCreate: name:" + imageModel.getImageName() + " bitmap:" + imageModel.getImage().toString());
        }
        adapter = new ImageShowAdapter(ShowAllImageInTag.this, imageModelLsit);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ShowAllImageInTag.this, 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();
        
    }
}
