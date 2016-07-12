package com.example.orvilleclarke.testfrag.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;

import com.example.orvilleclarke.testfrag.utils.TodoReaderContract;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Orville Clarke on 6/30/2016.
 */
// TODOLISTCONTENTS are a class the facilitates multiple lists functionality of the app
public class ToDoListContentsOfEachList {


    // PROPERTIES
    public static final ArrayList<ToDoListContent> AllListsContains = new ArrayList<>();
    public static final ArrayList<ToDoListContent> ListContains = new ArrayList<>();
    public static final ArrayList<ToDoItem> WhatToDisplay = new ArrayList<>();
    // METHODS

    // ADD ITEM TO LIST & DATABASE
    // READ
    // UPDATE
    // DELETE ITEM FROM LIST & DATABASE

public static class ToDoListContent{
    // PROPERTIES
    public long id;
    public long listid;
    public long todoid;




    //GETTERS

    public long getId() {
        return id;
    }

    public long getListid() {
        return listid;
    }

    public long getTodoid() {
        return todoid;
    }

    // SETTERS


    public void setId(long id) {
        this.id = id;
    }

    public void setListid(long listid) {
        this.listid = listid;
    }

    public void setTodoid(long todoid) {
        this.todoid = todoid;
    }


    //Constructors

    // Empty
    public ToDoListContent() {

    }
    public ToDoListContent(long listIdParam, long todoIdParam){

        this.listid = listIdParam;
        this.todoid = todoIdParam;
    }



    //METHODS

    //ADD ITEM TO GLOBAL VARIABLE
    public void addItem (ToDoListContent addToGlobal){

        ListContains.add(addToGlobal);

    }
    //CREATE
    // CREATE
    public long Create(long listid, long todoid, Context context){

        // make sure no id duplicates
        TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper mDbHelper =
                new  TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper(context);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLIST_ID, listid);
        values.put(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOITEM_ID, todoid);

        long id = -1;

        try{
            id = db.insert(TodoReaderContract.TodoListItems.TABLE_NAME, null, values);

            ToDoListContent addedToToDoList = new ToDoListContent();
            addedToToDoList.setListid(listid);
            addedToToDoList.setTodoid(todoid);
            addedToToDoList.setId(id);
            ToDoListContentsOfEachList.ListContains.add(addedToToDoList);
        }catch(SQLiteDatabaseCorruptException e){
            e.printStackTrace();
        }catch(SQLiteReadOnlyDatabaseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        return id;
    }
    //READ

    public boolean ReadAllFromDatabase (Context context){
        boolean read = false;
        TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper mDbHelper = new TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which colums from the database
        // you will actually use after this query.

        String[] projection = {
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID,
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLIST_ID,
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOITEM_ID

        };
        String sortOrder = TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID + " ASC";
        Cursor c = db.query(
                TodoReaderContract.TodoListItems.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        if (c.getCount() > 0) {
            read = true;

            ToDoListContentsOfEachList.AllListsContains.clear();

            // Read items into the global variable

            c.moveToFirst();

            while (c.moveToNext()) {

                ToDoListContent toDoListContentInDb = new ToDoListContent();
                toDoListContentInDb.setId(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID)));
                toDoListContentInDb.setListid(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLIST_ID)));
                toDoListContentInDb.setTodoid(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOITEM_ID)));

                ToDoListContentsOfEachList.AllListsContains.add(toDoListContentInDb);



            }
        }

        return read;
    }
    public boolean ReadToDoItemsFromDatabase (Long listId, Context context){
        boolean read = false;
        TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper mDbHelper = new TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        // Define a projection that specifies which colums from the database
        // you will actually use after this query.

        String[] projection = {
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID,
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLIST_ID,
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOITEM_ID

        };

        String selection =new String (TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLIST_ID + " =?");
        String [] selectionArg = new String [] {
                String.valueOf(listId)
        };
        String sortOrder = TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID + " ASC";
        Cursor c = db.query(
                TodoReaderContract.TodoListItems.TABLE_NAME,
                projection,
                selection,
                selectionArg,
                null,
                null,
                sortOrder
        );
        if (c.getCount() > 0) {
            read = true;

            ToDoListContentsOfEachList.ListContains.clear();
            ToDoListContentsOfEachList.WhatToDisplay.clear();
            // Read items into the global variable

            c.moveToFirst();

            while (c.moveToNext()) {

                ToDoListContent toDoListContentInDb = new ToDoListContent();
                toDoListContentInDb.setId(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID)));
                toDoListContentInDb.setListid(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLIST_ID)));
                toDoListContentInDb.setTodoid(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoListItems.COLUMN_NAME_TODOITEM_ID)));

                ToDoListContentsOfEachList.ListContains.add(toDoListContentInDb);

            }
