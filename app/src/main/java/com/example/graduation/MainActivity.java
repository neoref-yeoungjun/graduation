package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment home1;
    private InterestFragment interest1;
    private SettingFragment setting1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        //첫 화면 띄우기
       // getSupportFragmentManager().beginTransaction().add(R.id.frame_container,new home1()).commit();

        //case 함수를 통해 클릭 받을 때마다 화면 변경하기
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_fragment1 :
                        setFrag(0);
                        break;
                    case R.id.item_fragment4:
                        setFrag(1);
                        break;

                    case R.id.item_fragment5:
                        setFrag(2);
                        break;


                }
                return true;
            }
        });

        home1= new HomeFragment();
        interest1= new InterestFragment();
        setting1 = new SettingFragment();
        setFrag(0);
    }

    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n)
        {
            case 0:
            ft.replace(R.id.frame_container,home1);
            ft.commit();
            break;

            case 1:
                ft.replace(R.id.frame_container,interest1);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frame_container,setting1);
                ft.commit();
                break;


        }
    }
}