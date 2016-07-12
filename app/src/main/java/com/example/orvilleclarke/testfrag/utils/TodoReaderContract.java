package com.example.orvilleclarke.testfrag.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.provider.BaseColumns;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Orville Clarke on 6/30/2016.
 */
public class TodoReaderContract {

    // To prevent someone from accitentially instantiating the contract class
    // give it an empty constructor.
    public TodoReaderContract(){}

    /* Inner classes that defines the table contents */

    // TODOITEM
    public static abstract class TodoItem implements  BaseColumns{

        public static final String TABLE_NAME = "todoitem";
        public static String COLUMN_NAME_TODOITEM_ID = "todoitemid";
        public static String COLUMN_NAME_TODOITEM_NAME = "todoitemname";
        public static String COLUMN_NAME_TODOITEM_DESCRIP = "todoitemdescription";
        public static String COLUMN_NAME_CREATED_ON_DATE = "todocreatedondate";
        public static String COLUMN_NAME_TODOITEM_PRIORITY = "todoitempriority";
        public static String COLUMN_NAME_TODCOMPLETED_ONDATE = "todoitemcompleteddate";
        public static String COLUMN_NAME_TODDUE_ONDATE = "todoitemduedate";
        public static String COLUMN_NAME_COMPLETED_BOOL = "todoitemcompleted";



        //SQL HELPER


        private static final String TEXT_TYPE = " TEXT";
        private static final String DATE_TYPE = " DATE";
        private static final String TIMESTAMP_TYPE = " TIMESTAMP";
        private static final String BOOL_TYPE = " BOOL";
        private static final String  COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TodoItem.TABLE_NAME + " (" +
                        TodoItem.COLUMN_NAME_TODOITEM_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT," +
                        TodoItem.COLUMN_NAME_TODOITEM_NAME + TEXT_TYPE + COMMA_SEP +
                        TodoItem.COLUMN_NAME_TODOITEM_DESCRIP + TEXT_TYPE + COMMA_SEP +
                        TodoItem.COLUMN_NAME_CREATED_ON_DATE + DATE_TYPE + COMMA_SEP +
                        TodoItem.COLUMN_NAME_TODOITEM_PRIORITY + TEXT_TYPE + COMMA_SEP +
                        TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE + DATE_TYPE + COMMA_SEP +
                        TodoItem.COLUMN_NAME_TODDUE_ONDATE + TEXT_TYPE + COMMA_SEP +
                        TodoItem.COLUMN_NAME_COMPLETED_BOOL + BOOL_TYPE +
                        " )" ;

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                TodoItem.TABLE_NAME;


        public static class TodoItemReaderDbHelper extends SQLiteOpenHelper {

            //If you change the database schema, you must increment the database versio
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "TodoReader.db";


            public TodoItemReaderDbHelper (Context context){
                super(context, DATABASE_NAME, null, DATABASE_VERSION);

            }

            public void onCreate(SQLiteDatabase db){
                int id = 0;



                db.execSQL(SQL_CREATE_ENTRIES);
                SeedData(db);





            }
            public void SeedData(SQLiteDatabase db){
                String name = "seed";
                String description = "seed description";
                String priority = "HIGH";
                java.util.Date utilDate= new java.util.Date();
                Date codate= new Date(utilDate.getTime());
                Boolean completed = true;
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

                ContentValues values = new ContentValues();
//                values.put(TodoItem.COLUMN_NAME_TODOITEM_ID, "");
                values.put(TodoItem.COLUMN_NAME_CREATED_ON_DATE, date.toString());
                values.put(TodoItem.COLUMN_NAME_TODOITEM_NAME, name);
                values.put(TodoItem.COLUMN_NAME_TODOITEM_DESCRIP, description);
                values.put(TodoItem.COLUMN_NAME_TODOITEM_PRIORITY, priority);
                values.put(TodoItem.COLUMN_NAME_TODDUE_ONDATE, codate.toString());
                values.put(TodoItem.COLUMN_NAME_TODCOMPLETED_ONDATE, codate.toString());
                values.put(TodoItem.COLUMN_NAME_COMPLETED_BOOL, completed);
                long id = -1;
                try {
                    id = db.insert(TodoItem.TABLE_NAME, null, values);
                if(id !=-1){
                    boolean tried = true;
                    Cursor c = db.rawQuery("select * from todoitem", null);
//                    while(c.moveToNext()){
//
//
//                        ToDoItem todolistToDelete = new ToDoItem();
//                        todolistToDelete.id = c.getLong(c.getColumnIndexOrThrow(TodoItem.COLUMN_NAME_TODOITEM_ID));
//
////            String selection = TodoList.COLUMN_NAME_TODOLIST_ID + " LIKE ?";
////            String[] selectionarg ={ String.valueOf(todolistToDelete.id)};
//
//
////                        deleteArray.add(todolistToDelete);

//                    }
                }
                }catch(SQLiteDatabaseCorruptException e){
                    e.printStackTrace();
                }catch(SQLiteReadOnlyDatabaseException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }

            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

                onUpgrade(db, oldVersion, newVersion);
            }
        }
    }

