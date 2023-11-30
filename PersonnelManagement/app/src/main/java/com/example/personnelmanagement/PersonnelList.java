package com.example.personnelmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PersonnelList extends AppCompatActivity implements View.OnClickListener {
    DBManager dbManager;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout layout = (LinearLayout) findViewById(R.id.personnel);

        try{
            dbManager = new DBManager(this);
            sqLiteDatabase= dbManager.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query("Personnel", null, null,
                    null, null, null, null);
            int i = 0;
            while(cursor.moveToNext())
            {
                @SuppressLint("Range") String str_name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String str_gender = cursor.getString(cursor.getColumnIndex("gender"));
                @SuppressLint("Range") String age = cursor.getString(cursor.getColumnIndex("age"));
                @SuppressLint("Range") String str_tel = cursor.getString(cursor.getColumnIndex("Telephone no"));

                LinearLayout layout_item = new LinearLayout(this);
                layout_item.setOrientation(LinearLayout.VERTICAL);
                layout_item.setPadding(20,10,20,10);
                layout_item.setId(i);
                layout_item.setTag(str_name);


                TextView tv_name = new TextView(this);
                tv_name.setText("Name: " + str_name);

                tv_name.setBackgroundColor(Color.argb(50,0,255,0));
                layout_item.addView(tv_name);

                TextView tv_gender = new TextView(this);
                tv_gender.setText("Gender: "+ str_gender);
                layout_item.addView(tv_gender);

                TextView tv_age = new TextView(this);
                tv_age.setText("Age: "+age);
                layout_item.addView(tv_age);

                TextView tv_tel = new TextView(this);
                tv_tel.setText("Phone Number: "+ str_tel);
                layout_item.addView(tv_tel);

                layout_item.setOnClickListener(this);

                layout.addView(layout_item);


            i++;
        }
    }catch (SQLException e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}


    @Override
    public void onClick(View v) {

        int id = v.getId();
        LinearLayout layout_item = (LinearLayout) findViewById(id);
        String str_name = (String)layout_item.getTag();

        Intent it = new Intent(this, PersonnelInfo.class);
        it.putExtra("it_name", str_name);
        startActivity(it);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personnel_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 메뉴 클릭 시, 해당 액티비티로 이동
        if (id == R.id.action_settings) {
            /////////////////////////////////////////////////////////////////
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            /////////////////////////////////////////////////////////////////
            return true;
        }else if (id == R.id.action_settings2) {
            Intent it = new Intent(this, PersonnelReg.class);
            startActivity(it);
            finish();
            return true;
        }

        ////////////////////////////////////////////////

        return super.onOptionsItemSelected(item);
    }
}
