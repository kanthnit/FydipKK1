package com.example.kk.fydipkk1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView kkText;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);

        kkText = (TextView) findViewById(R.id.kkText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();
    }

    public void addUser(View view) {
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Username and Password", Toast.LENGTH_LONG).show();
            return;
        }
        User user = new User(username.getText().toString(),password.getText().toString());
        dbHandler.addUser(user);
        Toast.makeText(getApplicationContext(), "User Registered" , Toast.LENGTH_LONG).show();
        printDatabase();
    }

    public void onLogin(View view){
        User user = new User(username.getText().toString(),password.getText().toString());
        boolean isExists = true;
        //Intent i = new Intent(this,kkAct.class);
        //startActivity(i);
        isExists = dbHandler.verifyUser(user);
        if(isExists) {
            Intent i = new Intent(this,MatchAct.class);
            String msg = username.getText().toString();
            i.putExtra("username", msg);
            startActivity(i);
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid Password/Username. Please try again." , Toast.LENGTH_LONG).show();
        }
    }

//    public void deleteButton(View view) {
//        String product = kkInput.getText().toString();
//        dbHandler.deleteProduct(product);
//        printDatabase();
//    }

    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        //kkText.setText(dbString);
    }

}
