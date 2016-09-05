package com.yellowsoft.subhankar.devcomic;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subhankar on 9/5/2016.
 */
public class Comic {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("cartoon_jpg")
    private String cartoon_jpg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCartoon_jpg() {
        return cartoon_jpg;
    }

    public void setCartoon_jpg(String cartoon_jpg) {
        this.cartoon_jpg = cartoon_jpg;
    }
}
