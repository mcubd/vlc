package com.a.acs2;


public class dmanager {

    String name;
    String email;
    int image;

    int pause;
    int dlt;

    public dmanager(String name, String email, int image,int pause,int dlt) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.pause = pause;
        this.dlt = dlt;
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

}
