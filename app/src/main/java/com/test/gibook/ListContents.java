package com.test.gibook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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

    private String password;
    private String img;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcontents);

        Intent intent = getIntent();

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
        contents_date.setText(intent.getStringExtra("date")); // 날짜 형식을 yyyy/mm/dd로 바꿔야함
        contents_status.setText(intent.getStringExtra("status"));
        final String password = (intent.getStringExtra("password"));



        //메인으로 버튼 클릭시
        lobby_btn1 = findViewById(R.id.lobby_btn1);
        lobby_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListContents.this, Lobby.class);
                startActivity(intent);

            }
        });
        //비밀번호 일치시 버튼 활성화
        sold_out_btn = findViewById(R.id.sold_out_btn);
        sold_out_btn.setEnabled(false);
        //비활성화시 버튼색깔 회색으로 할 예정
        if(contents_Password == null) {
            if (password.equals(contents_Password)) {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                sold_out_btn.setEnabled(true);
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                sold_out_btn.setEnabled(false);

            }
        }
        //활성화된 기부완료 버튼 클릭시
        sold_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contents_status.setText("기부완료");
                contents_status.setTextColor(0xB2B2B2B2); //회색
            }
        });//작성끝

    }//메인 괄호
}