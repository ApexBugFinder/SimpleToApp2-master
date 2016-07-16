package com.example.orvilleclarke.testfrag.activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.orvilleclarke.testfrag.R;
import com.example.orvilleclarke.testfrag.models.ToDoItem;
import com.example.orvilleclarke.testfrag.models.ToDoList;
import com.example.orvilleclarke.testfrag.models.ToDoListContentsOfEachList;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Date;

public class EditOrDeleteActivity extends AppCompatActivity  {


//    ArrayList<ToDoContent.ToDoItem> items;
    ArrayAdapter<String> itemsAdapter;
   private TextView tvItem;
    private TextView tvItemId;
    private EditText etUpdateText ;
    private Button btnUpdate;
    private Button btnDelete;
    private DatePicker datePicker;
    private Spinner spinner1;
    private TimePicker timePicker;
    private int year;
    private int month;
    private int day;
    private Calendar cal;
    private Date dueDate;
    private TextView textViewDueDate;
    private TextView textViewDueTime;
    ToDoItem editItem;
    private EditText editTextdescription;
    private TextView tvListId;
    ToDoListContentsOfEachList.ToDoListContent addToList = new ToDoListContentsOfEachList.ToDoListContent();


    static final int DATE_DIALOG_ID = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete);

        // links textview to tvItem
//        addListeners();
        editItem = new ToDoItem();
        spinner1        =   (Spinner) findViewById(R.id.spinner);
//        btnDelete       =   (Button) findViewById(R.id.btnDelete);
//        btnUpdate       =   (Button) findViewById(R.id.btnUpdate);
        tvItem          =   (TextView) findViewById(R.id.etUpdateText);
        tvItemId        =   (TextView) findViewById(R.id.todoId);
        tvListId        =   (TextView) findViewById(R.id.tvListId);
        etUpdateText    =   (EditText) findViewById(R.id.etUpdateText);
         datePicker      =   (DatePicker) findViewById(R.id.datePicker);
        timePicker      =   (TimePicker) findViewById(R.id.timePicker);
        textViewDueDate =   (TextView)  findViewById(R.id.textViewDueDate);
        textViewDueTime = (TextView) findViewById(R.id.textViewDueTime);
        editTextdescription = (EditText) findViewById(R.id.editTextdescription);
        final LinearLayout calendar_layout = (LinearLayout)findViewById(R.id.LinearLayoutCalendar);
        final LinearLayout calendar_contents_layout = (LinearLayout)findViewById(R.id.LinearLayoutCalendarContents);
        final LinearLayout clock_layout = (LinearLayout)findViewById(R.id.LinearLayoutClock);
        final LinearLayout clock_contents_layout = (LinearLayout)findViewById(R.id.LinearLayoutClockContents);

//        calendar_contents_layout.setScaleY(0.0f);
        calendar_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                try{
                    Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.my_calendar_clock_animation);
                    if(calendar_contents_layout.getVisibility()== LinearLayout.INVISIBLE){
                    calendar_contents_layout.setVisibility(LinearLayout.VISIBLE);

                    datePicker.setAnimation(animation);
                    datePicker.animate();
                    datePicker.setVisibility(LinearLayout.VISIBLE);
                    animation.start();
                    }else{
                        calendar_contents_layout.setVisibility(LinearLayout.INVISIBLE);
                        datePicker.setVisibility(View.GONE);
                        animation.setRepeatMode(Animation.REVERSE);
                        datePicker.setAnimation(animation);
                        animation.start();
                    }
//
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        clock_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.my_calendar_clock_animation);
                if(clock_contents_layout.getVisibility()== View.INVISIBLE ){
                    clock_contents_layout.setVisibility(LinearLayout.VISIBLE);
                    timePicker.setVisibility(LinearLayout.VISIBLE);
                    timePicker.setAnimation(animation);
                    timePicker.animate();
                    animation.start();
                }else{
                    clock_contents_layout.setVisibility(LinearLayout.INVISIBLE);
                    timePicker.setVisibility(View.GONE);

                    animation.setRepeatMode(Animation.REVERSE);
                    timePicker.setAnimation(animation);
                    animation.start();
                }

            }
        });
