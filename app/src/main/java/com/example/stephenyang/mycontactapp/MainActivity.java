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
    EditText editSearch;
    Button btnAddData;
    String fields[] = {"ID", "Name", "Phone", "Address"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editText_Name);
        editPhone = (EditText) findViewById(R.id.editText_Phone);
        editAddress = (EditText) findViewById(R.id.editText_Address);
        editSearch = (EditText) findViewById((R.id.editText_search));
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
        editName.setText("");
        editPhone.setText("");
        editAddress.setText("");
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

        while(res.moveToNext()) {
            for(int i = 0; i<res.getColumnCount(); i++) {
                buffer.append(fields[i] + ": "+ res.getString(i) + "\n");
            }
            buffer.append("\n");
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

    public void searchByName (View v) {

        String search = editSearch.getText().toString();

        Cursor res = myDb.getAllData();

        StringBuffer buffer = new StringBuffer();
        StringBuffer bufferCopy = new StringBuffer();



        while(res.moveToNext()) {
            for (int i = 0; i < res.getCount(); i++) {
                if (res.getString(1).equals(search)) {
                    if(bufferCopy.indexOf(fields[0] + res.getString(0) + "\n") == -1) {
                        for (int field = 0; field < 4; field++) {
                            if(field != 0) {buffer.append(fields[field] + ": " + res.getString(field) + "\n");}
                            bufferCopy.append(fields[field] + res.getString(field) + "\n");
                        }
                        buffer.append("\n");
                    }
                }
            }
        }

        if (buffer.toString().equals("")) {
            showMessage("Search failed", " ");

        } else {
            showMessage("Search result:", buffer.toString());
        }

        editSearch.setText("");

    }
}
