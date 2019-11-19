package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.Model;

import android.graphics.Bitmap;
import android.media.Image;

public class ImageModel {
    Bitmap image;
    String imageName;

    public ImageModel(Bitmap image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
