package com.example.ideath.todoshka.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ideath.todoshka.Other.Note;
import com.example.ideath.todoshka.Other.SQLBases;
import com.example.ideath.todoshka.R;
import com.example.ideath.todoshka.Service.MyAlarmReceiver;

import java.util.Calendar;
import java.util.Locale;

public class CreateTodoActivity extends AppCompatActivity {

    private long todoId;
    private long timeInMillis;
    private int hr,min,yr,mh,ds;
    private EditText textThema;
    private EditText textDescription;
    private Switch aCheck;
    private TextView textDate;
    private TextView textTime;
    private SQLBases db;
    private boolean bound;
    private boolean boundTwo;
    private boolean isOne;
    private Calendar calendar = Calendar.getInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState != null){
            hr = savedInstanceState.getInt("hr");
            min = savedInstanceState.getInt("min");
            yr = savedInstanceState.getInt("yr");
            mh = savedInstanceState.getInt("mh");
            ds = savedInstanceState.getInt("ds");
            bound = savedInstanceState.getBoolean("bound");
            boundTwo = savedInstanceState.getBoolean("boundTwo");
        }
        init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hr",hr);
        outState.putInt("min",min);
        outState.putInt("yr",yr);
        outState.putInt("mh",mh);
        outState.putInt("ds",ds);
        outState.putBoolean("bound",bound);
        outState.putBoolean("boundTwo",boundTwo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu item){
        getMenuInflater().inflate(R.menu.menu_delete,item);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            item.removeItem(R.id.action_delete);
        }
        return super.onCreateOptionsMenu(item);
    }

    private void init(Bundle savedInstanceState){
        textThema = (EditText) findViewById(R.id.textThema);
        textDescription = (EditText) findViewById(R.id.textDescription);
        aCheck = (Switch) findViewById(R.id.checken);
        textDate = (TextView) findViewById(R.id.textDate);
        textTime = (TextView) findViewById(R.id.textTime);

        db = new SQLBases(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            setTitle(R.string.EditTodo);
            todoId = extras.getLong("id");

        }else {
            setTitle(R.string.CreateTodo);
            if(savedInstanceState == null) {
                yr = calendar.get(Calendar.YEAR);
                mh = calendar.get(Calendar.MONTH);
                ds = calendar.get(Calendar.DAY_OF_MONTH);
                hr = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
            }
            setText(0,yr,mh,ds,hr,min);
        }
        if(todoId > 0) {
            Note note = db.getTodo(todoId);
            textThema.setText(note.getThema());
            textDescription.setText(note.getDescription());
            aCheck.setChecked(note.isChecken());

            isOne = note.isChecken() ? true : false;
            textDate.setText(note.getDate());
            textTime.setText(note.getTime());
            yr = note.getYear();
            mh = note.getMonth();
            ds = note.getDay();
            hr = note.getHours();
            min = note.getMinute();
            timeInMillis = note.getTimeInMillis();
        }
        onColor();
    }

    private void onColor(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(!bound && boundTwo) {
                    textColor();
                }else if(!bound && isOne && !boundTwo){
                    textColor();
                }else if(bound && !boundTwo ){
                      textColor();
                }else if(!bound && !isOne && !boundTwo){
                      textColor();
                }else {
                      textColor();
                }
                handler.postDelayed(this,100);
            }
        });
    }

    private void textColor(){
        if(!bound && isOne && !boundTwo  || !bound && boundTwo){
            textDate.setTextColor(Color.RED);
            textTime.setTextColor(Color.RED);
        }else if(bound && !boundTwo || !bound && !isOne && !boundTwo){
            textDate.setTextColor(Color.WHITE);
            textTime.setTextColor(Color.WHITE);
        }
    }
    public void onCheckes(View view) {
        if(aCheck.isChecked()){
            bound = false;
            boundTwo = true;
        }else {
            bound = true;
            boundTwo =false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.Message));
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage(getString(R.string.messageDelete));
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Note note = db.getTodo(todoId);
                    if(note.isChecken()) {
                        alarmCancel(note);
                    }
                    db.deleteTodo(todoId);
                    goHome();
                }
            });
            builder.setNegativeButton(getString(R.string.no),null);
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDatePicker(View view) {
        DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                yr = calendar.get(Calendar.YEAR);
                mh = calendar.get(Calendar.MONTH);
                ds = calendar.get(Calendar.DAY_OF_MONTH);
                setText(1,yr,mh,ds,hr,min);
            }
        };
        DatePickerDialog dp =  new DatePickerDialog(
                CreateTodoActivity.this, d,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    public void onTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                hr = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
                setText(2,yr,mh,ds,hr,min);
            }
        };

        TimePickerDialog tp =  new TimePickerDialog(CreateTodoActivity.this,time ,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        tp.show();
    }

    private void setText(int i,int yr,int mh,int ds,int hr,int min){
        String nuls = "0";
        String nuls2 = "0";
        String nuls3 = "0";
        String nuls4 = "0";
        mh++;

        if(mh >= 10) {
            nuls = "";
        }
        if(ds >= 10){
            nuls2 = "";
        }
        if(hr >= 10){
            nuls3 = "";
        }
        if(min >= 10){
            nuls4 = "";
        }
        String formatDate = nuls2+""+ ds + "." + nuls + "" + mh + "." + yr;
        String formatTime = nuls3+""+hr + ":" +  nuls4 + "" + min;

        if(i == 1) {
            textDate.setText(formatDate);
        }else if(i == 2) {
            textTime.setText(formatTime);
        }else if(i == 0) {
            textDate.setText(formatDate);
            textTime.setText(formatTime);
        }
    }

    public void OnClickAds(View view) {
        String thema = textThema.getText().toString();
        String description = textDescription.getText().toString();
        boolean isChecked = aCheck.isChecked();
        String date = textDate.getText().toString();
        String time = textTime.getText().toString();
        int years = yr;
        int month = mh;
        int days = ds;
        int hours = hr;
        int minute = min;

        calendar.set(Calendar.YEAR,years);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,days);
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        timeInMillis = calendar.getTimeInMillis();

        if(timeInMillis >= System.currentTimeMillis() || !isChecked ){
            if(thema.trim().length() >= 1 && thema.trim().length() <= 20 ) {
                Note note = new Note(todoId, thema, description, isChecked, date, time,
                        years, month, days, hours, minute, timeInMillis);

                if (todoId > 0) {
                    db.updateTodo(note);
                } else {
                    db.insertTodo(note);
                }
                goService(note);
                goHome();
            }else {
                createBuilder(getString(R.string.dialogThema));
            }
       }else {
          createBuilder(getString(R.string.dialogTime));
       }
    }

    private AlertDialog.Builder createBuilder(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Message));
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(message);
        builder.setPositiveButton("OK",null);
        builder.show();
       return builder;
    }

    private void goService(Note note){
        if(note.isChecken()) {
            Intent intent = new Intent(this, MyAlarmReceiver.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            intent.putExtra("date", note.getDate());
            intent.putExtra("time",note.getTime());
            intent.putExtra("thema",note.getThema());
            intent.putExtra("id",note.getId());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(CreateTodoActivity.this,
                    (int)note.getId(), intent, PendingIntent.FLAG_ONE_SHOT);

            alarmManager.set(AlarmManager.RTC_WAKEUP, note.getTimeInMillis(), pendingIntent);
        }else  {
            alarmCancel(note);
        }

    }

    private void alarmCancel(Note note){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CreateTodoActivity.this,
                (int)note.getId(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private  void goHome(){
        Intent intent = new Intent(CreateTodoActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
