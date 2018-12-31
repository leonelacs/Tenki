package com.leonelacs.tenki;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChiikiDBO extends SQLiteOpenHelper {

    private Context dboContext;
    public static final String CREATE_CHIIKI = "create table Chiiki ("
            + "id integer primary key autoincrement, "
            + "shou text, "
            + "shi text, "
            + "ku text)";

    public ChiikiDBO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dboContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CHIIKI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