    // TODOLIST
    public static abstract class TodoList implements BaseColumns{

        public static final String TABLE_NAME = "todolist";
        public static String COLUMN_NAME_TODOLIST_ID = "todolistid";
        public static String COLUMN_NAME_TODOLIST_TITLE = "todolisttitle";
        public static String COLUMN_NAME_TODOLIST_DATE_CREATED = "datecreated";

        //SQL HELPER
        private static final String DATE_TYPE = " DATETIME";
        private static final String TEXT_TYPE = " TEXT";
        private static final String  COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TodoList.TABLE_NAME + " (" +
                        TodoList.COLUMN_NAME_TODOLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TodoList.COLUMN_NAME_TODOLIST_TITLE + TEXT_TYPE + COMMA_SEP +
                        TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED + DATE_TYPE + " BIGINT" +
                        " )" ;

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                TodoList.TABLE_NAME;

        public static class TodoListReaderDbHelper extends SQLiteOpenHelper {

            //If you change the database schema, you must increment the database versio
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "TodoReader.db";


            public TodoListReaderDbHelper (Context context){
                super(context, DATABASE_NAME, null, DATABASE_VERSION);

            }

            public void onCreate(SQLiteDatabase db){

                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                db.execSQL(SQL_CREATE_ENTRIES);
                //TODO SEED ENTRIES
               SeedData(db);

            }
            public void SeedData(SQLiteDatabase db){
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                ContentValues values = new ContentValues();

                values.put(TodoList.COLUMN_NAME_TODOLIST_TITLE, "seed title");
                values.put(TodoList.COLUMN_NAME_TODOLIST_DATE_CREATED, date.toString());



                long id = -1;
                try {
                    id = db.insert(TodoList.TABLE_NAME, null, values);

                }catch(SQLiteDatabaseCorruptException e){
                    e.printStackTrace();
                }catch(SQLiteReadOnlyDatabaseException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }

            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

                onUpgrade(db, oldVersion, newVersion);
            }
        }
    }

    // TODOLISTITEMS
    public static abstract class TodoListItems implements  BaseColumns{

        public static final String TABLE_NAME = "todolistitems";
        public static String COLUMN_NAME_TODOLISTITEM_ID = "id";
        public static String COLUMN_NAME_TODOLIST_ID = "todolistid";
        public static String COLUMN_NAME_TODOITEM_ID = "todoitemid";

        //SQL HELPER
        private static final String TEXT_TYPE = " TEXT";
        private static final String  COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TodoListItems.TABLE_NAME + " (" +
                        TodoListItems.COLUMN_NAME_TODOLISTITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TodoListItems.COLUMN_NAME_TODOLIST_ID + " INTEGER," +
                        TodoListItems.COLUMN_NAME_TODOITEM_ID + " INTEGER" +
                        " )" ;

        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
                TodoListItems.TABLE_NAME;

        public  static class TodoListItemsReaderDbHelper extends SQLiteOpenHelper {

            //If you change the database schema, you must increment the database versio
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "TodoReader.db";


            public TodoListItemsReaderDbHelper (Context context){
                super(context, DATABASE_NAME, null, DATABASE_VERSION);

            }

            public void onCreate(SQLiteDatabase db){


                db.execSQL(SQL_CREATE_ENTRIES);
                //TODO CREATE SEED
                SeedData(db);
            }
            public void SeedData(SQLiteDatabase db){
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                ContentValues values = new ContentValues();

                values.put(TodoListItems.COLUMN_NAME_TODOITEM_ID, "1");
                values.put(TodoListItems.COLUMN_NAME_TODOLIST_ID, "1");
                long id = -1;
                try {
                    id = db.insert(TodoListItems.TABLE_NAME, null, values);

                }catch(SQLiteDatabaseCorruptException e){
                    e.printStackTrace();
                }catch(SQLiteReadOnlyDatabaseException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }

            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

                onUpgrade(db, oldVersion, newVersion);
            }
        }
    }


