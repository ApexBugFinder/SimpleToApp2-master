package com.example.orvilleclarke.testfrag.activities;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orvilleclarke.testfrag.R;
import com.example.orvilleclarke.testfrag.models.ToDoList;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class AddA_ToDoList extends AppCompatActivity {

    TextView tvItem;
    EditText etNewToDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a__to_do_list);
        tvItem = (TextView) findViewById(R.id.textView_addTodoListPage);
        etNewToDo = (EditText)findViewById(R.id.editText_addToDoList);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabaddtodolist);
//
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Throw intent
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                String fieldvalue_toDoListTitle = etNewToDo.getText().toString();

                ToDoList newToDoList = new ToDoList();
                SimpleDateFormat dateformatter = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                        Locale.US);
                Date tempDAte= new Date();
                try{
                tempDAte = (Date)dateformatter.parse(tempDAte.toString());
                }catch(ParseException e){
                    e.printStackTrace();
                }

                newToDoList.Create(fieldvalue_toDoListTitle, tempDAte,getApplicationContext());
                ToDoList ab = new ToDoList();
                ab.ReadAllFromDatabase(getApplicationContext());
                Intent backToMain = new Intent(AddA_ToDoList.this, Main2Activity.class);
                startActivity(backToMain);
                finish();

            }
        });

    }
}
