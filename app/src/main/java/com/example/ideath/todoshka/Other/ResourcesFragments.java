package com.example.ideath.todoshka.Other;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ideath.todoshka.R;
import com.example.ideath.todoshka.activity.CreateTodoActivity;
import com.example.ideath.todoshka.adapter.TodosAdapter;

import java.util.List;

public class ResourcesFragments {

    private Context context;
    private SQLBases db;
    private TodosAdapter todosAdapter;
    private ListView list;
    private TextView messages;

    public ResourcesFragments(Context context) {
        this.context = context;

    }
    public  View resources(boolean Isfragments, LayoutInflater inflater, ViewGroup viewGroup){

        FrameLayout view = (FrameLayout) inflater.inflate(Isfragments ? R.layout.fragment_check : R.layout.fragment_top, viewGroup, false);

        list = view.findViewById(Isfragments ? R.id.listC : R.id.listT);
        messages = (TextView) view.findViewById(Isfragments ? R.id.sizeC : R.id.sizeT);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = todosAdapter.getNote(position);
                if(note != null){
                    Intent intent = new Intent(inflater.getContext(),CreateTodoActivity.class);
                    intent.putExtra("id",note.getId());
                    inflater.getContext().startActivity(intent);
                }
            }
        });
       list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               Note note = todosAdapter.getNote(position);
               if(note != null){
                   Intent intent = new Intent(Intent.ACTION_SEND);
                   intent.setType("text/plain");
                   if(note.isChecken()) {
                       intent.putExtra(Intent.EXTRA_TEXT,
                               inflater.getContext().getString(R.string.ReminderTodo)
                                       + ": " + note.getDate() + "\t"+Html.fromHtml("&#9830;")
                                       +"\t"+ note.getTime()+"\r\n"
                                       +inflater.getContext().getString(R.string.MyThema)+": "+ note.getThema());
                        message(intent);
                   }else {
                       intent.putExtra(Intent.EXTRA_TEXT,
                               inflater.getContext().getString(R.string.NoteOne) +
                                       ": "+inflater.getContext().getString(R.string.MyThema)
                                      +": " + note.getThema());
                       message(intent);
                   }
                   return true;
               }
               return false;
           }
       });
            return view;
    }

    private void message(Intent intent){
        String chooserTitle = context.getString(R.string.ReminderTodo);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        context.startActivity(chosenIntent);
    }


    public  void resume(boolean isChecks){
        db = new SQLBases(context);
        List<Note> notes = null;
        if(isChecks) {
            notes = db.getAllTodo(1);
            getCount(1,false);
        }else {
            notes = db.getAllTodo(0);
           getCount(0,true);
        }
        todosAdapter = new TodosAdapter(context,notes);
        list.setAdapter(todosAdapter);
    }

    @NonNull
    private void getCount(int i,boolean isText){
        if(db.getTodosCount(i) == 0){
            messages.setText(isText ? R.string.Notes : R.string.Reminders);
        }else if(db.getTodosCount(i) > 0){
            messages.setText(null);
        }
    }
}
