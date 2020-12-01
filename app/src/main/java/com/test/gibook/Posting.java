package com.test.gibook;

import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Posting {
    public String Name;
    public String Password;
    public String Title;
    public String Contents;
    public String Images;
    public String Department;
    public String Date;
    public String Push;
    public String Image_Name;
    // 생성자
    public Posting(String name, String title, String password, String contents, String images, String department, String date, String push,String image_Name) {
        this.Name = name;
        this.Title = title;
        this.Password = password;
        this.Contents = contents;
        this.Images = images;
        this.Department = department;
        this.Date = date;
        this.Push = push;
        this.Image_Name = image_Name;
    }

    public Posting() {

    }





}
