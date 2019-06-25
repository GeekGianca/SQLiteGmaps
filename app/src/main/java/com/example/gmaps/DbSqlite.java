package com.example.gmaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DbSqlite extends SQLiteOpenHelper {
    //Database
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "PersonasPos";

    //Table
    private static final String TABLA_NOMBRE = "Posiciones";
    private static final String KEY_ID = "Id";
    private static final String KEY_NOMBRE = "Nombre";
    private static final String KEY_DIRECCION = "Direccion";
    private static final String KEY_LAT = "Latitud";
    private static final String KEY_LNG = "Longitud";

    public DbSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLA_NOMBRE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOMBRE + " TEXT,"
                + KEY_DIRECCION + " TEXT,"
                + KEY_LAT + " REAL,"
                + KEY_LNG + " REAL)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRE);
        onCreate(sqLiteDatabase);

    }

    //CRUD Persons
    public void addPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, person.getNombre());
        values.put(KEY_DIRECCION, person.getDireccion());
        values.put(KEY_LAT, person.getLat());
        values.put(KEY_LNG, person.getLng());

        db.insert(TABLA_NOMBRE, null, values);
        db.close();
    }

    public int updatePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, person.getNombre());
        values.put(KEY_DIRECCION, person.getDireccion());
        values.put(KEY_LAT, person.getLat());
        values.put(KEY_LNG, person.getLng());

        return db.update(TABLA_NOMBRE, values, KEY_ID + " =?", new String[]{String.valueOf(person.getId())});
    }

    public void deletePerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_NOMBRE, KEY_ID + " =?", new String[]{String.valueOf(person.getId())});
        db.close();
    }

    public void deletePerson() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_NOMBRE, null, null);
        db.close();
    }

    public Person getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLA_NOMBRE, new String[]{KEY_ID, KEY_NOMBRE, KEY_LAT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return new Person(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getFloat(4));

    }

    public List<Person> getAllPerson() {
        List<Person> lstPersons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLA_NOMBRE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Person person = new Person();
                person.setId(cursor.getInt(0));
                person.setNombre(cursor.getString(1));
                person.setDireccion(cursor.getString(2));
                person.setLat(cursor.getFloat(3));
                person.setLng(cursor.getFloat(4));
                lstPersons.add(person);
            }
            while (cursor.moveToNext());
        }
        return lstPersons;
    }

}
