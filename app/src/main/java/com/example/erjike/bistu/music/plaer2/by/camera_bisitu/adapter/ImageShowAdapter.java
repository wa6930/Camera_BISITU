package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.Model.ImageModel;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.R;

import java.util.List;

/**
 * 用于显示在dialog界面，用于选择图片的添加位置。
 *
 */
public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder> {

    private Context mContext;
    private List<ImageModel> ImageList;



    public void setImageList(List<ImageModel> imageList) {
        this.ImageList = imageList;
    }


    public ImageShowAdapter(Context mContext, List<ImageModel> tagList) {
        this.mContext = mContext;
        this.ImageList = tagList;
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
        final View view = LayoutInflater.from(mContext).inflate(R.layout.image_show_item, parent, false);//导入卡片视图
        ViewHolder viewHolder = new ViewHolder(view);
        TextView tagName = (TextView) view.findViewById(R.id.tag_name_item);
        ImageView imageView = (ImageView)view.findViewById(R.id.tag_image_showd_item);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击显示大图对话框

            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
                //TODO 长按可以编辑文件（删除，转移到别的标签）
            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(ImageList.get(position).getImageName());
        holder.imageView.setImageBitmap(ImageList.get(position).getImage());
        holder.postion=position;
    }

    @Override
    public int getItemCount() {
        return ImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        ImageView imageView;
        int postion;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView tagName = (TextView) itemView.findViewById(R.id.tag_name_item);
            ImageView imageView = (ImageView)itemView.findViewById(R.id.tag_image_showd_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 点击后添加到对应的标签中
                }
            });


        }
        //TODO 自建holder类
    }
}
