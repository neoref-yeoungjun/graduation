package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment home1;
    private InterestFragment interest1;
    private SettingFragment setting1;

    private DrawerLayout mDrawerLayout;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 메뉴버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24); //왼쪽 상단 홈버튼

        ActionBar actionBar = getSupportActionBar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(mDrawerLayout.LOCK_MODE_LOCKED_CLOSED);//스와이프 비활성화

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.organization){
                    setFrag(0);
                }
                else if(id == R.id.location){
                    setFrag(1);
                }
                else if(id == R.id.category){
                    setFrag(2);
                }

                return true;
            }
        });


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
                    case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                        mDrawerLayout.openDrawer(GravityCompat.START);
                        return true;

                    }


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

    @Override  //왼쪽 상단 누르기
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
