package com.example.kk.fydipkk1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "fydip.db";

    public static final String TABLE_USER = "users";
    public static final String COLUMN_USER = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_MATCH = "match";
    public static final String COLUMN_MATCH_ID = "id";
    public static final String COLUMN_PLAYER1 = "player1";
    public static final String COLUMN_PLAYER2 = "player2";
    public static final String COLUMN_POINTS1 = "points1";
    public static final String COLUMN_POINTS2 = "points2";

    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_USER + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                "PRIMARY KEY (" + COLUMN_USER + ")" +
                ");";
        db.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE_MATCH + "(" +
                COLUMN_MATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_PLAYER1 + " TEXT, " +
                COLUMN_PLAYER2 + " TEXT, " +
                COLUMN_POINTS1 + " INTEGER DEFAULT 0, " +
                COLUMN_POINTS2 + " INTEGER DEFAULT 0, " +
                //"PRIMARY KEY (" + COLUMN_MATCH_ID + ")," +
                "FOREIGN KEY (" + COLUMN_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER + ")" +
                ");";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
        onCreate(db);
    }

    //Add a new row to the database
    public void addUser(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, user.get_username());
        values.put(COLUMN_PASSWORD, user.get_password());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean verifyUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        //String query = "SELECT " + COLUMN_PASSWORD + "FROM " + TABLE_USER + " WHERE " + COLUMN_USER + "=\'" + user.get_username() + "\';" ;
        String[] queryCols = new String[]{MyDBHandler.COLUMN_PASSWORD};
        Cursor cursor = db.query(TABLE_USER, queryCols, MyDBHandler.COLUMN_USER + "= '" + user.get_username() + "'", null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String strPassword = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_PASSWORD));
            if(strPassword.equals(user.get_password()))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public long addMatch(Match match){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, match.get_username());
        values.put(COLUMN_PLAYER1, match.get_player1());
        values.put(COLUMN_PLAYER2, match.get_player2());
        values.put(COLUMN_POINTS1, match.get_points1());
        values.put(COLUMN_POINTS2, match.get_points2());
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE_MATCH, null, values);
        db.close();
        return id;
    }

    public Cursor getMatchesList(String username) {
        String data = "";
        SQLiteDatabase db = getWritableDatabase();
        String[] queryCols=new String[]{"matchID", "player1", "player2"};
        Cursor cursor = db.query(TABLE_MATCH, queryCols, COLUMN_USER + " = " + username, null, null, null, null);
        return cursor;
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("username")) != null) {
                dbString += c.getString(c.getColumnIndex("username"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }

    public String databaseMatchToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MATCH + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("username")) != null) {
                dbString += c.getString(c.getColumnIndex("username"));
                dbString += c.getString(c.getColumnIndex("player1"));
                dbString += c.getString(c.getColumnIndex("player2"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        c.close();
        return dbString;
    }
}
