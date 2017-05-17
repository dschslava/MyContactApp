package com.example.stephenyang.mycontactapp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
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
    EditText editPhone;
    EditText editAddress;
    Button btnAddData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editText_Name);
        editPhone = (EditText) findViewById(R.id.editText_Phone);
        editAddress = (EditText) findViewById(R.id.editText_Address);
    }

    public void addData(View v){
        boolean isInserted = (myDb.insertData(editName.getText().toString(),editPhone.getText().toString(),editAddress.getText().toString()));
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
            Log.d("MyContact", "No data found");
            Context context = getApplicationContext();
            CharSequence text = "No data found";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        //loop with Cursor moveToNext method
        //append each column to buffer
        //use getString

        for (int i = 1; i <= res.getCount(); i++ ) {
            buffer.append(res.getString(i));
            res.moveToNext();
        }

        showMessage("Contacts", buffer.toString());
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
