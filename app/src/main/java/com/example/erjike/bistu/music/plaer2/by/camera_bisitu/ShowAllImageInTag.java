package com.example.erjike.bistu.music.plaer2.by.camera_bisitu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ShowAllImageInTag extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_image_in_tag);
        recyclerView=(RecyclerView)findViewById(R.id.show_all_image_recyclerView);


    }
}
