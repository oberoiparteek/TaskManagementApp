package parteek.taskmanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import parteek.taskmanagementapp.model.Event;

/**
 * Created by parteek on 11/9/2017.
 */
public class AddNewEventActivity extends AppCompatActivity  implements  View.OnClickListener{
    private Button bt_add_new_event;
    private Button bt_select_date;
    private Button bt_select_time;
    private EditText et_title;
    private EditText et_description;
    private TextView tv_date;
    private TextView tv_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        bt_add_new_event=(Button) findViewById(R.id.button_add_new_event);
        bt_select_date=(Button) findViewById(R.id.button_select_date);
        bt_select_time=(Button) findViewById(R.id.button_select_time);
        et_title=(EditText) findViewById(R.id.entry_title);
        et_description=(EditText) findViewById(R.id.entry_description);
        tv_date=(TextView) findViewById(R.id.label_date);
        tv_time=(TextView) findViewById(R.id.label_time);

    }

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private boolean mTimeSelected=false;
    private boolean mDateSelected=false;

    private int eventYear;
    private int eventMonth;
    private int eventDay;
    private int eventHour;
    private int eventMinute;

    @Override
    public void onClick(View v) {

        if (v ==bt_select_date ) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            mDateSelected=true;
                            eventDay=dayOfMonth;
                            eventYear=year;
                            eventMonth=monthOfYear;

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == bt_select_time) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            tv_time.setText(hourOfDay + ":" + minute);
                            mTimeSelected=true;
                            eventHour=hourOfDay;
                            eventMinute=minute;
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if(v== bt_add_new_event){
            if(et_title.getText().equals("") || et_description.equals("") || (mTimeSelected==false) || (mDateSelected==false)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please Enter the right input !!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }else{
                DBHelper db=new DBHelper(this.getBaseContext());
                Date d=new Date(eventYear,eventMonth,eventDay,eventHour,eventMinute);

                db.addNewEvent(new Event(et_title.getText().toString(),d,et_description.getText().toString()));
                db.close();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Event Added Successfully !!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AddNewEventActivity.this.finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }



}
