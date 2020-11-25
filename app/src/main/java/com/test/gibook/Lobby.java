package com.test.gibook;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Lobby extends AppCompatActivity {
    private Button up_btn1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private ChildEventListener mChild;
    private ListView mListView;
    private PostingAdapter adapter;
    private ImageView imageView;

    private void addListView() {
        ref.orderByKey().limitToLast(30).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Posting posting = ds.getValue(Posting.class);
                    adapter.addItem(posting);
                }
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby);
        //FireBase 실시간 DB 관리 얻어오기
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        //저장시킬 노드 참조객체 가져오기
        ref = firebaseDatabase.getReference(); //()안에 아무것도 안쓰면 최상위 노드

        adapter = new PostingAdapter(mListView);
        mListView = (ListView) findViewById(R.id.lobby_list);
        mListView.setAdapter(adapter);
        addListView();

        //리스트 아이템 클릭시 작동 (작성)
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ListContents.class);

                Posting posting = (Posting) mListView.getItemAtPosition(position);
                intent.putExtra("Title", posting.Title);
                intent.putExtra("writer", posting.Name);
                intent.putExtra("image", posting.Images);
                intent.putExtra("contents", posting.Contents);
                intent.putExtra("status", posting.Status);
                intent.putExtra("date", posting.Date);
                intent.putExtra("password", posting.Password);
                intent.putExtra("Position",posting.Push);
                intent.putExtra("Image_Name",posting.Image_Name);

                startActivity(intent);
            }
        });

        //글쓰기 버튼 클릭시
        up_btn1 = findViewById(R.id.up_btn1);
        up_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, To_Donation.class);
                startActivity(intent);

            }
        });

        //메인 버튼 클릭시
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, MainActivity.class);
                startActivity(intent);

            }
        });


        final EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;

        editTextFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editTextFilter.getText().toString().toLowerCase(Locale.getDefault());
               // adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });







    }//메인 괄호





}