//            db.close();
            TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper2 = new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(context);
            SQLiteDatabase db2 = mDbHelper2.getWritableDatabase();
            for (ToDoListContentsOfEachList.ToDoListContent item: ToDoListContentsOfEachList.ListContains) {


                if (db2.isOpen()) {
                    String[] projection2 = new String[]{
                            TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID,
                            TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_NAME,
                            TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_DESCRIP,
                            TodoReaderContract.TodoItem.COLUMN_NAME_CREATED_ON_DATE,
                            TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_PRIORITY,
                            TodoReaderContract.TodoItem.COLUMN_NAME_TODDUE_ONDATE,
                            TodoReaderContract.TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE
                    };
                    String id = String.valueOf(item.getTodoid());
                    String selection2 = TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID + "=?";
                    String[] selectionArg2 = new String[]{
                            id
                    };
                    Cursor b = db2.rawQuery("select * from " + TodoReaderContract.TodoItem.TABLE_NAME + "  where " +
                            TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID + " = ?", selectionArg2);
//                    Cursor b = db2.query(
//                            TodoReaderContract.TodoItem.TABLE_NAME,
//                            projection2,
//                            selection2,
//                            selectionArg2,
//                            null,
//                            null,
//                            null);

                    b.getCount();
                    b.moveToFirst();
                    ToDoItem foundInDb = new ToDoItem();
                    foundInDb.setId(b.getLong(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID)));
                    foundInDb.setName(b.getString(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_NAME)));
                    foundInDb.setDescription(b.getString(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_DESCRIP)));
                    foundInDb.setCreatedOnDate(new Date(b.getLong(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_CREATED_ON_DATE))));
                    foundInDb.setToDoPriority(b.getString(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_PRIORITY)));
                    foundInDb.setToDoDueDate(new Date(b.getLong(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODDUE_ONDATE))));
                    foundInDb.setToDoCompleted(new Date(b.getLong(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE))));
                    foundInDb.setCompleted(Boolean.parseBoolean(b.getString(b.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_COMPLETED_BOOL))));


                    ToDoListContentsOfEachList.WhatToDisplay.add(foundInDb);
                }
            }
        }

        return read;
    }
    //DELETE
    // DELETE ITEM


    public boolean DeleteAllInDatabase(Context context){
        boolean deletedall = false;
        TodoReaderContract.TodoList.TodoListReaderDbHelper mDbHelper1 =
                new TodoReaderContract.TodoList.TodoListReaderDbHelper(context);

        SQLiteDatabase db = mDbHelper1.getReadableDatabase();
        mDbHelper1.onUpgrade(db,1,1);


        TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper mDbHelper2 =
                new TodoReaderContract.TodoListItems.TodoListItemsReaderDbHelper(context);
        mDbHelper2.onUpgrade(db,1,1);

        TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper3 =
                new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(context);

        mDbHelper3.onUpgrade(db,1,1);

//        String[] projection = {
//                TodoReaderContract.TodoList.COLUMN_NAME_TODOLIST_ID,
//                TodoReaderContract.TodoList.COLUMN_NAME_TODOLIST_TITLE,
//                TodoReaderContract.TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED
//
//        };
//        String sortOrder = TodoReaderContract.TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED + " DESC";
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
//
//        for(int i =0; i<c.getCount(); i ++){
//            c.move(i+1);
//            ToDoList todolistToDelete = new ToDoList();
//            todolistToDelete.id = c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoList.COLUMN_NAME_TODOLIST_ID));
//            todolistToDelete.title = c.getString(c.getColumnIndexOrThrow(TodoReaderContract.TodoList.COLUMN_NAME_TODOLIST_TITLE));
//            todolistToDelete.dateCreated = new Date(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED)));
////            String selection = TodoList.COLUMN_NAME_TODOLIST_ID + " LIKE ?";
////            String[] selectionarg ={ String.valueOf(todolistToDelete.id)};
//
//
//            deleteArray.add(todolistToDelete);
//
//        }
//
//        ArrayList<ToDoList>deleteArray2 = new ArrayList<>();
//
//
//
//
//
//
//
//

        return true;
    }

    public boolean DeleteItemInDatabase(long id,Context context){
        boolean deletedall = false;


        TodoReaderContract.TodoList.TodoListReaderDbHelper mDbHelper1 =
                new TodoReaderContract.TodoList.TodoListReaderDbHelper(context);

        SQLiteDatabase db = mDbHelper1.getReadableDatabase();






        String[] projection = {
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID,
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLIST_ID,
                TodoReaderContract.TodoListItems.COLUMN_NAME_TODOITEM_ID

        };
        String selection =new String (TodoReaderContract.TodoListItems.COLUMN_NAME_TODOLISTITEM_ID + "=?");
        String [] selectionArg= {String.valueOf(id)};
        try {
            db.delete(TodoReaderContract.TodoListItems.TABLE_NAME,
                    selection, selectionArg);
        }catch(Exception e){
            e.printStackTrace();
        }



        return true;
    }
}





}
