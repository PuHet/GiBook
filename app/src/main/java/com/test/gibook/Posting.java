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
    public String Status;
    public String Date;
    public String Push;
    public String Image_Name;

    // 생성자
    public Posting(String name, String title, String password, String contents, String images, String status, String date, String push,String image_Name) {
        this.Name = name;
        this.Title = title;
        this.Password = password;
        this.Contents = contents;
        this.Images = images;
        this.Status = status;
        this.Date = date;
        this.Push = push;
        this.Image_Name = image_Name;
    }

    public Posting() {

    }
/*
    @Exclude
    public Map<String, Object> result() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("기부완료", this.Status);
        return result;
    }

 */


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
