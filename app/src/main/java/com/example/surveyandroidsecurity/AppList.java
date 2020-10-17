package com.example.surveyandroidsecurity;

import android.graphics.drawable.Drawable;

public class  AppList {
    private String name;
    Drawable icon;
    Drawable lock_icon;
    private String packaganame;
    private String criticidad;
    public void setName(String name) {
        this.name = name;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    public void setLock_icon(Drawable lock_icon) {
        this.icon = lock_icon;
    }
    public void setPackaganame(String packaganame) {
        this.packaganame = packaganame;
    }
    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }
    public AppList() {
    }
    public AppList(String name, Drawable icon, String criticidad, String packaganame, Drawable lock_icon) {
        this.name = name;
        this.icon = icon;
        this.packaganame=packaganame;
        this.criticidad = criticidad;
        this.lock_icon=lock_icon;
    }
    public AppList(String Packagename, String criticidad) {
        this.packaganame = Packagename;
        this.icon = icon;
        this.criticidad = criticidad;
    }

    public String getName() {
        return name;
    }
    public Drawable getIcon() {
        return icon;
    }
    public Drawable getLock_icon() {
        return lock_icon;
    }
    public String getCriticidad() {
        return criticidad;
    }
    public String getPackaganame() {
        return packaganame;
    }
}
