package com.example.orvilleclarke.testfrag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.orvilleclarke.testfrag.R;
import com.example.orvilleclarke.testfrag.fragments.TodoListsFragment;
import com.example.orvilleclarke.testfrag.models.ToDoItem;
import com.example.orvilleclarke.testfrag.models.ToDoList;
import com.example.orvilleclarke.testfrag.models.ToDoListContentsOfEachList;

import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity
implements TodoListsFragment.OnListTodoListFragmentInteractionListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_todolistspage_container2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        purgeAndInitialSeed();
//        SeedSomeData();
//        testdb();
        initializeToDoListsGlobalVariables();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Throw intent
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent AddToDoList = new Intent(Main2Activity.this, AddA_ToDoList.class );
                startActivity(AddToDoList);
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
        } else{

            try{
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new TodoListsFragment())
                        .commit();

//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.container2, new AddFrag())
//                        .commit();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onListTodoListFragmentInteraction(long position) {

        try {

            // Create Intent that swaps out MainActivity and brings in EditOrDeleteActivity
            Intent intent = new Intent(Main2Activity.this, display_todolist.class);
//
            // Create bundle
            Bundle b = new Bundle();

            // Adds key and ArrayList Index converted to String to the bundle b
            b.putString("listId", String.valueOf(position));
//            // Adds Bundle b to the intent
            intent.putExtras(b);
//            ToDoListContentsOfEachList.ToDoListContent ab = new ToDoListContentsOfEachList.ToDoListContent();
//            ab.ReadToDoItemsFromDatabase(position, getApplicationContext());



//            //TODO figure out why startActivityForResult doesn't work
////            setResult(RESULT_OK,intent);
//
           startActivity(intent);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initializeToDoListsGlobalVariables (){
//        TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper = new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(getApplicationContext());
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ToDoList ab = new ToDoList();
        ab.ReadAllFromDatabase(getApplicationContext());

        ToDoList.getAllAvailableTodoLists();

    }

    public void testdb(){

        //TESTBED

        //Create to Todoitem
        ToDoItem a = new ToDoItem("test");
        a.description = "test";
        a.setToDoPriority("LOW");

        boolean comp = false;
        a.setCompleted(comp);
        a.setCreatedOnDate(new Date());

        long newid= a.CreateToDatabase(a.name, a.description,a.CreatedOnDate, getApplicationContext() );
        long newid2= a.CreateToDatabase(a, getApplicationContext() );


        ToDoItem b = a.ReadSingleFromDatabase(newid, getApplicationContext());

        // Update items in database
        ToDoItem c = new ToDoItem();
        a.setToDoPriority("HIGH");
        a.setToDoDueDate(new Date());
        a.setDescription("updated");
        a.setCompleted(true);

        a.setToDoCompleted(new Date());
        a.setId(newid2);
        c.UpdateInDatabase(a,getApplicationContext());
        ToDoListContentsOfEachList.ToDoListContent ab = new ToDoListContentsOfEachList.ToDoListContent();
        ab.ReadAllFromDatabase(getApplicationContext());
        ab.ReadToDoItemsFromDatabase(Long.valueOf(3), getApplicationContext());

        c.DeleteInDatabase(newid,getApplicationContext());

        // TODOLIST - CREATE READ UPDATE DELETE

        //CREATE
        ToDoList newlist = new ToDoList();
        newlist.setTitle("new school");
        newlist.setDateCreated(new Date());

        long newlistid = newlist.Create(newlist.getTitle(), newlist.getDateCreated(),getApplicationContext());
        long newlistid2 = newlist.Create(newlist.getTitle()+"2", newlist.getDateCreated(),getApplicationContext());
        long newlistid3 = newlist.Create(newlist.getTitle()+"3", newlist.getDateCreated(),getApplicationContext());
        //READ

        newlist.ReadAllFromDatabase(getApplicationContext());
        ArrayList<ToDoList> whatsInThereToDoList = newlist.getAllAvailableTodoLists();
        whatsInThereToDoList.clear();
        //UPDATE
        newlist.UpdateInDatabase(newlistid, "updated Title", getApplicationContext());
        newlist.UpdateInDatabase(newlistid3, "updated Title", getApplicationContext());
        newlist.ReadAllFromDatabase(getApplicationContext());
        newlist.getAllAvailableTodoLists();
        //DELETE
        newlist.DeleteInDatabase(newlistid2, getApplicationContext());
        newlist.ReadAllFromDatabase(getApplicationContext());
        newlist.getAllAvailableTodoLists();

        // TODOLISTITEMS - CREATE READ UPDATE DELETE
        //CREATE
        ToDoListContentsOfEachList.ToDoListContent newlistitems = new ToDoListContentsOfEachList.ToDoListContent();
        newlistitems.Create(newlistid, newid2, getApplicationContext());
        newlistitems.ReadToDoItemsFromDatabase(newlistid, getApplicationContext());  //saves to global variable per listid

        newlistitems.ReadAllFromDatabase(getApplicationContext());
    }
    public void SeedSomeData(){

        ToDoList newlist = new ToDoList();
        ToDoListContentsOfEachList.ToDoListContent newlistitems = new ToDoListContentsOfEachList.ToDoListContent();
        for(int i =0; i< 3; i++){
            String listTitle = "Seed: TODO List " + String.valueOf(i+1);
            long listid= newlist.Create(listTitle,new Date(), getApplicationContext());

            // Each list has 5 todoitems
            for(int j=0; j< 5; j++){
                ToDoItem testtodo = new ToDoItem();
                long todoid= testtodo.CreateToDatabase("Seed: TODOLIST " + String.valueOf(i+1) + ": TODOITEM "+ String.valueOf(j+1), "test description " + String.valueOf(j+1),new Date(), getApplicationContext());


                //Add to todolistitems
                newlistitems.Create(listid,todoid,getApplicationContext());
            }
        }

    }

    public void purgeAndInitialSeed(){
        ToDoListContentsOfEachList.ToDoListContent ab = new ToDoListContentsOfEachList.ToDoListContent();
        ab.DeleteAllInDatabase(getApplicationContext());

    }
    public void onHome(View view){
        Intent intent = new Intent(Main2Activity.this, Main2Activity.class);
        startActivity(intent);
        finish();

    }

}
