package com.ceiec.android.bustrack.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ceiec.android.bustrack.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View view){
        switch (view.getId()){
            case R.id.btn_busCompany:
                startActivity(new Intent(MainActivity.this,BusCompanyActivity.class));
                break;
            case R.id.btn_busList:
                startActivity(new Intent(MainActivity.this,BusListActivity.class));
                break;
        }
    }

}
