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
    private ChildEventListener  mChild;
    ArrayList<Posting> DataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);

        //이쪽은 일시적 오류때문에 주석처리해놓음
/*
        ListView mListView = (ListView) findViewById(R.id.lobby_list);
        final ArrayAdapter adapter = new ArrayAdapter<Posting>(this,R.layout.list_item,DataList);


        //리스트 아이템 클릭시 작동 (작성)
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ListContents.class);

                intent.putExtra("Title", DataList.get(position).getTitle());
                intent.putExtra("writer", DataList.get(position).getName());
                intent.putExtra("image", DataList.get(position).getImages());
                intent.putExtra("contents", DataList.get(position).getContents());
                intent.putExtra("status", DataList.get(position).getStatus());
                intent.putExtra("date", DataList.get(position).getDate());
                intent.putExtra("password", DataList.get(position).getPassword());
                startActivity(intent);
            }


        });

*/





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