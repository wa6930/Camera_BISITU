package com.example.erjike.bistu.music.plaer2.by.camera_bisitu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter.TagChangeAdapter;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter.TapShowAdapter;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.FileControl;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.TagChangedTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ImageView imageView2;
    public static final int PICK_PHOTO = 102;
    public static final int TAKE_CAMERA = 101;
    public static final String TAG = "MainActivityErJike";
    private Uri imageUri;
    List<String> stringList;
    //上标题栏三大功能
    ImageView image_createTag;
    TextView text_createTag;
    ImageView image_camera;
    TextView text_camera;
    ImageView image_folder;
    TextView text_foler;

    //显示标签的RecyclerView
    RecyclerView tag_reyclerView;
    TapShowAdapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        //绑定导航菜单
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_tag:
                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("是否删除该标签");

                LayoutInflater layoutInflater=getLayoutInflater();
                View dialogeView=layoutInflater.inflate(R.layout.dialoge_delete_tag_tips,null);
                dialogBuilder.setView(dialogeView);
                dialogBuilder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO 完成实现方法
                        FileControl.deleteChildFiles(getExternalCacheDir().getPath());//删除所有文件夹
                        TagChangedTools.deleteAllTag(MainActivity.this);//删除所有的tag
                        stringList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        // 删除文件夹与内部所有文件，并且删除内部标签
                    }
                });
                dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //不进行任何操作
                    }
                });
                dialogBuilder.show();
                break;
            default:
        }
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //对标题功能键赋值与绑定
        image_createTag = (ImageView)findViewById(R.id.create_tag_Image);
        text_createTag = (TextView)findViewById(R.id.create_tag);
        image_camera = (ImageView)findViewById(R.id.open_camera_image);
        text_camera = (TextView)findViewById(R.id.open_camera);
        image_folder = (ImageView)findViewById(R.id.open_folder_image);
        text_foler = (TextView)findViewById(R.id.open_folder);
        //RecyclerView赋值
        tag_reyclerView = (RecyclerView)findViewById(R.id.main_tag_recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);//一行显示两个
        tag_reyclerView.setLayoutManager(layoutManager);
        stringList = new ArrayList<>();
        stringList.addAll(TagChangedTools.getTagList(MainActivity.this));//对链表付初始值
        for(int i =0 ;i<stringList.size();i++){
            Log.i(TAG, "onCreate: stringList["+i+"]:"+stringList.get(i));//标签添加成功
        }
        Log.i(TAG, "onCreate: stringList遍历完毕！");

        adapter = new TapShowAdapter(MainActivity.this,stringList);
        tag_reyclerView.setAdapter(adapter);//显示
        adapter.notifyDataSetChanged();

        //设置界面显示元素
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            int color= Color.parseColor("#14BFF3");//设为亮蓝色
            ColorDrawable drawable = new ColorDrawable(color);
            actionBar.setBackgroundDrawable(drawable);//隐藏标题栏
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //>21时，修改导航栏颜色
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorLightBlue));
        }
        //实现新建标签的功能
        View.OnClickListener createTag_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("创建新标签");
                LayoutInflater layoutInflater=getLayoutInflater();
                View dialogeView=layoutInflater.inflate(R.layout.dialoge_create_tag,null);
                dialogBuilder.setView(dialogeView);
                //TODO 设计adapter
                final EditText editText=(EditText) dialogeView.findViewById(R.id.dialog_CreateByTagName);
                dialogBuilder.setPositiveButton("新建", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!editText.getText().toString().equals("")||editText!=null){
                            stringList.add(editText.getText().toString());
                            TagChangedTools.addTag(MainActivity.this,editText.getText().toString());
                            Toast.makeText(MainActivity.this,"添加标签成功！",Toast.LENGTH_SHORT).show();//与显示添加标签冲突
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //不进行任何操作
                    }
                });
                dialogBuilder.show();

            }
        };
        //实现点击跳转到拍照界面
        View.OnClickListener camera_listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建File对象，用于存储拍照后的图片
                //存放在手机SD卡的应用关联缓存目录下
                File outputImage = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
                    outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                    Log.i(TAG, "onClick: getExternalCacheDir():"+getExternalCacheDir().getName());
                    //地址为：/storage/emulated/0/Android/data/com.example.erjike.bistu.music.plaer2.by.camera_bisitu/cache
                }

                //TODO 存储名字随时间标签修改而修改
               /* 从Android 6.0系统开始，读写SD卡被列为了危险权限，如果将图片存放在SD卡的任何其他目录，
                  都要进行运行时权限处理才行，而使用应用关联 目录则可以跳过这一步
                */
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();//该图片存在则先删除
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //大于24，7.0
                    //TODO：获取拍照的url
                    imageUri = FileProvider.getUriForFile(MainActivity.this, "com.erjike.cameraApp", outputImage);
                    Log.i(TAG, "onClick: version>=24");
                } else {
                    //小于android 7.0的方法
                    imageUri = Uri.fromFile(outputImage);
                    Log.i(TAG, "onClick: version<24");
                    //TODO:直接获取图片
                }
                //动态授予权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_CAMERA);
                }


            }
        };
        //实现点击跳转到图片选择界面
        View.OnClickListener fold_listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_PHOTO);
                }

            }
        };
        image_createTag.setOnClickListener(createTag_listener);
        text_createTag.setOnClickListener(createTag_listener);
        image_camera.setOnClickListener(camera_listener);
        text_camera.setOnClickListener(camera_listener);
        image_folder.setOnClickListener(fold_listener);//赋予点击事件
        text_foler.setOnClickListener(fold_listener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Log.i(TAG, "onActivityResult: requestCode:"+requestCode);//RequestCode不会变
        switch (requestCode) {
            case TAKE_CAMERA:
                //照相机返回内容实现
                dialogShowCamera(data);
                break;
            case PICK_PHOTO:
                if (Build.VERSION.SDK_INT >= 19) {
                    //android 4.4以上版本使用此方法处理图片

                    // 获取选择图片（或者选择的所有图片？）
                    dialogShowKitKat(data);

                } else {
                    //android 4.4以下版本处理图片的方式
                    dialogShowBeforeKitKat(data);
                    //同上
                }

                break;
            default:
        }
    }
    private void handleImageBeforeKitKat(Intent data,ImageView imageView) {
        Uri uri = data.getData();
        Log.i(TAG, "handleImageBeforeKitKat: data:"+data);
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath,imageView);
    }


    private void dialogShowBeforeKitKat(Intent data){//版本低的对话框内容
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("选择图片的标签");
        LayoutInflater layoutInflater=getLayoutInflater();
        View dialogeView=layoutInflater.inflate(R.layout.dialoge_camera,null);
        dialogBuilder.setView(dialogeView);
        //TODO 设计adapter
        ImageView dialog_image=(ImageView) dialogeView.findViewById(R.id.dialog_imageView);
        RecyclerView dilog_recyclerview=(RecyclerView) dialogeView.findViewById(R.id.dialog_recycler);
        TagChangeAdapter adapter = new TagChangeAdapter(MainActivity.this,stringList,dialog_image);//获取这三者信息
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
        dilog_recyclerview.setLayoutManager(layoutManager);
        dilog_recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Bitmap bitmap = FileControl.ImageToBitmap(dialog_image);//将图片转换为bitmap形式
        adapter.setBitmap(bitmap);//绑定adapter的bitmap
        handleImageOnKitKat(data,dialog_image);//查图
        //绑定adapter
        handleImageBeforeKitKat(data,dialog_image);//调用查图方法
        dialogBuilder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //无功能
            }
        });
        dialogBuilder.show();


    }
    private void dialogShowKitKat(Intent data){//获取返回内容时，弹出对话框
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("选择图片的标签");
        LayoutInflater layoutInflater=getLayoutInflater();
        View dialogeView=layoutInflater.inflate(R.layout.dialoge_camera,null);
        dialogBuilder.setView(dialogeView);
        //TODO 设计adapter
        ImageView dialog_image=(ImageView) dialogeView.findViewById(R.id.dialog_imageView);
        RecyclerView dilog_recyclerview=(RecyclerView) dialogeView.findViewById(R.id.dialog_recycler);
        TagChangeAdapter adapter = new TagChangeAdapter(MainActivity.this,stringList,dialog_image);//获取这三者信息
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
        dilog_recyclerview.setLayoutManager(layoutManager);
        dilog_recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        Bitmap bitmap = FileControl.ImageToBitmap(dialog_image);//将图片转换为bitmap形式
//        adapter.setBitmap(bitmap);//绑定adapter的bitmap
        handleImageOnKitKat(data,dialog_image);//查图
        dialogBuilder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //无功能
            }
        });
        dialogBuilder.show();

    }

    private void dialogShowCamera(Intent data){//获取返回内容时，弹出对话框
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView2.setImageBitmap(bitmap);

        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setTitle("选择图片的标签");
        LayoutInflater layoutInflater=getLayoutInflater();
        View dialogeView=layoutInflater.inflate(R.layout.dialoge_camera,null);
        dialogBuilder.setView(dialogeView);
        //TODO 设计adapter
        ImageView dialog_image=(ImageView) dialogeView.findViewById(R.id.dialog_imageView);
        dialog_image.setImageBitmap(bitmap);
        RecyclerView dilog_recyclerview=(RecyclerView) dialogeView.findViewById(R.id.dialog_recycler);
        TagChangeAdapter adapter = new TagChangeAdapter(MainActivity.this,stringList,dialog_image);//获取这三者信息
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
        dilog_recyclerview.setLayoutManager(layoutManager);
        dilog_recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setBitmap(bitmap);//绑定adapter中的Bitmap
        //TODO 绑定adapter

        dialogBuilder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //无功能
            }
        });
        dialogBuilder.show();

    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data, ImageView imageView) {
        Log.i(TAG, "handleImageBeforeKitKat: data:"+data);
        String imagePath = null;
        Uri uri = data.getData();
        Log.i(TAG, "handleImageOnKitKat: getAuthority():"+uri.getAuthority());
        if (DocumentsContract.isDocumentUri(MainActivity.this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                //解析出数字格式的id
                Log.i(TAG, "handleImageOnKitKat: docId.split[1]"+docId);
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
            else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())){
                imagePath = getImagePath(uri,null);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                imagePath = uri.getPath();
            }
            displayImage(imagePath,imageView);
        }
    }

    private void displayImage(String imagePath,ImageView imageView) {
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView2.setImageBitmap(bitmap);
            imageView.setImageBitmap(bitmap);

        }else{
            Toast.makeText(MainActivity.this,"获取图片失败！",Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri,String selection) {
        //TODO
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
