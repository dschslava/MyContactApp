package com.example.stephenyang.mycontactapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName;
    Button btnAddData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editText_Name);
    }

    public void addData(View v){
        boolean isInserted = myDb.insertData(editName.getText().toString());

        if (isInserted){
            Log.d("MyContact", "Data insertion successful");
            //toast!!! data inserted correctly
            Context context = getApplicationContext();
            CharSequence text = "Contact added";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            Log.d("MyContact", "Data insertion unsuccessful");
            //toast :( data inserted incorrectly
            Context context = getApplicationContext();
            CharSequence text = "Contact add unsuccessful";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void viewData(View v){
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0){
            showMessage("Error", "No data found in database");
            //log message, toast
            return;
        }
        StringBuffer buffer = new StringBuffer();
        //loop with Cursor moveToNext method
        //append each column to buffer
        //use getString

        showMessage("Data", buffer.toString());
    }

    private void showMessage(String title, String message) {

    }
}
