package com.example.personnelmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.personnelmanagement.MainActivity;
import com.example.personnelmanagement.PersonnelInfo;
import com.example.personnelmanagement.R;

public class PersonnelReg extends AppCompatActivity {

    DBManager dbManager;

    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personnel_reg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            return true;
        } else if (id == R.id.action_settings1) {
            Intent it = new Intent(this, PersonnelList.class);
            startActivity(it);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void register(View v) {
        EditText et_name = (EditText) findViewById(R.id.name);
        String str_name = et_name.getText().toString();

        RadioGroup rg_gender = (RadioGroup) findViewById(R.id.gender);
        RadioButton rb_gender;
        String str_gender = "";
        if (rg_gender.getCheckedRadioButtonId() == R.id.male) {
            rb_gender = (RadioButton) findViewById(R.id.male);
            str_gender = rb_gender.getText().toString();
        }
        if (rg_gender.getCheckedRadioButtonId() == R.id.female) {
            rb_gender = (RadioButton) findViewById(R.id.female);
            str_gender = rb_gender.getText().toString();
        }


        EditText et_age = (EditText)findViewById(R.id.age) ;
        String str_age = et_age.getText().toString();

        EditText et_tel = (EditText)findViewById(R.id.tel);
        String str_tel = et_tel.getText().toString();
        try {
            dbManager = new DBManager(this);
            sqLiteDatabase = dbManager.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("name", str_name);
            values.put("gender", str_gender);
            values.put("age", str_age);
            values.put("tel", str_tel);

            long newRowId = sqLiteDatabase.insert("Personnel", null, values);
            sqLiteDatabase.close();
            dbManager.close();

            Intent it = new Intent(this, PersonnelInfo.class);
            it.putExtra("it_name", str_name);
            startActivity(it);
            finish();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}