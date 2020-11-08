package com.test.gibook;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.CaseMap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ListContents extends AppCompatActivity {

    private Button lobby_btn1;
    private Button sold_out_btn;
    private TextView contents_writer;
    private TextView contents_title;
    private TextView contents_contents;
    private ImageView contents_images;
    private TextView contents_date;
    private TextView contents_status;
    private EditText contents_Password;
    private String now1;
    private String password;
    private String ETpassword;
    private String img;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView imageView;
    private void setImage(final ImageView iv, final String url) {
        new ThreadTask<String, Bitmap>() {
            @Override
            protected void onPreExecute() {
            }
            @Override
            protected Bitmap doInBackground(String arg) {
                Bitmap image = null;
                try {
                    InputStream in = new java.net.URL(arg).openStream();
                    image = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return image;
            }
            @Override
            protected void onPostExecute(Bitmap result) {
                iv.setImageBitmap(result);
            }
        }.execute(url);
    }
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontents);
        Intent intent = getIntent();
        final Posting posting = new Posting();
        contents_writer = findViewById(R.id.contents_writer );
        contents_title = findViewById(R.id.contents_title );
        contents_contents = findViewById(R.id.contents_contents );
        contents_images= findViewById(R.id.contents_images);
        contents_date= findViewById(R.id.contents_date);
        contents_status= findViewById(R.id.contents_status);
        contents_Password = findViewById(R.id.contents_Password);
        //인텐트 이미지 URL로 불러오기(작성)
        img = intent.getStringExtra("image");
        //인텐트 DATA 띄워주기
        setImage((ImageView) findViewById(R.id.contents_images), img);
        contents_writer.setText(intent.getStringExtra("writer"));
        contents_title.setText(intent.getStringExtra("Title"));
        contents_contents.setText(intent.getStringExtra("contents"));
        //인텐트로 시간 받아옴
        now1 = intent.getStringExtra("date");
        Date date2 = new Date(now1);
        // 정해준 형식으로 date1 변수에 저장한다.
        SimpleDateFormat date1 = new SimpleDateFormat("yyyy/MM/dd");
        date1.format(date2);
        //TextView에 날짜 삽입
        contents_date.setText(date1.format(date2));
       // 날짜 형식을 yyyy/mm/dd로 바꿔야함 (성공)
        contents_status.setText(intent.getStringExtra("status"));
        final String password = (intent.getStringExtra("password"));
        final String positionToRemove = (intent.getStringExtra("Position"));
        final String Image_Name = (intent.getStringExtra("Image_Name"));
        //FireBase 실시간 DB 관리 얻어오기
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //저장시킬 노드 참조객체 가져오기
        final DatabaseReference myRef = firebaseDatabase.getReference(); //()안에 아무것도 안쓰면 최상위 노드
        //FirebaseStorage 레퍼런스 생성
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageReference = storage.getReference().child("images/"+Image_Name);
        //메인으로 버튼 클릭시
        lobby_btn1 = findViewById(R.id.lobby_btn1);
        lobby_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContents.this, Lobby.class);
                startActivity(intent);
            }
        });
        //비밀번호 버튼 비활성화
        sold_out_btn = findViewById(R.id.sold_out_btn);
        sold_out_btn.setEnabled(false);
        //SwipeRefreshLayout 새로고침 기능
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (contents_Password != null) {
                    ETpassword = contents_Password.getText().toString();
                    if (password.equals(ETpassword)) {
                        //기부완료 버튼 변경
                        sold_out_btn.setBackgroundResource(R.drawable.buttonshape);
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                        sold_out_btn.setEnabled(true);
                        //활성화된 기부완료 버튼 클릭시
                        sold_out_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //기부완료 활성화 기능
                                /*
                                Map<String, Object> result = new HashMap<String, Object>();
                                myRef.updateChildren(result);
                                contents_status.setText("기부완료");
                                contents_status.setTextColor(0xB2B2B2B2);
                                sold_out_btn.setEnabled(false);//버튼 비활성화
                                 */

                                //활성화된 기부완료 버튼 클릭시
                                AlertDialog.Builder dlg = new AlertDialog.Builder(ListContents.this);
                                dlg.setTitle("기부해주셔서 감사합니다."); //제목
                                dlg.setMessage("기부완료시 이 게시글은 삭제 됩니다."); // 메시지
                                //버튼 클릭시 동작
                                dlg.setPositiveButton("삭제",new DialogInterface.OnClickListener(){
                                            public void onClick(DialogInterface dialog, int which) {
                                                //토스트 메시지
                                                Toast.makeText(ListContents.this,"게시글이 삭제됩니다.",Toast.LENGTH_SHORT).show();
                                                //스토리지 사진 삭제 구문
                                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                   @Override
                                                    public void onSuccess(Void aVoid) {
                                                       Toast.makeText(ListContents.this,"게시글이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception exception) {
                                                        Toast.makeText(ListContents.this,"게시글에 삭제에 실패했습니다.",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                myRef.child(positionToRemove).removeValue();
                                                Intent intent = new Intent(ListContents.this, Lobby.class);
                                                startActivity(intent);
                                            }
                                        });
                                        dlg.show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                        sold_out_btn.setEnabled(false);
                    }

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //메인 버튼 클릭시
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContents.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }//메인 괄호
}
