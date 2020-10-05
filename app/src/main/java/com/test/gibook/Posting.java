package com.test.gibook;

import com.google.android.gms.common.api.internal.IStatusCallback;

public class Posting {
    public String Name;
    public String Password;
    public String Title;
    public String Contents;
    public String Images;
    public String Status;
    public String Date;

    // 생성자
    public Posting(String name, String title, String password, String contents, String images, String status, String date) {
        this.Name = name;
        this.Title = title;
        this.Password = password;
        this.Contents = contents;
        this.Images = images;
        this.Status = status;
        this.Date = date;
    }

    public Posting() {

    }

    //이쪽 부분 주석처리 하니까 DB로 업로드 잘됨
/*
    public String getTitle() {
        return this.Title;
    }
    public String getName() {
        return this.Name;
    }
    public String getDate() {
        return this.Date;
    }
    public String getStatus() {
        return this.Status;
    }
    public String getImages() {
        return this.Images;
    }
    public String getContents() {
        return this.Contents;
    }
    public String getPassword() {
        return this.Password;
    }

    */
}
