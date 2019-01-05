package com.example.ideath.todoshka.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ideath.todoshka.Other.Note;
import com.example.ideath.todoshka.R;

import java.util.List;

public class TodosAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater layoutInflater;
    List<Note> objects;

    public TodosAdapter(Context context, List<Note> notesList){
        ctx = context;
        objects = notesList;
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.tood_list_adapter,parent,false);
        }
            Note n = getNote(position);
            ((TextView) view.findViewById(R.id.thema)).setText(n.getThema());
            if(n.isChecken()) {
                ((TextView) view.findViewById(R.id.date)).setText(n.getDate());
                ((TextView) view.findViewById(R.id.time)).setText(n.getTime());
            }
        return view;
        }

       public Note getNote(int position){
            return  (Note) getItem(position);
       }
}
