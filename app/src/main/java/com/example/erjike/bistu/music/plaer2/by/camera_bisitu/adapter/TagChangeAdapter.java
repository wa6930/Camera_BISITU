package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.MainActivity;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.R;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.ShowAllImageInTag;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.TagChangedTools;

import java.io.File;
import java.util.List;

/**
 * 用于显示在dialog界面，用于选择图片的添加位置。
 *
 */
public class TagChangeAdapter extends RecyclerView.Adapter<TagChangeAdapter.ViewHolder> {

    private Context mContext;
    private List<String> tagList;
    private ImageView outImage;


    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }


    public TagChangeAdapter(Context mContext, List<String> tagList, ImageView outImage) {
        this.mContext = mContext;
        this.tagList = tagList;
        this.outImage = outImage;
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
        final View view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, parent, false);//导入卡片视图
        ViewHolder viewHolder = new ViewHolder(view);
        TextView tagName = (TextView) view.findViewById(R.id.item_tagName);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 书写点击了标签后应该将图片添加到对应的文件夹下。
                Bitmap bitmap = ((BitmapDrawable)outImage.getDrawable()).getBitmap();//获得BitMap

            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(tagList.get(position));
        holder.postion=position;
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
            tagName = (TextView) itemView.findViewById(R.id.item_tagName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 点击后添加到对应的标签中
                }
            });


        }
        //TODO 自建holder类
    }
    public  void saveBitmapFile(Bitmap bitmap){
        //File temp = new File(mContext.getExternalCacheDir().get,)
    }

}
