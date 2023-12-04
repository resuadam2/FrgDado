package com.resuadam2.frgdado;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "partidas";
    private static final int DB_VERSION = 1;

    private static final String TABLE_PARTIDAS = "partidas";
    private static final String COL_NAME_ID = "id";
    private static final String COL_NAME_TIRADAS = "tiradas";
    private static final String COL_NAME_RACHA = "racha";
    private static final String COL_NAME_DIFICULTAD = "dificultad";
    private static final String COL_NAME_FECHA = "fecha";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_PARTIDAS + " (" +
                COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME_TIRADAS + " INTEGER, " +
                COL_NAME_RACHA + " INTEGER, " +
                COL_NAME_DIFICULTAD + " INTEGER, " +
                COL_NAME_FECHA + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + TABLE_PARTIDAS;
        db.execSQL(sql);
        onCreate(db);

    }

    public void insertPartida(int tiradas, int racha, int dificultad) {
        String sql = "INSERT INTO " + TABLE_PARTIDAS + " (" +
                COL_NAME_TIRADAS + ", " +
                COL_NAME_RACHA + ", " +
                COL_NAME_DIFICULTAD + ") VALUES (" +
                tiradas + ", " +
                racha + ", " +
                dificultad + ")";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void deletePartidasPorDificultad(int dificultad) {
        String sql = "DELETE FROM " + TABLE_PARTIDAS + " WHERE " + COL_NAME_DIFICULTAD + " = " + dificultad;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor getPartidasPorDificultad(int dificultad) {
        String sql = "SELECT " + COL_NAME_FECHA +
                "," + COL_NAME_TIRADAS
                + "," + COL_NAME_RACHA
                + " FROM " + TABLE_PARTIDAS
                + " WHERE " + COL_NAME_DIFICULTAD + " = " + dificultad
                + " ORDER BY " + COL_NAME_TIRADAS + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }
}