    // TODOPRIORITY
//    public static abstract class TodoPriority implements  BaseColumns {
//
//        public static final String TABLE_NAME = "todopriority";
//        public static String COLUMN_NAME_TODOPRIORITY_ID = "todolistid";
//        public static String COLUMN_NAME_TODOITEM_ID = "todoitemid";
//        public static String COLUMN_NAME_TODOITEM_PRIORITY = "todoitempriority";
//
//        //SQL HELPER
//        private static final String TEXT_TYPE = " TEXT";
//        private static final String  COMMA_SEP = ",";
//        private static final String SQL_CREATE_ENTRIES =
//                "CREATE TABLE " + TodoPriority.TABLE_NAME + " (" +
//                        TodoPriority.COLUMN_NAME_TODOPRIORITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                        TodoPriority.COLUMN_NAME_TODOITEM_ID + " INTEGER," +
//                        TodoPriority.COLUMN_NAME_TODOITEM_PRIORITY + TEXT_TYPE +
//                        " )" ;
//
//        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
//                TodoPriority.TABLE_NAME;
//
//        public class TodoPriorityReaderDbHelper extends SQLiteOpenHelper {
//
//            //If you change the database schema, you must increment the database versio
//            public static final int DATABASE_VERSION = 1;
//            public static final String DATABASE_NAME = "TodoReader.db";
//
//
//            public TodoPriorityReaderDbHelper (Context context){
//                super(context, DATABASE_NAME, null, DATABASE_VERSION);
//
//            }
//
//            public void onCreate(SQLiteDatabase db){
//
//
//                db.execSQL(SQL_CREATE_ENTRIES);
//
//
//            }
//
//            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//                // This database is only a cache for online data, so its upgrade policy is
//                // to simply to discard the data and start over
//                db.execSQL(SQL_DELETE_ENTRIES);
//                onCreate(db);
//            }
//
//            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
//
//                onUpgrade(db, oldVersion, newVersion);
//            }
//        }
//    }
//
//    // TODOCOMPLETEDDATE
//    public static abstract class TodoCompleted implements  BaseColumns {
//
//        public static final String TABLE_NAME = "todocompleted";
//        public static String COLUMN_NAME_TODOCOMPLETED_ID = "todocompletedid";
//        public static String COLUMN_NAME_TODOITEM_ID = "todoitemid";
//        public static String COLUMN_NAME_TODCOMPLETED_ONDATE = "todoitemcompleteddate";
//
//        //SQL HELPER
//        private static final String TEXT_TYPE = " DATE";
//        private static final String  COMMA_SEP = ",";
//        private static final String SQL_CREATE_ENTRIES =
//                "CREATE TABLE " + TodoCompleted.TABLE_NAME + " (" +
//                        TodoCompleted.COLUMN_NAME_TODOCOMPLETED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                        TodoCompleted.COLUMN_NAME_TODOITEM_ID + " INTEGER," +
//                        TodoCompleted.COLUMN_NAME_TODCOMPLETED_ONDATE + TEXT_TYPE +
//                        " )" ;
//
//        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
//                TodoCompleted.TABLE_NAME;
//
//        public class TodoCompletedReaderDbHelper extends SQLiteOpenHelper {
//
//            //If you change the database schema, you must increment the database versio
//            public static final int DATABASE_VERSION = 1;
//            public static final String DATABASE_NAME = "TodoReader.db";
//
//
//            public TodoCompletedReaderDbHelper (Context context){
//                super(context, DATABASE_NAME, null, DATABASE_VERSION);
//
//            }
//
//            public void onCreate(SQLiteDatabase db){
//
//
//                db.execSQL(SQL_CREATE_ENTRIES);
//
//
//            }
//
//            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//                // This database is only a cache for online data, so its upgrade policy is
//                // to simply to discard the data and start over
//                db.execSQL(SQL_DELETE_ENTRIES);
//                onCreate(db);
//            }
//
//            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
//
//                onUpgrade(db, oldVersion, newVersion);
//            }
//        }
//    }
//
//    // TODODUEDATE
//    public static abstract class TodoDueDate implements  BaseColumns {
//
//        public static final String TABLE_NAME = "tododuedate";
//        public static String COLUMN_NAME_TODODUEDATE_ID = "tododuedateid";
//        public static String COLUMN_NAME_TODOITEM_ID = "todoitemid";
//        public static String COLUMN_NAME_TODDUE_ONDATE = "todoitemduedate";
//
//        //SQL HELPER
//        private static final String TEXT_TYPE = " DATE";
//        private static final String  COMMA_SEP = ",";
//        private static final String SQL_CREATE_ENTRIES =
//                "CREATE TABLE " + TodoDueDate.TABLE_NAME + " (" +
//                        TodoDueDate.COLUMN_NAME_TODODUEDATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                        TodoDueDate.COLUMN_NAME_TODOITEM_ID + " INTEGER," +
//                        TodoDueDate.COLUMN_NAME_TODDUE_ONDATE + TEXT_TYPE +
//                        " )" ;
//
//        private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
//                TodoDueDate.TABLE_NAME;
//
//        public class TodoDueDateReaderDbHelper extends SQLiteOpenHelper {
//
//            //If you change the database schema, you must increment the database versio
//            public static final int DATABASE_VERSION = 1;
//            public static final String DATABASE_NAME = "TodoReader.db";
//
//
//            public TodoDueDateReaderDbHelper (Context context){
//                super(context, DATABASE_NAME, null, DATABASE_VERSION);
//
//            }
//
//            public void onCreate(SQLiteDatabase db){
//
//
//                db.execSQL(SQL_CREATE_ENTRIES);
//
//
//            }
//
//            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//                // This database is only a cache for online data, so its upgrade policy is
//                // to simply to discard the data and start over
//                db.execSQL(SQL_DELETE_ENTRIES);
//                onCreate(db);
//            }
//
//            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
//
//                onUpgrade(db, oldVersion, newVersion);
//            }
//        }



    }













