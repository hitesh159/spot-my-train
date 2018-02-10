package com.example.hitesh.spotmytrain;

import android.graphics.drawable.Drawable;

/**
 * Created by hitesh on 2/2/2018.
 */

public class Options {
    int image;
    String description;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Options(int image, String description) {
        this.image = image;
        this.description = description;
    }
}
