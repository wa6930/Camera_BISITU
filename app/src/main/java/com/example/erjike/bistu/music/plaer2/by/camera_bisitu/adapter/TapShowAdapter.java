package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.R;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.ShowAllImageInTag;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.FileControl;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.TagChangedTools;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.ZipFolderTools;

import java.io.File;
import java.util.List;

import static com.example.erjike.bistu.music.plaer2.by.camera_bisitu.ShowAllImageInTag.TAG;

//用于显示主界面的tag
public class TapShowAdapter extends RecyclerView.Adapter<TapShowAdapter.ViewHolder> {

    private Context mContext;
    private List<String> tagList;

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public TapShowAdapter(Context mContext, List<String> tagList) {
        this.mContext = mContext;
        this.tagList = tagList;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();

        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.main_tag_item, parent, false);//导入卡片视图
        ViewHolder viewHolder = new ViewHolder(view);
        TextView tagName = (TextView) view.findViewById(R.id.main_tag_tag_name);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(tagList.get(position));
        holder.postion = position;
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        int postion;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = (TextView) itemView.findViewById(R.id.main_tag_tag_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 点击后用intent跳转到显示页面
                    Intent intent = new Intent(mContext, ShowAllImageInTag.class);
                    intent.putExtra("foldName", tagName.getText().toString());
                    mContext.startActivity(intent);//跳转到showAllMageInTag
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //TODO 弹出菜单
                    PopupMenu menu = new PopupMenu(mContext, v);
                    menu.getMenuInflater().inflate(R.menu.tag_long_click, menu.getMenu());
                    menu.show();
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.change_tagName:
                                    //TODO 重命名文件夹
                                    AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(mContext);
                                    dialogBuilder2.setTitle("更改标签内容");

                                    LayoutInflater layoutInflater2 = ((Activity) mContext).getLayoutInflater();
                                    View dialogeView2 = layoutInflater2.inflate(R.layout.dialog_rename, null);
                                    dialogBuilder2.setView(dialogeView2);
                                    final EditText editText = (EditText) dialogeView2.findViewById(R.id.dialog_rename_tag);
                                    dialogBuilder2.setPositiveButton("更改", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (!editText.getText().toString().equals("") && editText.getText().toString() != null) {
                                                FileControl.renameFile(mContext.getExternalCacheDir().getPath() + "/" + tagList.get(postion), mContext.getExternalCacheDir().getPath() + "/" + editText.getText().toString());
                                                tagList.add(postion, editText.getText().toString());
                                                tagList.remove(postion + 1);
                                                TagChangedTools.changTagName(mContext, postion, editText.getText().toString());//调用TagChang方法实现标签更改
                                                notifyDataSetChanged();
                                                Toast.makeText(mContext, "更改成功", Toast.LENGTH_SHORT).show();

                                            } else {
                                                Toast.makeText(mContext, "更改失败，请输入新名字", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    dialogBuilder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //不进行任何操作
                                        }
                                    });
                                    dialogBuilder2.show();
                                    break;
                                case R.id.delete_tag:
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                    dialogBuilder.setTitle("是否删除该标签");

                                    LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
                                    View dialogeView = layoutInflater.inflate(R.layout.dialog_delete_tag_tips, null);
                                    dialogBuilder.setView(dialogeView);
                                    dialogBuilder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            TagChangedTools.deleteTag(mContext, postion);//删除本地链表内容与文件
                                            tagList.remove(postion);//删除显示列表
                                            notifyDataSetChanged();
                                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
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
                                case R.id.share_tag:

                                    //TODO 实现文件夹压缩
                                    //TODO 调用微信分享接口，或者qq分享接口，或者其他相应分享接口
                                    List<File> fileList = FileControl.getFile(new File(mContext.getExternalCacheDir() + "/" + tagName.getText().toString()));//获取所有文件的的链表
                                    for (File f : fileList) {
                                        Log.i(TAG, "onMenuItemClick: f:" + fileList.toString());
                                    }
                                    try {
                                        ZipFolderTools.ZipFolder(mContext.getExternalCacheDir() + "/" + tagName.getText().toString(), mContext.getExternalCacheDir() + "/out.zip");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        File file = new File(mContext.getExternalCacheDir() + "/out.zip");
                                        Uri zipUri = FileProvider.getUriForFile(mContext,mContext.getPackageName()+".cameraApp",file);
                                        shareIntent.putExtra(Intent.EXTRA_STREAM, zipUri);
                                        shareIntent.setType(FileControl.getMimeType(file.getAbsolutePath()));
                                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        mContext.startActivity(Intent.createChooser(shareIntent, "分享文件"));




                                    break;
                                default:
                            }

                            return true;
                        }
                    });

                    return true;
                }
            });


        }
        //TODO 自建holder类
    }
}