//
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.activity_edit_or_delete, getResources().getStringArray(R.array.priority));
        dataAdapter.setDropDownViewResource(R.layout.activity_edit_or_delete);


        // gets messages from intent
        Bundle b = getIntent().getExtras();
        String listId = b.getString("listId");

        String itemId = b.getString("itemId");
        addToList.setListid(Long.valueOf(listId));

        if(!itemId.equals("new")) {
            addToList.setTodoid(Long.valueOf(itemId));
        }



        Date newdate = new Date();
        ArrayList<Integer> dateInfo = processDate(newdate);
        year= dateInfo.get(0);
        month = dateInfo.get(1);
        day = dateInfo.get(2);
        showDate(year,month, day);
        showTime(newdate);
        if (b != null) {





            int i = 0;
            if(!itemId.equals("new") || ToDoListContentsOfEachList.WhatToDisplay.isEmpty()) {
                for (ToDoItem item : ToDoListContentsOfEachList.WhatToDisplay) {
                    if(item.getId() == Long.valueOf(itemId)){

                        editItem = item;
                    }
                }
            }

            if(editItem.id != 0) {
                tvItem.setText(editItem.getName());
                tvListId.setText(listId);
                tvItemId.setText(String.valueOf(editItem.getId()));

                if(editItem.getToDoPriority() == "LOW"){
                    int position = 0 ;

                    position =  dataAdapter.getPosition("LOW");
                    spinner1.setSelection(position);

                }if(editItem.getToDoPriority() == "MED"){
                    int position = 0 ;

                    position =  dataAdapter.getPosition("MED");
                    spinner1.setSelection(position);

                }if(editItem.getToDoPriority() == "HIGH"){
                    int position = 0 ;

                    position =  dataAdapter.getPosition("HIGH");
                    spinner1.setSelection(position);

                }

                editTextdescription.setText( editItem.getDescription());
                dueDate = editItem.ToDoDueDate;
                if(dueDate != null && !dueDate.equals(new Date(0L))){
                     dateInfo = processDate(dueDate);
                    year= dateInfo.get(0);
                    month = dateInfo.get(1);
                    day = dateInfo.get(2);
//  String date = DateFormatUtils.format(dueDate, "yyyy-MM-dd HH:mm:ss");
//                    year =Integer.valueOf( date.substring(0,4));
//                    month = Integer.valueOf(date.substring(5,7));
//                    day = Integer.valueOf(date.substring(8,10));

                datePicker.init(year,month-1,day, null);
                showDate(year,month,day);
                }else
                {
                    dueDate = new Date();
                     dateInfo = processDate(dueDate);
                    year= dateInfo.get(0);
                    month = dateInfo.get(1);
                    day = dateInfo.get(2);
//                    String date = DateFormatUtils.format(dueDate, "yyyy-MM-dd HH:mm:ss");
//                    year =Integer.valueOf( date.substring(0,4));
//                    month = Integer.valueOf(date.substring(5,7));
//                    day = Integer.valueOf(date.substring(8,10));

                    datePicker.init(year,month-1,day, null);
                    showDate(year,month,day);
                }

            }
            else{

                tvItem.setText(editItem.getName());
                tvListId.setText(listId);
            }

        }
    }

    public void onUpdate(View view){


        // grabs string from the editText etUpdateText and saves to string
        if(etUpdateText.getText().toString().isEmpty()){

        }else{
        editItem.setName( etUpdateText.getText().toString());}
        String fieldValueOld = tvItem.getText().toString();
        String fieldValueId =  tvItemId.getText().toString();
        editItem.setToDoPriority( String.valueOf(spinner1.getSelectedItem()));
        editItem.setDescription(editTextdescription.getText().toString());



        if(fieldValueId == null || fieldValueId == "" || addToList.getTodoid()==0){
            editItem.setCreatedOnDate(new Date());
            long id = editItem.CreateToDatabase(editItem,getApplicationContext());
            ToDoListContentsOfEachList.ToDoListContent updateList = new ToDoListContentsOfEachList.ToDoListContent(addToList.getListid(), id);
            updateList.Create(updateList.getListid(), updateList.getTodoid(), getApplicationContext());
        }else{

            editItem.UpdateInDatabase(editItem, getApplicationContext());

        }

        ToDoListContentsOfEachList.ToDoListContent ab = new ToDoListContentsOfEachList.ToDoListContent();
        ab.ReadAllFromDatabase(getApplicationContext());

        Intent intent = new Intent(EditOrDeleteActivity.this, display_todolist.class);
//        startActivityForResult(intent, RESULT_OK);
//        finish();
        Bundle b = getIntent().getExtras();
        b.putString("listId", String.valueOf( addToList.getListid()));

        intent.putExtras(b);
       startActivity(intent );
//        setResult(RESULT_OK, intent);
        finish();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onCancel(View view){

        // Create Intent that swaps out MainActivity and brings in EditOrDeleteActivity
        Intent intent = new Intent(EditOrDeleteActivity.this, display_todolist.class);
//
        // Create bundle
        Bundle b = new Bundle();

        // Adds key and ArrayList Index converted to String to the bundle b
        b.putString("listId", String.valueOf(addToList.getListid()));
//            // Adds Bundle b to the intent
        intent.putExtras(b);
        startActivity(intent );

    }
    public void onDelete (View view){

        // GET THE ArrayList index FROM THE INDEX
        String fieldValueId =  tvItemId.getText().toString();

        // DELETE FROM todolistitems
        ToDoListContentsOfEachList.ToDoListContent ab = new ToDoListContentsOfEachList.ToDoListContent();
        for (ToDoListContentsOfEachList.ToDoListContent item: ToDoListContentsOfEachList.ListContains ) {
            if(item.getListid() == addToList.getListid() && item.getTodoid() == addToList.getTodoid()){
               long listitemToDelete = item.getId();
                ab.DeleteItemInDatabase(listitemToDelete,getApplicationContext());
            }
        }
//        ab.DeleteItemInDatabase(Long.valueOf(fieldValueId))
        //DELETE FROM todoitem
        ToDoItem deleter = new ToDoItem();
         deleter.DeleteInDatabase(addToList.getTodoid(), getApplicationContext());

        ToDoList updater = new ToDoList();
        updater.ReadAllFromDatabase(getApplicationContext());
        ab.ReadAllFromDatabase(getApplicationContext());
        ToDoListContentsOfEachList.ToDoListContent updaterOflistitems = new ToDoListContentsOfEachList.ToDoListContent();
        updaterOflistitems.ReadToDoItemsFromDatabase(addToList.getListid(),getApplicationContext());

        // Build intent and return to the main menu
        // Create Intent that swaps out MainActivity and brings in EditOrDeleteActivity
        Intent intent = new Intent(EditOrDeleteActivity.this, display_todolist.class);
//
        // Create bundle
        Bundle b = new Bundle();

        // Adds key and ArrayList Index converted to String to the bundle b
        b.putString("listId", String.valueOf(addToList.getListid()));
//            // Adds Bundle b to the intent
        intent.putExtras(b);
        startActivity(intent );

        }
private DatePicker.OnDateChangedListener myDateListener = new DatePicker.OnDateChangedListener(){
    @Override
    public void onDateChanged(DatePicker arg0, int arg1, int arg2, int arg3){
        showDate(arg1, arg2 +1, arg3);

    }
};
    private ArrayList<Integer> processDate(Date dateTobeShown){

        int year, month, day;
        String date = DateFormatUtils.format(dateTobeShown, "yyyy-MM-dd HH:mm:ss");
         year =Integer.valueOf(date.substring(0,4));
         month = Integer.valueOf(date.substring(5,7));
         day = Integer.valueOf(date.substring(8,10));
        ArrayList<Integer> processdateInfo = new ArrayList<Integer>(3);
        processdateInfo.add(year);
        processdateInfo.add(month);
        processdateInfo.add(day);
        return processdateInfo;

    }
    private void showDate(int year, int month, int day){

        textViewDueDate.setText(new StringBuilder()
                .append(day).append("/")
                .append(month).append("/")
                .append(year));

    }
    private void showTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        sdf.setTimeZone(TimeZone.getDefault());
        textViewDueTime.setText(sdf.format(date.getTime()));


    }

    public void setDueDate(View view){
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hh = timePicker.getHour();
        int mm = timePicker.getMinute();

        long yearlong =(long) datePicker.getYear();
        long monthlong = (long)datePicker.getMonth();
        long daylong = (long)datePicker.getDayOfMonth();
        Date aa = new Date( year -1900  , month , day, hh, mm);
        Date bb = new Date();

       editItem.setToDoDueDate(aa);

        showDate(year, month,day);
        showTime(aa);
    }

public void addListeners(){

}


    }



