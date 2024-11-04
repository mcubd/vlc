package com.a.acs2;


public class playlist_mini {

    String name;
    String email;
    int image;
    int pause;
    int play;
    int down;
    int dlt;
    int done;
    int pauseImg0;
    String percent0;
    String clr;
    String active;
    int percenOnly;
    String msg, url, lecInt, id, lectitle;
    int backgroundColor;

    public playlist_mini(String name, String email, int image, int backgroundColor, String percent0, int percenOnly, String msg, String url, String clr, String active, String lecInt, String id, String lectitle ,int pauseImg0) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.backgroundColor = backgroundColor;
        this.percent0 = percent0;
        this.msg = msg;
        this.clr = clr;
        this.url = url;
        this.id = id;
        this.lectitle = lectitle;
        this.lecInt = lecInt;
        this.active = active;
        this.percenOnly = percenOnly;
        this.pauseImg0 = pauseImg0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecInt() {
        return lecInt;
    }

    public void setLecInt(String name) {
        this.lecInt = lecInt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public int getBackgroundColor01() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getPercent0() {
        return percent0;
    }

    public void setPercent0(String percent0) {
        this.percent0 = percent0;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setpercenOnly(int percenOnly) {
        this.percenOnly = percenOnly;
    }

    public int getpercenOnly() {
        return percenOnly;
    }

    public void setPauseImg0(int pauseImg0) {
        this.pauseImg0 = pauseImg0;
    }

    public int getPauseImg0() {
        return pauseImg0;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setClr(String clr) {
        this.clr = clr;
    }

    public String getClr() {
        return clr;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }

    public void setVidId(String id) {
        this.id = id;
    }

    public String getVidId() {
        return id;
    }


    public void setLectitle(String lectitle) {
        this.lectitle = lectitle;
    }

    public String getLectitle() {
        return lectitle;
    }

}

