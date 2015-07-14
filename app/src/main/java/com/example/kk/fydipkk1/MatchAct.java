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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MatchAct extends AppCompatActivity {

    EditText player1;
    EditText player2;
    MyDBHandler dbHandler;
    String username = "";
    TextView matchList;
    MatchlistAdapter matchlistAdapater;
    ListView matchlistListView;
    RadioGroup radioGroupNumber;
    RadioGroup radioGroupMode;


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

        radioGroupNumber = (RadioGroup) findViewById(R.id.radioGroupNumber);
        radioGroupMode = (RadioGroup) findViewById(R.id.radioGroupMode);

        dbHandler = new MyDBHandler(this, null, null, 1);

        //SQLiteDatabase db = new MyDBHandler(this).getWritableDatabase();
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        matchlistAdapater = new MatchlistAdapter(this, getMatchList());
        matchlistListView = (ListView) findViewById(R.id.matchlistView);

        //radioNumberOnclickListen();
        //radioModeOnclickListen();
        radioGroupNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioSingles:
                        Toast.makeText(getApplicationContext(), "Singles", Toast.LENGTH_SHORT).show();
                        player1.setHint("player1");
                        player2.setHint("player2");
                        break;
                    case R.id.radioDoubles:
                        Toast.makeText(getApplicationContext(), "Doubles", Toast.LENGTH_SHORT).show();
                        player1.setHint("player1/player2");
                        player2.setHint("player1/player2");
                        break;
                }
            }
        });

        radioGroupMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioLeague:
                        Toast.makeText(getApplicationContext(), "League", Toast.LENGTH_SHORT).show();
                        //player1.setHint("player1/player2");
                        break;
                    case R.id.radioEliminator:
                        Toast.makeText(getApplicationContext(), "Eliminator", Toast.LENGTH_SHORT).show();
                        //player2.setHint("player1/player2");
                        break;
                }
            }
        });

        matchlistListView.setAdapter(matchlistAdapater);
        matchlistListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //String name = String.valueOf(parent.getItemAtPosition(position));
                        Match match = (Match) matchlistAdapater.getItem(position);

                        //Toast.makeText(getApplicationContext(), String.valueOf(match.get_id()) , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MatchAct.this, MatchScorecardAct.class);
                        i.putExtra("id", match.get_id());
                        startActivity(i);
                    }
                }
        );

        db.close();

    }

    public void radioNumberOnclickListen() {
        radioGroupNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioSingles:
                        Toast.makeText(getApplicationContext(), "Singles", Toast.LENGTH_LONG).show();
                        player1.setHint("player1/player2");
                        break;
                    case R.id.radioDoubles:
                        Toast.makeText(getApplicationContext(), "Doubles", Toast.LENGTH_LONG).show();
                        player2.setHint("player1/player2");
                        break;
                }
            }
        });
    }

    public void radioModeOnclickListen() {
        radioGroupNumber.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioLeague:
                        Toast.makeText(getApplicationContext(), "League", Toast.LENGTH_LONG).show();
                        //player1.setHint("player1/player2");
                        break;
                    case R.id.radioEliminator:
                        Toast.makeText(getApplicationContext(), "Eliminator", Toast.LENGTH_LONG).show();
                        //player2.setHint("player1/player2");
                        break;
                }
            }
        });
    }

    public List<Match> getMatchList() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        //String[] queryCols=new String[]{MyDBHandler.COLUMN_MATCH_ID, MyDBHandler.COLUMN_PLAYER1, MyDBHandler.COLUMN_PLAYER2,MyDBHandler.COLUMN_USER,};
        Cursor c = db.query(MyDBHandler.TABLE_MATCH, null, MyDBHandler.COLUMN_USER + " = '" + username + "'", null, null, null, null);

        List<Match> matchList = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                Match match = new Match(c.getString(c.getColumnIndex(MyDBHandler.COLUMN_PLAYER1)),
                        c.getString(c.getColumnIndex(MyDBHandler.COLUMN_PLAYER2)),
                        c.getString(c.getColumnIndex(MyDBHandler.COLUMN_USER)));
                int strScore1 = c.getInt(c.getColumnIndex(MyDBHandler.COLUMN_POINTS1));
                int strScore2 = c.getInt(c.getColumnIndex(MyDBHandler.COLUMN_POINTS2));
                match.set_points1(strScore1);
                match.set_points2(strScore2);
                match.set_id(c.getInt(c.getColumnIndex(MyDBHandler.COLUMN_MATCH_ID)));
                matchList.add(match);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return matchList;
    }

    public void addMatch(View view) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Match match = new Match(player1.getText().toString(),player2.getText().toString(), username);
        long id  = dbHandler.addMatch(match);
        Toast.makeText(getApplicationContext(), "Match added to list" , Toast.LENGTH_SHORT).show();
        printSpinner();

        if(id != -1)
        {
           // update data in our adapter
            //matchlistAdapater.getData().clear();
            //matchlistAdapater.getData().addAll(objects);
            // fire the event
            //matchlistAdapater.notifyDataSetChanged();
            matchlistAdapater = new MatchlistAdapter(this, getMatchList());
            matchlistListView.setAdapter(matchlistAdapater);
        }
        db.close();
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
