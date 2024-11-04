package com.a.acs2;


public class Item {

    String name;
    String email;
    String imgnum;
    int image;
    int pause;
    int dlt;
    int done;
    float percent;
    private boolean hidden;



    public Item(String name, String email,float percent, int image, int pause,int dlt,boolean hidden,String imgnum) {
        this.name = name;
        this.email = email;
        this.imgnum = imgnum;
        this.image = image;
        this.pause = pause;
        this.dlt = dlt;
        this.done = done;
        this.percent = percent;
        this.hidden = hidden;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public float getPercent() {
        return percent;
    }
    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getPause() {
        return pause;
    }
    public void setPause(int pause) {
        this.pause = pause;
    }

    public int getDlt() {
        return dlt;
    }
    public void setDlt(int dlt) {
        this.dlt = dlt;
    }


    public int getDone() {
        return R.drawable.done;
    }
    public void setDone(int dlt) {
        this.done = dlt;
    }

    public String getimgnum() {return imgnum;}
    public void setimgnum(String imgnum) {this.imgnum = imgnum;}

    public boolean getHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
