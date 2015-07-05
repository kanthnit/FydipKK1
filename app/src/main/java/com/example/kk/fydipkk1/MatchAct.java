package com.example.kk.fydipkk1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MatchAct extends AppCompatActivity {

    EditText player1;
    EditText player2;
    MyDBHandler dbHandler;
    String username = "";
    TextView matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Bundle user = getIntent().getExtras();
        if(user == null){
            return;
        }
        username = user.getString("username");

        player1 = (EditText) findViewById(R.id.editTextPlayer1);
        player2 = (EditText) findViewById(R.id.editTextPlayer2);
        matchList = (TextView) findViewById(R.id.textViewMatchList);

        dbHandler = new MyDBHandler(this, null, null, 1);

       //SQLiteDatabase db = new MyDBHandler(this).getWritableDatabase();
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        printSpinner();
        String[] queryCols=new String[]{MyDBHandler.COLUMN_MATCH_ID, MyDBHandler.COLUMN_PLAYER1, MyDBHandler.COLUMN_PLAYER2};
        Cursor c = db.query(MyDBHandler.TABLE_MATCH, queryCols, MyDBHandler.COLUMN_USER + " = '" + username + "'", null, null, null, null);
        // make an adapter from the cursor
        String[] from = new String[] { MyDBHandler.COLUMN_PLAYER1 , MyDBHandler.COLUMN_PLAYER2};
        int[] to = new int[] {android.R.id.text1};
//        @SuppressWarnings("deprecation")
        //SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);
//        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.activity_match,c,from,to);
//        sca.setDropDownViewResource(R.layout.activity_match);
//
//        Spinner spin = (Spinner) findViewById(R.id.spinnerListmatches);
//        spin.setAdapter(sca);
//
//        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "Selected ID=" + id, Toast.LENGTH_LONG).show();
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
        db.close();
        c.close();

    }

    public void addMatch(View view) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Match match = new Match(player1.getText().toString(),player2.getText().toString(), username);
        long id  = dbHandler.addMatch(match);
        Toast.makeText(getApplicationContext(), "Match added to list" , Toast.LENGTH_LONG).show();
        printSpinner();
        db.close();
        if(id != -1)
        {
            Intent i = new Intent(this, MatchScorecardAct.class);
            i.putExtra("id", id);
            startActivity(i);
        }
        else
            return;
        //updateSpinner();
    }

    public void printSpinner() {
        String list = dbHandler.databaseMatchToString();
        //matchList.setText(list);
    }

    public void updateSpinner()
    {
        Cursor c = dbHandler.getMatchesList(username);
        // make an adapter from the cursor
        String[] from = new String[] { MyDBHandler.COLUMN_PLAYER1,MyDBHandler.COLUMN_PLAYER2};
        int[] to = new int[] {android.R.id.text1};
        @SuppressWarnings("deprecation")
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.activity_match,c,from,to);
        sca.setDropDownViewResource(R.layout.activity_match);
        Spinner spin = (Spinner) findViewById(R.id.spinnerListmatches);
        spin.setAdapter(sca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_match, menu);
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
