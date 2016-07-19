package com.example.orvilleclarke.testfrag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.orvilleclarke.testfrag.R;
import com.example.orvilleclarke.testfrag.fragments.ToDoListFragment;
import com.example.orvilleclarke.testfrag.models.ToDoListContentsOfEachList;

public class display_todolist extends AppCompatActivity
implements ToDoListFragment.OnToDoItemFragmentInteractionListener {

  String listId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_todolistspage_container2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Bundle b = getIntent().getExtras();
        listId = b.getString("listId");
        //get all of list from db
        ToDoListContentsOfEachList.ToDoListContent ab = new ToDoListContentsOfEachList.ToDoListContent();
        ab.ReadToDoItemsFromDatabase(Long.valueOf(listId),getApplicationContext());



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Throw intent
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent AddToDoItem = new Intent(display_todolist.this, EditOrDeleteActivity.class );

                Bundle b = new Bundle();
                b.putString("listId", listId);
                b.putString("itemId", "new");
                AddToDoItem.putExtras(b);
                startActivity(AddToDoItem);
                finish();
            }
        });
        if (savedInstanceState != null) {
            try {
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.container, new TodoListsFragment())
//                        .commit();

//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.container2, new AddFrag())
//                        .commit();
//
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {


                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ToDoListFragment())
                        .commit();

//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.container2, new AddFrag())
//                        .commit();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        @Override
        public void onListFragmentInteraction ( long position){

            try {

                // Create Intent that swaps out MainActivity and brings in EditOrDeleteActivity
                Intent intent = new Intent(display_todolist.this, EditOrDeleteActivity.class);

                // Create bundle
                Bundle b = new Bundle();

                // Adds key and ArrayList Index converted to String to the bundle b
                b.putString("itemId", String.valueOf(position));
                b.putString("listId", listId);
                // Adds Bundle b to the intent
                intent.putExtras(b);
                //TODO figure out why startActivityForResult doesn't work
//            setResult(RESULT_OK,intent);

                startActivityForResult(intent, RESULT_OK);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void onHome(){
        Intent intent = new Intent(display_todolist.this, Main2Activity.class);
        startActivity(intent);
        finish();

    }

}
