package com.example.erjike.bistu.music.plaer2.by.camera_bisitu.Model;

import android.media.Image;

public class ImageModel {
    Image image;
    String imageName;

    public ImageModel(Image image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }

    public Image getImage() {
        return image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
