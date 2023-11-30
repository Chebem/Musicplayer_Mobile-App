package com.example.personnelmanagement;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class PersonnelInfo extends AppCompatActivity {

    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;

    String str_name;


    String str_gender;

    int age;

    String str_tel;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 레이아웃 내의 뷰 인식
        TextView tv_name = (TextView) findViewById(R.id.name);
        TextView tv_gender = (TextView) findViewById(R.id.gender);
        TextView tv_age = (TextView) findViewById(R.id.age);
        TextView tv_tel = (TextView) findViewById(R.id.tel);

        // 데이터 수신
        Intent it = getIntent();
        str_name = it.getStringExtra("it_name");
        try {
            dbManager = new DBManager(this);
            sqLiteDatabase = dbManager.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query("Personnel", null, "name= ? ", new String[]{str_name}, null, null, null, null);
            if (cursor.moveToNext()) {
                str_gender = cursor.getString(cursor.getColumnIndex("gender"));
                age = cursor.getInt(cursor.getColumnIndex("age"));
                str_tel = cursor.getString(cursor.getColumnIndex("tel"));
            }
            sqLiteDatabase.close();
            dbManager.close();
        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        tv_name.setText(str_name);
        tv_gender.setText(str_gender);
        tv_age.setText("" + age);
        tv_tel.setText(str_tel + "\n");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // 메뉴 클릭 시, 해당 액티비티로 이동
        if (id == R.id.action_settings) {
            /////////////////////////////////////////////////////////////////
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            /////////////////////////////////////////////////////////////////
            return true;
        } else if (id == R.id.action_settings1) {
            Intent it = new Intent(this, PersonnelList.class);
            startActivity(it);
            finish();
            return true;
        } else if (id == R.id.action_settings2) {
            Intent it = new Intent(this, PersonnelReg.class);
            startActivity(it);
            finish();
            return true;
        } else if (id == R.id.action_settings4) {
            try {
                dbManager = new DBManager(this);
                sqLiteDatabase = dbManager.getReadableDatabase();
                sqLiteDatabase.delete("Personnel", "name= ? ", new String[]{str_name});
                sqLiteDatabase.close();
                dbManager.close();
            } catch (SQLException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            Intent it = new Intent(this, PersonnelList.class);
            startActivity(it);
            finish();
            return true;

            ////////////////////////////////////////////////


        }
        return super.onOptionsItemSelected(item);
    }
}