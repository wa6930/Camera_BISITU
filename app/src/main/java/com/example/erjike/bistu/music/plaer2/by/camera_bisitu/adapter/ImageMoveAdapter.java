package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erjike.bistu.music.plaer2.by.camera_bisitu.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 用于显示在dialog界面，用于选择图片的添加位置。
 */
public class ImageMoveAdapter extends RecyclerView.Adapter<ImageMoveAdapter.ViewHolder> {

    private Context mContext;
    private List<File> fileList;
    private File file;


    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }


    public ImageMoveAdapter(Context mContext, List<File> tagList, File file) {
        this.mContext = mContext;
        this.fileList = tagList;
        this.file = file;
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

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(fileList.get(position).getName());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = (TextView) itemView.findViewById(R.id.item_tagName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File newfile = fileList.get(position);
                    String fileName = file.getName();
                    File outFile = new File(newfile.getPath()+"/"+fileName);
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file.getPath());
                        FileOutputStream fileOutputStream = new FileOutputStream(outFile.getPath());
                        byte[] buffer = new byte[1024];
                        int byteRead;
                        while(-1 !=(byteRead = fileInputStream.read(buffer))){
                            fileOutputStream.write(buffer,0,byteRead);
                        }
                        fileInputStream.close();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Toast.makeText(mContext,"文件转移成功！",Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
