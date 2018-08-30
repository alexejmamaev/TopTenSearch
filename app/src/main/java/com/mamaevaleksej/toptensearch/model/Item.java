package com.mamaevaleksej.toptensearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

// Класс используется одновременно как Retrofit 2 POJO и Room DB @Entity
@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id; // Room entity
    @SerializedName("title")
    private String title; // Room entity
    @SerializedName("link")
    private String link; // Room entity
    @SerializedName("snippet")
    private String snippet; // Room entity

    @Ignore
    public Item(String title, String link, String snippet) {
        this.title = title;
        this.link = link;
        this.snippet = snippet;
    }

    public Item (int id, String title, String link, String snippet) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.snippet = snippet;
    }

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

}
