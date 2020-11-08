package com.test.gibook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.PeriodicSync;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import java.io.InputStream;
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
    private Button file_btn; //파일 불러오기 버튼
    private EditText Donation_title;
    private EditText Donation_name;
    private EditText Donation_password;
    private EditText Donation_contents;
    private ImageView Donation_image;
    private ImageView imageView; //기북 타이틀
    private final int GET_GALLERY_IMAGE = 200;
    final String Title = "";
    final String Name = "";
    final String Contents = "";
    final String Password = "";
    final String Image = "";
    String i = "0";
    Bitmap bitmap = null;
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
        /*
        //이미지뷰 숨김
        if (Donation_image.getDrawable() == null) {
            Donation_image.setVisibility(View.GONE);
        } else if (Donation_image.getDrawable() != null) {
            Donation_image.setVisibility(View.VISIBLE);
        }
         */
        up_btn = findViewById(R.id.up_btn);
                        up_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              final Posting posting = new Posting();
                              String Title = Donation_title.getText().toString(); //각 게시글 정보의 길이값을 구하기 위한 작업
                              String Name = Donation_name.getText().toString();
                              String Contents = Donation_contents.getText().toString();
                              String Password = Donation_password.getText().toString();
                                //각 게시글정보의 길이값으로 입력 유무 비교
                                if ( Title.getBytes().length > 0  &&  Name.getBytes().length > 0 &&  Contents.getBytes().length > 0 &&  Password.getBytes().length > 0) {
                                    //FirebaseStorage 레퍼런스 생성
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference storageReference = storage.getReference();
                                        //사진 유무 확인
                                    if (Donation_image.getDrawable() != null) {
                                        final String Images1 = UUID.randomUUID().toString();
                                        //Storage 경로 설정
                                        final StorageReference imageReference = storageReference.child("images/" + Images1);
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
                                                        //PUSH값으로 노드 만들기
                                                        String key1 = myRef.push().getKey();
                                                        //EditText에 있는 문자 얻어오기
                                                        posting.Title = Donation_title.getText().toString();
                                                        posting.Name = Donation_name.getText().toString();
                                                        posting.Password = Donation_password.getText().toString();
                                                        posting.Contents = Donation_contents.getText().toString();
                                                        posting.Images = downloadUrl;
                                                        posting.Date = formatDate;
                                                        posting.Status = "기부중)";
                                                        posting.Push = key1;
                                                        posting.Image_Name = Images1;
                                                        //키값으로 게시글 작성 등록
                                                        myRef.child(key1).setValue(posting);
                                                        Intent intent = new Intent(To_Donation.this, Lobby.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                        });

                                    } else //사진 없으면 실행
                                    {
                                        AlertDialog.Builder dlg = new AlertDialog.Builder(To_Donation.this);
                                        dlg.setTitle("사진 첨부는 필수 입니다.."); //제목
                                        dlg.setMessage("사진을 첨부해주세요."); // 메시지
                                        //버튼 클릭시 동작
                                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        dlg.show();
                                    }
                                }
                                if ( Title.getBytes().length <= 0  ||  Name.getBytes().length <= 0 ||  Contents.getBytes().length <= 0 ||  Password.getBytes().length <= 0 || Donation_image == null){
                                    //빈칸 있을시
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(To_Donation.this);
                                    dlg.setTitle("입력하지 않은 정보가 있습니다."); //제목
                                    dlg.setMessage("모든 정보를 입력해 주세요."); // 메시지
                                    //버튼 클릭시 동작
                                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    dlg.show();
                                }
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

        //메인 버튼 클릭시
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(To_Donation.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // 파일 불러오기
        file_btn = (Button)findViewById(R.id.file_btn);
        file_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = "1";
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }//메인 괄호

    // 파일 불러오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(i.equals("1")) {
            if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Donation_image.setImageResource(0);
                Uri selectedImageUri = data.getData();
                Donation_image.setImageURI(selectedImageUri);
            }
        }
            if(i.equals("2")){
            if (resultCode == RESULT_OK) {
                Donation_image.setImageResource(0);
                Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");
                Donation_image.setImageBitmap(bitmap);
            }
            }
    }

    //카메라 버튼 동작
    public void showCameraBtn(View view) {
        i = "2";
        Donation_image.setImageResource(0);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
   }