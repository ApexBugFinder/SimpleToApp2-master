package com.example.orvilleclarke.testfrag.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.example.orvilleclarke.testfrag.utils.Helpers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.example.orvilleclarke.testfrag.utils.TodoReaderContract.TodoList;

/**
 * Created by Orville Clarke on 6/30/2016.
 */
// EACH TODOLIST has TODOITEMS
public class ToDoList {


    // GLOBAL ARRAYLIST FOR TODOLISTs
    public static final ArrayList<ToDoList> allAvailableTodoLists = new ArrayList<>();
    public long id;
    public String title;
    public Date dateCreated;



   // GETTERS
    public static ArrayList<ToDoList> getAllAvailableTodoLists() {
        return allAvailableTodoLists;
    }


    public long getId() {
        return id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }


    public String getTitle() {
        return title;
    }

    // SETTERS


    public void setId(long id) {
        this.id = id;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTitle(String name) {
        this.title = name;
    }



    // Empty Constructor
    public ToDoList ()
    {}
    // Constructor 3 parameters
    public ToDoList(long id, String title, Date createdOn){

        this.id = id;
        this.title = title;
        this.dateCreated = createdOn;

    }


    //**********************METHODS********************//
    //*************************************************//

    private static final int COUNT = 25;

//    static {
////        Add some sample items.
//        for (long i = 1; i <= COUNT; i++) {
//            ToDoList dummy = new ToDoList(i, "new list", new Date());
//
//            addItem(dummy);
//
//        }
//    }




    public static void addItem (ToDoList addToGlobal){

        allAvailableTodoLists.add(addToGlobal);

    }


    //TODO Save ToDoList Title to Database
    // CREATE
    public long Create(String title1,Date createdOn, Context context){

        boolean saved=false;
        // make sure no id duplicates

        TodoList.TodoListReaderDbHelper mDbHelper = new TodoList.TodoListReaderDbHelper(context);


        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        //Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TodoList.COLUMN_NAME_TODOLIST_TITLE, title1);
        values.put(TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED, Helpers.getDateTime());


        long id = -1;
        try{
            id = db.insert(TodoList.TABLE_NAME, null, values);
        }catch(SQLiteDatabaseCorruptException e){
            e.printStackTrace();
        }catch(SQLiteReadOnlyDatabaseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        if(id != -1)
        {
            saved = true;
        }
        ToDoList added = new ToDoList(id, title1, createdOn);

        addItem(added);

        return id;
    }

    // READ
    public boolean ReadAllFromDatabase(Context context) {
        boolean read = false;
        // Read from database

        TodoList.TodoListReaderDbHelper mDbHelper = new TodoList.TodoListReaderDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which colums from the database
        // you will actually use after this query.

        String[] projection = {
                TodoList.COLUMN_NAME_TODOLIST_ID,
                TodoList.COLUMN_NAME_TODOLIST_TITLE,
                TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED

        };
        String sortOrder = TodoList.COLUMN_NAME_TODOLIST_ID + " ASC";

        Cursor c = db.query(
                TodoList.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        if (c.getCount() > 0) {
            read = true;

            allAvailableTodoLists.clear();

            // Read items into the global variable

            c.moveToFirst();
            DateFormat formatter;

            formatter = new SimpleDateFormat("dd-MM-yyyy");
            try {
                while (c.moveToNext()) {

                    ToDoList toDoListInDb = new ToDoList();
                    toDoListInDb.id = c.getLong(c.getColumnIndexOrThrow(TodoList.COLUMN_NAME_TODOLIST_ID));
                    toDoListInDb.title = c.getString(c.getColumnIndexOrThrow(TodoList.COLUMN_NAME_TODOLIST_TITLE));
                    toDoListInDb.dateCreated = formatter.parse(c.getString(c.getColumnIndexOrThrow(TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED)));

                    allAvailableTodoLists.add(toDoListInDb);


                }
            }catch(ParseException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
            return read;

    }


    // Update in database
    public boolean UpdateInDatabase(long id, String Title, Context context){
        boolean saved=false;

        TodoList.TodoListReaderDbHelper mDbHelper = new TodoList.TodoListReaderDbHelper(context);
        SQLiteDatabase db =  mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoList.COLUMN_NAME_TODOLIST_TITLE, Title);

        String selection = TodoList.COLUMN_NAME_TODOLIST_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(TodoList.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count > 0 ){
            saved = true;
        }


      String[] projection = {

              TodoList.COLUMN_NAME_TODOLIST_ID,
              TodoList.COLUMN_NAME_TODOLIST_TITLE,
              TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED
      };
        selection = TodoList.COLUMN_NAME_TODOLIST_ID + " LIKE ?";

        Cursor c = db.query(TodoList.TABLE_NAME,
                projection,
                selection,          // columns for the where clause
                selectionArgs,      // values for the where clause
                null,               // don't filter by rows
                null,               // don't filter by row groups
                null);              // the sort order

        c.moveToFirst();



        Date datecreatED = new Date(c.getLong(c.getColumnIndexOrThrow(TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED)));
        ToDoList updatedInDb = new ToDoList(id, Title, datecreatED);
        addItem(updatedInDb);

        return saved;
    }
    //TODO Delete ToDoDueDate Date from Database
    // Delete In database
    public boolean DeleteInDatabase(long id, Context context){
        boolean saved=false;

        // Instantiate your subclass that extends SQLiteOpenHelper
        TodoList.TodoListReaderDbHelper mDbHelper = new TodoList.TodoListReaderDbHelper(context);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selection = TodoList.COLUMN_NAME_TODOLIST_ID + " LIKE ?";

        String[] sectionArgs = { String.valueOf(id) };

        int count = -1;
        //TODO put in a try catch
        count = db.delete(TodoList.TABLE_NAME, selection, sectionArgs);
        if(count >0 ){
            saved = true;
        }



        return saved;
    }
//    public boolean DeleteAllTableInDatabase(Context context){
//        boolean deletedall = false;
//        TodoList.TodoListReaderDbHelper mDbHelper = new TodoList.TodoListReaderDbHelper(context);
//
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//       //mDbHelper.onUpgrade(db,1,1);
//        String[] projection = {
//                TodoList.COLUMN_NAME_TODOLIST_ID,
//                TodoList.COLUMN_NAME_TODOLIST_TITLE,
//                TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED
//
//        };
//        String sortOrder = TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED + " DESC";
//
//        Cursor c = db.rawQuery("delete * from todolist", null);
////                TodoList.TABLE_NAME,
////                projection,
////                null,
////                null,
////                null,
////                null,
////                sortOrder
////        );
//        ArrayList<ToDoList> deleteArray = new ArrayList<>();
//        c.moveToFirst();
//        while(c.moveToNext()){
//
//
//            ToDoList todolistToDelete = new ToDoList();
//            todolistToDelete.id = c.getLong(c.getColumnIndexOrThrow(TodoList.COLUMN_NAME_TODOLIST_ID));
//            todolistToDelete.title = c.getString(c.getColumnIndexOrThrow(TodoList.COLUMN_NAME_TODOLIST_TITLE));
//            todolistToDelete.dateCreated = new Date(c.getLong(c.getColumnIndexOrThrow(TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED)));
////            String selection = TodoList.COLUMN_NAME_TODOLIST_ID + " LIKE ?";
////            String[] selectionarg ={ String.valueOf(todolistToDelete.id)};
//
//
//            deleteArray.add(todolistToDelete);
//
//        }
//
//ArrayList<ToDoList>deleteArray2 = new ArrayList<>();
//
//
//
//
//
//
//
//
//
//    return true;
//    }
}
