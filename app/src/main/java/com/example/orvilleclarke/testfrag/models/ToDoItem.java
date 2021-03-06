package com.example.orvilleclarke.testfrag.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.example.orvilleclarke.testfrag.utils.TodoReaderContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Orville Clarke on 6/30/2016.
 */
public class ToDoItem {
    // PROPERTIES
    public long id;
    public String name;
    public String description;
    public String ToDoPriority;
    public Date CreatedOnDate;
    public Date ToDoCompleted;
    public Date ToDoDueDate;
    public boolean Completed = false;
    public boolean DueDateSet = false;



    // GETTERS

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedOnDate() {return CreatedOnDate;    }

    public String getToDoPriority() {
        return ToDoPriority;
    }

    public Date getToDoCompleted() {
        return ToDoCompleted;
    }

    public Date getToDoDueDate() {
        return ToDoDueDate;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public boolean isDueDateSet() { return DueDateSet; }


    //SETTERS

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedOnDate(Date createdOnDate) {CreatedOnDate = createdOnDate;    }

    public void setToDoPriority(String toDoPriority) {
        ToDoPriority = toDoPriority;
    }

    public void setToDoCompleted(Date toDoCompleted) {
        ToDoCompleted = toDoCompleted;
    }

    public void setToDoDueDate(Date toDoDueDate) {
        ToDoDueDate = toDoDueDate;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }

    public void setDueDateSet(boolean dueDateSet) { DueDateSet = dueDateSet;    }

    // Empty constructor
    public ToDoItem(){


    }

    public ToDoItem(String nameParam){
        this.name= nameParam;
    }
    // Constructor 2 Params name and id
    public ToDoItem (long id, String  nameParam)
    {
        //TODO get id from database getNewId
        this.name = nameParam;
        this.id = id;
        this.CreatedOnDate = new Date();

    }

    //TODO Save ToDoItem Date to Database
    // Save to database
    public long CreateToDatabase(String name, String description, Date createdOnDate, Context context) {

        long id = -1;
        //Get Helper
        TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper = new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(context);

        try {
            // Get the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            boolean f = false;
            String priority = "LOW";

            // Create a new map of values where the columnn names are keys
            ContentValues values = new ContentValues();
//            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID, "");
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_NAME, name);
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_DESCRIP, description);
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_CREATED_ON_DATE, createdOnDate.toString());
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_PRIORITY, priority);
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_COMPLETED_BOOL, f);
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_IS_TODO_DUEDATE_SET,f );
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE, new Date().toString());
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODDUE_ONDATE, new Date().toString());

            id = db.insert(TodoReaderContract.TodoItem.TABLE_NAME, null, values);

        } catch (SQLiteDatabaseCorruptException e) {
            e.printStackTrace();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(id != -1){
           return id;

        }


        return id;
    }

    public long CreateToDatabase(ToDoItem newTodo, Context context) {

        long id = -1;
        //Get Helper
        TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper = new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(context);

        try {
            // Get the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            SimpleDateFormat dateformatter = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.US);
            Date tempDAte= new Date();

            if(newTodo.getToDoCompleted()== null){

                newTodo.setToDoCompleted(tempDAte);

            }
            if(newTodo.getToDoDueDate() == null){
                newTodo.setToDoDueDate(tempDAte);
            }
            String createdonDate = dateformatter.format(newTodo.getToDoCompleted()).toString();
            // Create a new map of values where the columnn names are keys
            ContentValues values = new ContentValues();
//            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID, "");
            if (newTodo.getName() != null)
                values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_NAME, newTodo.name);
            if (newTodo.getDescription() != null)
                values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_DESCRIP, newTodo.description);
            if (newTodo.getCreatedOnDate() != null)
                values.put(TodoReaderContract.TodoItem.COLUMN_NAME_CREATED_ON_DATE, dateformatter.format(newTodo.CreatedOnDate).toString());
            if (newTodo.getToDoPriority() != null)
                values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_PRIORITY, newTodo.getToDoPriority());
            if (newTodo.getToDoDueDate() != null)
                values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODDUE_ONDATE, createdonDate);
            if (newTodo.getToDoCompleted() != null)
                values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE, dateformatter.format(newTodo.getToDoCompleted()).toString());
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_IS_TODO_DUEDATE_SET, newTodo.isDueDateSet());
            values.put(TodoReaderContract.TodoItem.COLUMN_NAME_COMPLETED_BOOL, newTodo.Completed);

            id = db.insert(TodoReaderContract.TodoItem.TABLE_NAME, null, values);

        }catch(NullPointerException e){
            e.printStackTrace();

        } catch (SQLiteDatabaseCorruptException e) {
            e.printStackTrace();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(id != -1){
           return id;

        }


        return id;
    }
    public ToDoItem ReadSingleFromDatabase(long todoitemid, Context context){


        ToDoItem foundInDb = new ToDoItem();
        DateFormat formatter;

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper = new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(context);
        SQLiteDatabase db  = mDbHelper.getReadableDatabase();
        String sid =String.valueOf(todoitemid);
        try{
            String [] projection = new String [] {
                    TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID,
                    TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_NAME,
                    TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_DESCRIP,
                    TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_PRIORITY,
                    TodoReaderContract.TodoItem.COLUMN_NAME_TODDUE_ONDATE,
                    TodoReaderContract.TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE,
                    TodoReaderContract.TodoItem.COLUMN_NAME_COMPLETED_BOOL,
                    TodoReaderContract.TodoItem.COLUMN_NAME_IS_TODO_DUEDATE_SET,
                    TodoReaderContract.TodoItem.COLUMN_NAME_CREATED_ON_DATE
            };
            String selection =new String (TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID + "=?");
            String [] selectionArg = new String [] {
                    String.valueOf(todoitemid)
            };
            Cursor c = db.query(
                    TodoReaderContract.TodoItem.TABLE_NAME,
                    projection,
                    selection,
                   selectionArg,
                    null,
                    null,
                    null);

            c.moveToFirst();

            android.icu.util.Calendar ca = android.icu.util.Calendar.getInstance();


            foundInDb.setId(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID)));
            foundInDb.setName(c.getString(c.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_NAME)));
            foundInDb.setDescription(c.getString(c.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_DESCRIP)));
            foundInDb.setToDoPriority(c.getString(c.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_PRIORITY)));

            foundInDb.setToDoDueDate(new Date (c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODDUE_ONDATE))));
            foundInDb.setCreatedOnDate(new Date(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_CREATED_ON_DATE))));
            foundInDb.setToDoCompleted(new Date(c.getLong(c.getColumnIndexOrThrow(TodoReaderContract.TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE))));
            foundInDb.setCompleted (Boolean.parseBoolean(
                    c.getString(
                            c.getColumnIndexOrThrow(
                                    TodoReaderContract.TodoItem.COLUMN_NAME_COMPLETED_BOOL)
                    )
            )
            );

            if(foundInDb.id >0)
            return foundInDb;
        }catch(SQLiteCantOpenDatabaseException e){
            e.printStackTrace();
        }
        catch(SQLiteReadOnlyDatabaseException e){
            e.printStackTrace();
        }
        catch(SQLiteException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }



        return foundInDb;
    }
    //TODO Update ToDoDueDate Date in Database
    // Update in database
    public boolean UpdateInDatabase(ToDoItem updatedTodoItem, Context context){
        boolean saved=false;

        TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper = new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(context);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        java.util.Date utilDate = new Date();
        java.sql.Date  date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        ContentValues values = new ContentValues();
//        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID, String.valueOf(updatedTodoItem.id));
        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_NAME, updatedTodoItem.getName());
        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_DESCRIP, updatedTodoItem.getDescription());
        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_CREATED_ON_DATE, updatedTodoItem.getCreatedOnDate().toString());
        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_PRIORITY, updatedTodoItem.getToDoPriority());
        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODDUE_ONDATE, updatedTodoItem.getToDoDueDate().toString());
        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE, updatedTodoItem.getToDoCompleted().toString());
        values.put(TodoReaderContract.TodoItem.COLUMN_NAME_COMPLETED_BOOL, updatedTodoItem.isCompleted());

        String selection = new String(TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID + "=?");
        String [] selectionArgs = {String.valueOf(updatedTodoItem.id)};
        try {
            int count = db.update(TodoReaderContract.TodoItem.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            if(count>0){
                saved = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return saved;
    }
    //TODO Delete ToDoDueDate Date from Database
    // Delete In database
    public boolean DeleteInDatabase(long id, Context context){
        boolean saved=false;

        // Instantiate your subclass that extends SQLiteOpenHelper
        TodoReaderContract.TodoItem.TodoItemReaderDbHelper mDbHelper = new TodoReaderContract.TodoItem.TodoItemReaderDbHelper(context);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selection = TodoReaderContract.TodoItem.COLUMN_NAME_TODOITEM_ID + " LIKE ?";

        String[] sectionArgs = { String.valueOf(id) };

        int count = -1;
        //TODO put in a try catch
        count = db.delete(TodoReaderContract.TodoItem.TABLE_NAME, selection, sectionArgs);
        if(count >0 ){
            saved = true;
        }
        //TODO Delete item from todoitemlist


        return saved;
    }


//    /**
//     * Created by Orville Clarke on 6/30/2016.
//     */
//    public static class ToDoPriority {
//
//        public int id;
//
//
//
//        public int todoItemId;
//       public enum priority {LOW, MED, HIGH}
//
//
//
//        public int getTodoItemId() {
//            return todoItemId;
//        }
//
//        public void setTodoItemId(int todoItemId) {
//            this.todoItemId = todoItemId;
//        }
//
//
//
//        //TODO Save ToDoCompleted Date to Database
//        // Save to database
//        public boolean SaveToDatabase(){
//            boolean saved=false;
//            // make sure no id duplicates
//
//            return saved;
//        }
//        //TODO Update ToDoDueDate Date in Database
//        // Update in database
//        public boolean UpdateInDatabase(){
//            boolean saved=false;
//
//            return saved;
//        }
//        //TODO Delete ToDoDueDate Date from Database
//        // Delete In database
//        public boolean DeleteInDatabase(){
//            boolean saved=false;
//
//            return saved;
//        }
//
//
//    }
//
//    /**
//     * Created by Orville Clarke on 6/30/2016.
//     */
//    public static class ToDoCompleted {
//
//        public long id;
//        public Date completedOn ;
//        public int ToDoItemId;
//
//
//        //Getters
//        public Date getCompletedOn() {
//            return completedOn;
//        }
//        public long getToDoItemId() {
//            return ToDoItemId;
//        }
//
//
//        public void setCompletedOn(Date completedOn) {
//            this.completedOn = completedOn;
//        }
//
//
//        public void getId (){
//            // get id from database
//                // connect to db and get last id value
//            // int idFromDb =
//            // set local id
//
//            //this.id =
//        }
//        //TODO Save ToDoCompleted Date to Database
//        // Save to database
//        public boolean SaveToDatabase(){
//            boolean saved=false;
//            // make sure no id duplicates
//
//            return saved;
//        }
//        //TODO Update ToDoCompleted Date in Database
//        // Update in database
//        public boolean UpdateInDatabase(){
//            boolean saved=false;
//
//            return saved;
//        }
//        //TODO Delete ToDoCompleted Date from Database
//        // Delete In database
//        public boolean DeleteInDatabase(){
//            boolean saved=false;
//
//            return saved;
//        }
//
//    }
//
//    /**
//     * Created by Orville Clarke on 6/30/2016.
//     */
//    public static class ToDoDueDate {
//
//        public final long id;
//        public Date dueDate ;
//
//        public ToDoDueDate() {
//            id = 0;
//        }
//
//        public Date getDueDate() {
//            return dueDate;
//        }
//
//        public void setDueDate(Date dueDate) {
//            this.dueDate = dueDate;
//        }
//
//
//
//        //TODO Save ToDoCompleted Date to Database
//        // Save to database
//        public boolean SaveToDatabase(){
//            boolean saved=false;
//            // make sure no id duplicates
//
//            return saved;
//        }
//        //TODO Update ToDoDueDate Date in Database
//        // Update in database
//        public boolean UpdateInDatabase(){
//            boolean saved=false;
//
//            return saved;
//        }
//        //TODO Delete ToDoDueDate Date from Database
//        // Delete In database
//        public boolean DeleteInDatabase(){
//            boolean saved=false;
//
//            return saved;
//        }
//    }
}
