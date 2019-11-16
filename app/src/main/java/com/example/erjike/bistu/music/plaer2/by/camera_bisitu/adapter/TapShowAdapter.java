package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.R;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.ShowAllImageInTag;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.TagChangedTools;

import java.util.List;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 读取对应文件夹，进入对应目录
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
            tagName = (TextView) itemView.findViewById(R.id.main_tag_tag_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 点击后用intent跳转到显示页面
                    Intent intent = new Intent(mContext, ShowAllImageInTag.class);
                    intent.putExtra("foldName",tagName.getText().toString());
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
                                    //TODO 重命名文件夹，删除标签，新建一个新的名字的标签
                                    break;
                                case R.id.delete_tag:
                                    TagChangedTools.deleteTag(mContext,postion);
                                    tagList.remove(postion);
                                    notifyDataSetChanged();
                                    //TODO 删除文件夹与内部所有文件
                                    break;
                                case R.id.share_tag:

                                    //TODO 实现文件夹压缩
                                    //TODO 调用微信分享接口，或者qq分享接口，或者其他相应分享接口
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
