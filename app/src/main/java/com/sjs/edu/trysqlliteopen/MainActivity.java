package com.sjs.edu.trysqlliteopen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyDBOpenHelper dbHelper;
    SQLiteDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDBOpenHelper(this, "sjs.db",null,1);
        mdb = dbHelper.getWritableDatabase();
        ((Button)findViewById(R.id.buttonInsert)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonRead)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id;
        String country, capital, query;
        EditText editCountry, editCapital;
        TextView textViewResult;

        switch(v.getId()){
            case R.id.buttonInsert:
                editCountry = (EditText) findViewById(R.id.editTextCountry);
                country = editCountry.getText().toString();
                editCapital = (EditText) findViewById(R.id.editTextCity);
                capital = editCapital.getText().toString();

                query = "INSERT INTO awe_country VALUES(null,'" + country + "', '" + capital + "');";
                mdb.execSQL(query);

                editCountry.setText("");
                editCapital.setText("");
                break;
            case R.id.buttonRead:
                textViewResult = findViewById(R.id.textViewResult);
                query = "SELECT * FROM awe_country;";
                Cursor cursor = mdb.rawQuery(query,null);
                String str = "";

                while (cursor.moveToNext()) {
                    id = cursor.getInt(0);
                    country = cursor.getString(cursor.getColumnIndex("country"));
                    capital = cursor.getString(cursor.getColumnIndex("capital"));
                    str += (id + " : " + country + " - " + capital + "\n");
                }
                if(str.length()>0){
                    textViewResult.setText(str);
                }else{
                    Toast.makeText(getApplicationContext(),"Warning Empty DB",
                            Toast.LENGTH_LONG).show();
                    textViewResult.setText("");
                }

        }
    }
}
