package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.MainActivity;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.Model.ImageModel;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.R;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.FileControl;
import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.tools.TagChangedTools;

import java.io.File;
import java.util.List;

import static com.example.erjike.bistu.music.plaer2.by.camera_bisitu.ShowAllImageInTag.TAG;

/**
 * 用于显示图片，从而完成之后的细致操作。
 *
 */
public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder> {

    private Context mContext;
    private List<ImageModel> imageList;



    public void setImageList(List<ImageModel> imageList) {
        this.imageList = imageList;
    }


    public ImageShowAdapter(Context mContext, List<ImageModel> tagList) {
        this.mContext = mContext;
        this.imageList = tagList;
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




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(imageList.get(position).getImageName());
        holder.imageView.setImageBitmap(imageList.get(position).getImage());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        ImageView imageView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = (TextView)itemView.findViewById(R.id.tag_name_item);
            imageView = (ImageView)itemView.findViewById(R.id.tag_image_showd_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 点击显示大图对话框

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu menu = new PopupMenu(mContext,v);
                    menu.getMenuInflater().inflate(R.menu.image_long_click,menu.getMenu());
                    menu.show();
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.delete_this_imge:
                                    //删除该图片
                                    FileControl.deleteImage(imageList.get(position).getImagePath());
                                    imageList.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(mContext,"删除图片成功！",Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.move_to_other_tag:
                                    List<File> fileList =FileControl.getFolder(mContext.getExternalCacheDir());
                                    AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(mContext);
                                    dialogBuilder.setTitle("转移标签");
                                    LayoutInflater layoutInflater= ((Activity)mContext).getLayoutInflater();
                                    View dialogeView=layoutInflater.inflate(R.layout.dialog_move_to_tag,null);
                                    dialogBuilder.setView(dialogeView);
                                    RecyclerView recyclerView = (RecyclerView) dialogeView.findViewById(R.id.dialog_move);
                                    File file = new File(imageList.get(position).getImagePath());
                                    ImageMoveAdapter adapter = new ImageMoveAdapter(mContext,fileList,file);
                                    recyclerView.setAdapter(adapter);
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                    //TODO 设计adapter
                                    dialogBuilder.setNegativeButton("完成", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //不进行任何操作
                                        }
                                    });
                                    dialogBuilder.show();

                                    //TODO
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
