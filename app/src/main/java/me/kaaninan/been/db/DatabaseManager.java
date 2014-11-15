package me.kaaninan.been.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import me.kaaninan.been.constructor.KayitConstructor;


public class DatabaseManager {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    // INSERT

    public void ekleKayit(String message, String tarih) {

        db = helper.getWritableDatabase();

        ContentValues satir = new ContentValues();
        satir.put(DatabaseContract.Kayit.COLUMN_TEXT, message);
        satir.put(DatabaseContract.Kayit.COLUMN_TARIH, getDate());
        //satir.put(DatabaseContract.Kayit.COLUMN_RESIM, String.valueOf(resim1));
    	/*
    	if(!tarih.isEmpty()){
    		String[] dbTarih = convertDateTimeNoktaTire(tarih);
    		satir.put(DatabaseContract.Kayit.COLUMN_TARIH, dbTarih[0]+" "+dbTarih[1]);
    	}else{
    		satir.put(DatabaseContract.Kayit.COLUMN_TARIH, getDateTime());
    	}
    	*/
        db.insert(DatabaseContract.Kayit.TABLE_NAME, null, satir);
    }



    // TARİH İSLEMLERİ

    public String getDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = dateFormat.format(c.getTime());
        return strDate;
    }

    // Sadece SQL için
    public String getDateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(c.getTime());
        return strDate;
    }

    // DB formatindaki tarihi ikiye bölme
    public String[] splitDateTime(String date){
        String[] tokens = date.split("(?<=\\G.{10})");
        String[] yeni = {tokens[0],tokens[1]};
        return yeni;
    }


    // DB format˝ndaki tarihi "."ya Áevir
    public String[] convertDateTimeTireNokta(String date){
        String[] tokens = date.split("(?<=\\G.{10})");
        String date2 = convertDateTireNokta(tokens[0]);
        String[] yeni = {date2,tokens[1]};
        return yeni;
    }

    public String convertDateTireNokta(String date){
        String delims = "[-]+";
        String[] tokens = date.split(delims);
        String newDate = tokens[2]+"."+tokens[1]+"."+tokens[0];
        return newDate;
    }


    // getDateTime'˝ DB format˝na Áevir
    public String[] convertDateTimeNoktaTire(String date){
        String[] tokens = date.split("(?<=\\G.{10})");
        String date2 = convertDateNoktaTire(tokens[0]);
        String[] yeni = {date2,tokens[1]};
        return yeni;
    }

    public String convertDateNoktaTire(String date){
        String delims = "[.]+";
        String[] tokens = date.split(delims);
        String newDate = tokens[2]+"-"+tokens[1]+"-"+tokens[0];
        return newDate;
    }




    // DELETE

    public void silKayit(int position){

        db = helper.getReadableDatabase();
        String where = DatabaseContract.Kayit.ID + "=" + position;
        db.delete(DatabaseContract.Kayit.TABLE_NAME, where, null);

    }


    // Kayitlar ##############################################################

    private String selectQuery;

    public ArrayList<KayitConstructor> getKayitlar() {

        ArrayList<KayitConstructor> kayitlar = new ArrayList<KayitConstructor>();

        selectQuery = "SELECT * FROM " +DatabaseContract.Kayit.TABLE_NAME;

        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        //Cursor c = sorgulaKayitlar();

        if (c.moveToFirst()) {
            do {

                KayitConstructor kayit = new KayitConstructor();
                kayit.id = c.getInt(c.getColumnIndex(DatabaseContract.Kayit.ID));
                kayit.text = c.getString(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_TEXT));
                kayit.tarih = c.getString(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_TARIH));
                //kayit.photo = (Bitmap) c.getExtras().get(c.getString(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_RESIM)));
                //kayit.photo2 = (Bitmap) c.getExtras().get(c.getString(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_RESIM2)));

                kayitlar.add(kayit);

            } while (c.moveToNext());
        }
        return kayitlar;
    }


	/*
	public int kayitlarCount(String tarih){
		Cursor c = sorgulaKayitlar();
		int count = 0;
		if(c.moveToNext()){
			do{
				count = count+1;
			}while(c.moveToNext());
		}
		return count;
	}


	public KayitConstructor getKayit(long kayit_id) {
	    db = helper.getReadableDatabase();

	    String selectQuery = "SELECT  * FROM " + DatabaseContract.Kayit.TABLE_NAME + " WHERE "
	            + DatabaseContract.Kayit.COLUMN_HESAP_ID + " = " + kayit_id;


	    //Cursor c = db.rawQuery(selectQuery, null);
	    Cursor c = db.rawQuery(selectQuery, null);

	    if (c != null)
	        c.moveToFirst();

	    KayitConstructor td = new KayitConstructor();
	    td.setHesapId(c.getInt(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_HESAP_ID)));
	    td.setTur((c.getString(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_TUR))));
	    td.setTutar((c.getDouble(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_TUTAR))));
	    td.setNot((c.getString(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_NOT))));
	    td.setTarih((c.getString(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_NOT))));
	    td.setHesapId((c.getInt(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_NOT))));
	    td.setKategoriId((c.getInt(c.getColumnIndex(DatabaseContract.Kayit.COLUMN_NOT))));

	    return td;
	}
	*/
    // End Kayitlar ##############################################################

}
