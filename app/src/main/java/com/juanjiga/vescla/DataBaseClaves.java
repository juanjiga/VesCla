package com.juanjiga.vescla;

// basado en Codigo Alonso, equivalente
// a la clase DataBaseAdapter

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseClaves {

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public DataBaseClaves(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    private void openReadableDB() {
        db = dataBaseHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dataBaseHelper.getWritableDatabase();
    }

    private void closeDB() {
        if(db!=null){
            db.close();
        }
    }


    //clase interna Helper
    private static class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context) {
            super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Constantes.DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLA_CLAVES);
            onCreate(db);
        }

    }
}