package com.test.gibook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.PeriodicSync;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class To_Donation extends AppCompatActivity {

    private Button up_btn; //등록 버튼
    private Button re_btn; //메인으로 버튼
    private EditText Donation_title;
    private EditText Donation_name;
    private EditText Donation_password;
    private EditText Donation_contents;
    private ImageView Donation_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to__donation);

        //시간 출력 설정
        TimeZone tz = TimeZone.getTimeZone ("Asia/Seoul");
        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA);
        //시간 지역 설정
        sdfNow.setTimeZone(tz);
        // nowDate 변수에 값을 저장한다.
        final String formatDate = sdfNow.format(date);

        Donation_title = (EditText) findViewById(R.id.Donation_title);
        Donation_name = findViewById(R.id.Donation_name);
        Donation_password = findViewById(R.id.Donation_password);
        Donation_contents = findViewById(R.id.Donation_contents);
        Donation_image = findViewById(R.id.Donation_image);

        up_btn = findViewById(R.id.up_btn);
        up_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //FirebaseStorage 레퍼런스 생성
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                //Storage 경로 설정
                final StorageReference imageReference = storageReference.child("images/" + UUID.randomUUID().toString());
                final String Images = UUID.randomUUID().toString();
                Bitmap bitmap = ((BitmapDrawable) Donation_image.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageArr = baos.toByteArray();

                // 이미지 업로드
                final UploadTask uploadTask = imageReference.putBytes(imageArr);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast toast = Toast.makeText(getApplicationContext(), "업로드가 실패하였습니다.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //FireBase 실시간 DB 관리 얻어오기
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                //저장시킬 노드 참조객체 가져오기
                                DatabaseReference myRef = firebaseDatabase.getReference(); //()안에 아무것도 안쓰면 최상위 노드
                                //별도의 키 없이 값(value)만 저장하기
                                String downloadUrl = uri.toString();
                                Posting posting = new Posting();
                                //PUSH값으로 노드 만들기
                                String key1 = myRef.push().getKey();
                                //EditText에 있는 문자 얻어오기
                                posting.Title = Donation_title.getText().toString();
                                posting.Name = Donation_name.getText().toString();
                                posting.Password = Donation_password.getText().toString();
                                posting.Contents = Donation_contents.getText().toString();
                                posting.Images = downloadUrl;
                                posting.Date  = formatDate;
                                posting.Status  = "기부중)";
                                posting.Push  = key1;
                                posting.Image_Name  = Images;
                                //키값으로 게시글 작성 등록
                                myRef.child(key1).setValue(posting);

                                Intent intent = new Intent(To_Donation.this, Lobby.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }


        });

        //메인으로 버튼 동작
        re_btn = findViewById(R.id.re_btn);
        re_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(To_Donation.this, Lobby.class);
                startActivity(intent);

            }
        });

    }//메인 괄호

    //카메라 버튼 동작
    public void showCameraBtn(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");
            Donation_image.setImageBitmap(bitmap);
        }
    }
}