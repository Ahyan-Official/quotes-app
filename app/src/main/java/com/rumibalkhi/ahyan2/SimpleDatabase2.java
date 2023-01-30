package com.rumibalkhi.ahyan2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SimpleDatabase2 extends SQLiteOpenHelper {
    // declare require values
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "SimpleDB";
    private static final String TABLE_NAME = "SimpleTable";

    public SimpleDatabase2(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // declare table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_ISIMAGE = "isimage";
    private static final String KEY_TIME = "time";





    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
         String createDb = "CREATE TABLE "+TABLE_NAME+" ("+
                 KEY_ID+" INTEGER PRIMARY KEY,"+
                 KEY_TITLE+" TEXT,"+
                 KEY_IMAGE +" BLOB,"+
                 KEY_ISIMAGE +" TEXT"+
                 " )";
         db.execSQL(createDb);
    }

    // upgrade db if older version exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addNote(Note2 note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_TITLE,note.getName());
        v.put(KEY_IMAGE,note.getImage());
        v.put(KEY_ISIMAGE,note.getIsimage());

        // inserting data into db
        long ID = db.insert(TABLE_NAME,null,v);
        return  ID;
    }

    public Note getNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE, KEY_IMAGE, KEY_ISIMAGE,KEY_TIME};
       Cursor cursor=  db.query(TABLE_NAME,query,KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }
    public List<Note2> getAllNotes(){
        List<Note2> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Note2 note = new Note2();
                note.setId(Long.parseLong(cursor.getString(0)));
                note.setName(cursor.getString(1));
                note.setImage(cursor.getBlob(2));
                note.setIsimage(cursor.getString(3));
                allNotes.add(note);
            }while (cursor.moveToNext());
        }

        return allNotes;

    }

    public List<Note2> getAllNotesCategory(String cate){
        List<Note2> allNotes = new ArrayList<>();

        //String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+KEY_ID+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection= {KEY_ID, KEY_TITLE, KEY_IMAGE, KEY_ISIMAGE};
        String[] where={cate}; //this must be array

        //Cursor cursor = db.rawQuery( "select * from "+TABLE_NAME+" WHERE title = 'Me'", null );

        Cursor cursor= db.query(TABLE_NAME, projection, "name"+"=?", where, null, null, null);

//        Cursor cursor= db.query
//                (
//                        TABLE_NAME,
//                        new String[] { KEY_ID,KEY_TITLE, KEY_CONTENT,KEY_DATE,KEY_TIME },
//                        KEY_TITLE + "=" + cate,
//                        null, null, null, null, null
//                );
        //Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where title = '" + cate + "'" , null);
       // Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where title == '" +cate + "'" , null);

       // Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Note2 note = new Note2();
                note.setId(Long.parseLong(cursor.getString(0)));
                note.setName(cursor.getString(1));
                note.setImage(cursor.getBlob(2));
                note.setIsimage(cursor.getString(3));
                allNotes.add(note);
            }while (cursor.moveToNext());
        }

        return allNotes;

    }


    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getId());
        c.put(KEY_TITLE,note.getTitle());
        c.put(KEY_IMAGE,note.getContent());
        c.put(KEY_ISIMAGE,note.getDate());
        return db.update(TABLE_NAME,c,KEY_ID+"=?",new String[]{String.valueOf(note.getId())});
    }



    void deleteNote(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }





}
