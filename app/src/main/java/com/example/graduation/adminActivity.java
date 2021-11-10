package com.example.graduation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class adminActivity extends AppCompatActivity {
    private static final String TAG = "admin_Activity";

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment home1;
    private InterestFragment interest1;
    private SettingFragment setting1;
    private WeekdayFragment weekday1;
    private LocationFragment location1;
    private CategoryFragment cate1;
    private MypageFragment mypage1;
    private QuestionFragment question1;
    private SearchActivity search1;
    private AdminFragment admin1;


    private DrawerLayout mDrawerLayout;
    private Context context = this;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();// 로그인 상태확인

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);



        bottomNavigationView = findViewById(R.id.bottomNavi_admin);

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

                if(id == R.id.week_day){
                    setFrag(3);
                }
                else if(id == R.id.location){
                    setFrag(4);
                }
                else if(id == R.id.category){
                    setFrag(5);
                }
                else if(id == R.id.interest_edu){
                    if (user != null) {
                        setFrag(1);
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(id == R.id.mypage){
                    if (user != null) {
                        setFrag(6);
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(id == R.id.question){
                    if (user != null) {
                        setFrag(7);
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                    }
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

                    case R.id.item_fragment2://이전 버튼
                        onBackPressed();
                        break;

                    case R.id.item_fragment3://새로 고침
                        setFrag(9);
                        break;

                    case R.id.item_fragment4:
                        if (user != null) {
                            setFrag(1);
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.item_fragment5:
                        if (user != null) {
                            setFrag(10);
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
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
        weekday1 = new WeekdayFragment();
        location1 = new LocationFragment();
        cate1 = new CategoryFragment();
        mypage1 = new MypageFragment();
        question1 = new QuestionFragment();
        search1 = new SearchActivity();
        admin1 = new AdminFragment();
        setFrag(10);
    }

    private void setFrag(int n)
    {
        String uid = getIntent().getStringExtra("uid");
        Bundle bundle = new Bundle(); bundle.putString("uid",uid);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n)
        {
            case 0:
                ft.replace(R.id.admin_frame_container,home1,"Frag1");
                ft.addToBackStack(null);
                ft.commit();
                home1.setArguments(bundle);

                break;

            case 1:
                ft.replace(R.id.admin_frame_container,interest1,"Frag2");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.admin_frame_container,setting1,"Frag3");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.admin_frame_container,weekday1,"Frag4");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.admin_frame_container,location1,"Frag5");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 5:
                ft.replace(R.id.admin_frame_container,cate1,"Frag6");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 6:
                ft.replace(R.id.admin_frame_container,mypage1,"Frag7");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 7:
                ft.replace(R.id.admin_frame_container,question1,"Frag8");
                ft.addToBackStack(null);
                ft.commit();
                question1.setArguments(bundle);
                break;
            case 8:
                ft.replace(R.id.admin_frame_container,search1,"Frag9");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 10:
                ft.replace(R.id.admin_frame_container,admin1,"Frag10");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 9:
                Fragment frg = null;
                frg = getSupportFragmentManager().findFragmentById(R.id.admin_frame_container);
                ft = getSupportFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(frg).attach(frg).commit();



        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_action, menu);

        return true;
    }

    @Override  //왼쪽 상단 누르기
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }

            case R.id.action_search: {
                setFrag(8);
                return true;
            }
            case R.id.action_account: {
                if (user != null) {
                    setFrag(6);
                } else {
                    Intent intent = new Intent(adminActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
