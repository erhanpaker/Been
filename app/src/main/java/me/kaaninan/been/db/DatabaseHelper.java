package me.kaaninan.been.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{


    public static final String
            DATABASE_CREATE_KAYIT = "CREATE TABLE " + DatabaseContract.Kayit.TABLE_NAME + " ("
            + DatabaseContract.Kayit.ID + " integer primary key autoincrement,"
            + DatabaseContract.Kayit.COLUMN_TEXT + " text,"
            + DatabaseContract.Kayit.COLUMN_TARIH + " datetime,"
            + DatabaseContract.Kayit.COLUMN_CHANGE + " boolean,"
            + DatabaseContract.Kayit.COLUMN_SON_DUZEN + " datetime,"
            + DatabaseContract.Kayit.COLUMN_RESIM + " bitmap, "
            + DatabaseContract.Kayit.COLUMN_RESIM2 + " bitmap );";


    public static final String DATABASE_DROP_KAYIT ="DROP TABLE IF EXISTS " + DatabaseContract.Kayit.TABLE_NAME;

    public DatabaseHelper(Context context){
        super(context, DatabaseContract.DATABASE_NAME , null, DatabaseContract.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_KAYIT);

		// Ekleme
		db.execSQL("INSERT INTO "+DatabaseContract.Kayit.TABLE_NAME+" ("+DatabaseContract.Kayit.COLUMN_TEXT+") VALUES ('Nakit')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DatabaseHelper", "Veritabani " + oldVersion + "\'dan " + newVersion + "\'a guncelleniyor.");
        db.execSQL(DATABASE_DROP_KAYIT);
        onCreate(db);
    }

}
