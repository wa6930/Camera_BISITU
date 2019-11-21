package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.Model;

import android.graphics.Bitmap;
import android.media.Image;

public class ImageModel {
    Bitmap image;
    String imageName;
    String imagePath;

    public ImageModel(Bitmap image, String imageName,String imagePath) {
        this.image = image;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
