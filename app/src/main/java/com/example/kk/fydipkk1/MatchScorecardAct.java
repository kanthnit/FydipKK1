package com.example.kk.fydipkk1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MatchScorecardAct extends AppCompatActivity {

    TextView player1;
    TextView player2;
    TextView score1;
    TextView score2;
    MyDBHandler dbHandler;
    long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_scorecard);

        Bundle matchID = getIntent().getExtras();
        if(matchID == null){
            return;
        }
        id = matchID.getLong("id");

        player1 = (TextView) findViewById(R.id.textViewPlayer1);
        player2 = (TextView) findViewById(R.id.textViewPlayer2);
        score1 = (TextView) findViewById(R.id.textViewScore1);
        score2 = (TextView) findViewById(R.id.textViewScore2);

        dbHandler = new MyDBHandler(this, null, null, 1);

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String[] queryCols=new String[]{MyDBHandler.COLUMN_PLAYER1, MyDBHandler.COLUMN_PLAYER2, MyDBHandler.COLUMN_POINTS1, MyDBHandler.COLUMN_POINTS2};
        Cursor c = db.query(MyDBHandler.TABLE_MATCH, queryCols, MyDBHandler.COLUMN_MATCH_ID + " = " + id, null, null, null, null);
        c.moveToFirst();
        String strPlayer1 = c.getString(c.getColumnIndex(MyDBHandler.COLUMN_PLAYER1));
        String strPlayer2 = c.getString(c.getColumnIndex(MyDBHandler.COLUMN_PLAYER2));
        int strScore1 = c.getInt(c.getColumnIndex(MyDBHandler.COLUMN_POINTS1));
        int strScore2 = c.getInt(c.getColumnIndex(MyDBHandler.COLUMN_POINTS2));

        player1.setText(strPlayer1);
        player2.setText(strPlayer2);
        score1.setText(String.valueOf(strScore1));
        score2.setText(String.valueOf(strScore2));

        c.close();
    }

    public void onButtCLick(View view) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String[] queryCols = new String[]{MyDBHandler.COLUMN_POINTS1, MyDBHandler.COLUMN_POINTS2};
        Cursor c = db.query(MyDBHandler.TABLE_MATCH, queryCols, MyDBHandler.COLUMN_MATCH_ID + " = " + id , null, null, null, null);
        c.moveToFirst();
        int strScore1 = c.getInt(c.getColumnIndex(MyDBHandler.COLUMN_POINTS1));
        int strScore2 = c.getInt(c.getColumnIndex(MyDBHandler.COLUMN_POINTS2));
        String query = "";
        switch (view.getId()) {
            case (R.id.buttPlus1):
                strScore1++;
                query = "UPDATE " + MyDBHandler.TABLE_MATCH +
                        " SET " + MyDBHandler.COLUMN_POINTS1 + "=" + strScore1 +
                        " WHERE " + MyDBHandler.COLUMN_MATCH_ID + "=" + id;
                db.execSQL(query);
                break;
            case (R.id.buttPlus2):
                strScore2++;
                query = "UPDATE " + MyDBHandler.TABLE_MATCH +
                        " SET " + MyDBHandler.COLUMN_POINTS2 + "=" + strScore1 +
                        " WHERE " + MyDBHandler.COLUMN_MATCH_ID + "=" + id;
                db.execSQL(query);
                break;
            case (R.id.buttMinus1):
                if(strScore1 > 0) {
                    strScore1--;
                    query = "UPDATE " + MyDBHandler.TABLE_MATCH +
                            " SET " + MyDBHandler.COLUMN_POINTS1 + "=" + strScore1 +
                            " WHERE " + MyDBHandler.COLUMN_MATCH_ID + "=" + id;
                    db.execSQL(query);
                }
                break;
            case (R.id.buttMinus2):
                if(strScore2 > 0) {
                    strScore2--;
                    query = "UPDATE " + MyDBHandler.TABLE_MATCH +
                            " SET " + MyDBHandler.COLUMN_POINTS2 + "=" + strScore1 +
                            " WHERE " + MyDBHandler.COLUMN_MATCH_ID + "=" + id;
                    db.execSQL(query);
                }
                break;
        }
        //ContentValues cv = new ContentValues();
        //cv.put(MyDBHandler.COLUMN_POINTS1,strScore1);
        //cv.put(MyDBHandler.COLUMN_POINTS1, strScore2);
        //long a = db.update(MyDBHandler.TABLE_MATCH, cv, MyDBHandler.COLUMN_MATCH_ID + " = " + String.valueOf(id), null);
        // db.update(MyDBHandler.TABLE_MATCH, cv, MyDBHandler.COLUMN_MATCH_ID + "=?", new String[]{String.valueOf(id) + ""});

        score1.setText(String.valueOf(strScore1));
        score2.setText(String.valueOf(strScore2));

        c.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match_scorecard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
