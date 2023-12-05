package com.resuadam2.frgdado;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "partidas"; // Nombre de la base de datos
    private static final int DB_VERSION = 1; // Versión de la base de datos

    private static final String TABLE_PARTIDAS = "partidas"; // Nombre de la tabla
    private static final String COL_NAME_ID = "id"; // Nombre de la columna id
    private static final String COL_NAME_TIRADAS = "tiradas"; // Nombre de la columna tiradas
    private static final String COL_NAME_RACHA = "racha"; // Nombre de la columna racha
    private static final String COL_NAME_DIFICULTAD = "dificultad"; // Nombre de la columna dificultad
    private static final String COL_NAME_FECHA = "fecha"; // Nombre de la columna fecha

    /**
     * Constructor de la clase DBManager
     * @param context Contexto de la aplicación
     */
    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Método que se ejecuta cuando se crea la base de datos
     * @param db The database.
     */
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

    /**
     * Método que se ejecuta cuando se actualiza la base de datos
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + TABLE_PARTIDAS;
        db.execSQL(sql);
        onCreate(db);

    }

    /**
     * Método que inserta una partida en la base de datos
     * @param tiradas Número de tiradas
     * @param racha Racha máxima
     * @param dificultad Número de caras
     */
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

    /**
     * Método que borra las partidas de una dificultad
     * @param dificultad Número de caras
     */
    public void deletePartidasPorDificultad(int dificultad) {
        String sql = "DELETE FROM " + TABLE_PARTIDAS + " WHERE " + COL_NAME_DIFICULTAD + " = " + dificultad;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    /**
     * Método que devuelve las partidas de una dificultad
     * @param dificultad Número de caras
     * @return Cursor con las partidas
     */
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
