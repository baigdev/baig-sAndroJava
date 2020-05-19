package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyContactsDataBase extends SQLiteOpenHelper {

    private final static String DB_NAME="Contacs";
    private final static String DB_TABLE="Data";
    private final static int DB_VERSION=2;

    SQLiteDatabase myDB;
    Context ctx;
    public MyContactsDataBase(Context ct)
    {
        super(ct,DB_NAME,null,DB_VERSION);
        ctx=ct;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+DB_TABLE+ "(_id integer primary key autoincrement,name,number)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+DB_TABLE);
        onCreate(db);
    }

    public void saveData(String name,String number){
        try {
            myDB=getWritableDatabase();
            myDB.execSQL("insert into "+DB_TABLE+" (name,number) values('"+name+"','"+number+"');");
            Toast.makeText(ctx,"Saved Successfully",Toast.LENGTH_SHORT).show();
        }catch (SQLException e)
        {
            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    public void getData(){

        myDB=getReadableDatabase();
       Cursor cursor =myDB.rawQuery("select * from "+DB_TABLE,null);
       MainActivity.nameList.clear();
       MainActivity.numberList.clear();
       MainActivity.id_List.clear();

       while (cursor.moveToNext())
       {
           int id=cursor.getInt(0);
           String name=cursor.getString(1);
           String number=cursor.getString(2);

           MainActivity.nameList.add(name);
           MainActivity.numberList.add(number);
           MainActivity.id_List.add(id);
       }
    }

    public void deleteData(int getId){
        myDB=getWritableDatabase();
        String where="_id like ?";
        String whereArg[]={String.valueOf(getId)};
        int code= myDB.delete(DB_TABLE,where,whereArg);
        getData();
        if (code==1)
        Toast.makeText(ctx,"Successfully Deleted",Toast.LENGTH_SHORT).show();
    }

    public void deleteAll(){

        myDB=getWritableDatabase();
        myDB.execSQL("delete from "+DB_TABLE);
        getData();

    }
    public void upDate(int getId,String getName,String getNum)
    {
        myDB=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",getName);
        values.put("number",getNum);
        String where=" _id like ?";
        String whereArg[]={String.valueOf(getId)};
        myDB.update(DB_TABLE,values,where,whereArg);
        getData();
        Toast.makeText(ctx,"Done",Toast.LENGTH_SHORT).show();
    }
}
