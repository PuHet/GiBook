package com.test.gibook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostingAdapter extends BaseAdapter {
    private ArrayList<Posting> postings = new ArrayList<>();

    //생성자
    public PostingAdapter() {

    }

    @Override
    public int getCount() {
        return postings.size();
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
       final int i = position;
        Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView statusView = (TextView) view.findViewById(R.id.status);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView dateView = (TextView) view.findViewById(R.id.date);

        Posting posting = (Posting) getItem(i);


        titleView.setText(posting.Title);
        statusView.setText(posting.Status);
        nameView.setText(posting.Name);
        dateView.setText(posting.Date);

        //view.setOnClickListener();



        return view;
    }
    @Override
    public Object getItem(int i) {

        return postings.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }


    public void addItem(Posting posting) {
        postings.add(posting);
    }
}
