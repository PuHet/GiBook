package com.test.gibook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Lobby extends AppCompatActivity {
    private Button up_btn1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private ChildEventListener mChild;

    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);
        Posting posting = new Posting();
        //FireBase 실시간 DB 관리 얻어오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //저장시킬 노드 참조객체 가져오기
        DatabaseReference myRef = firebaseDatabase.getReference(); //()안에 아무것도 안쓰면 최상위 노드

        final ArrayList<Posting> postings = new ArrayList<>();
        PostingAdapter PostingAdapter = new PostingAdapter();
        ListView mListView = (ListView) findViewById(R.id.lobby_list);

        ArrayAdapter<Posting> adapter = new ArrayAdapter(this,R.layout.list_item,postings);
        mListView.setAdapter(PostingAdapter);


        //리스트 아이템 클릭시 작동 (작성)
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ListContents.class);

                intent.putExtra("Title", postings.get(position).Title);
                intent.putExtra("writer", postings.get(position).Name);
                intent.putExtra("image", postings.get(position).Images);
                intent.putExtra("contents", postings.get(position).Contents);
                intent.putExtra("status", postings.get(position).Status);
                intent.putExtra("date", postings.get(position).Date);
                intent.putExtra("password", postings.get(position).Password);


                startActivity(intent);
            }
        });//작성끝







        //글쓰기 버튼 클릭시
        up_btn1 = findViewById(R.id.up_btn1);
        up_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, To_Donation.class);
                startActivity(intent);

            }
        });

    }





}