package com.example.graduation;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class eduTableFragment extends Fragment implements OnMapReadyCallback {

    TextView table_apply_start;
    TextView table_apply_url;
    TextView table_apply_end;
    TextView table_edu_person;
    TextView table_edu_start;
    TextView table_edu_end;
    TextView table_institution;
    TextView table_name;
    TextView table_time;
    TextView table_fee;
    TextView table_week;
    TextView table_outlook;
    TextView table_place;
    TextView table_target;
    TextView table_teacher;
    TextView table_teacher_info;
    TextView table_cate;
    EditText edu_comment_content;
    Button edu_comment_btn;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference1,databaseReference2,dataremove,datainter,datacheck,datacheck2,checkremove;;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Comment> arrayList;
    private String removeid, removekey;
    private ArrayList<Integer> check;
    private String apply_start;
    private String apply_url;
    private String apply_end;
    private int edu_person;
    private String edu_start;
    private String edu_end;
    private String institution;
    private String name;
    private String time;
    private String fee;
    private String week;
    private String outlook;
    private String place;
    private String target;
    private String teacher;
    private String teacher_info;
    private String cate;
    private int field;
    private NaverMap naverMap;
    private DatabaseReference databaseReference,dataRef;
    private String str, str2;
    private static final String EMAIL_PATTERN = "([\\w.])(?:[\\w.]*)(@.*)";
    private double lat;
    private double log;
    private ArrayList<Map> Maplist;
    private LatLng latLng;
    private String userid,useremail;
    private Button naver_web;
    private CheckBox check_fa;
    private int ch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.edu_table,container,false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userid =user.getUid();
            useremail= user.getEmail();
        }

       table_name = v.findViewById(R.id.table_name);
       table_outlook = v.findViewById(R.id.table_outlook);
       table_institution = v.findViewById(R.id.table_institution);
       table_place = v.findViewById(R.id.table_place);
       table_week = v.findViewById(R.id.table_week);
       table_time = v.findViewById(R.id.table_time);
       table_teacher = v.findViewById(R.id.table_teacher);
       table_edu_person = v.findViewById(R.id.table_edu_person);
       table_apply_start = v.findViewById(R.id.table_apply_start);
       table_apply_end = v.findViewById(R.id.table_apply_end);
       table_edu_start = v.findViewById(R.id.table_edu_start);
       table_edu_end = v.findViewById(R.id.table_edu_end);
       table_target = v.findViewById(R.id.table_target);
       table_fee = v.findViewById(R.id.table_fee);
       table_teacher_info = v.findViewById(R.id.table_teacher_info);
       table_apply_url = v.findViewById(R.id.table_apply_url);
       table_cate = v.findViewById(R.id.table_cate);

        if(getArguments() != null){
            lat = getArguments().getDouble("lat");
            log = getArguments().getDouble("log");
            name = getArguments().getString("table_name");
            table_name.setText(name);

            outlook = getArguments().getString("table_outlook");
            table_outlook.setText(outlook);

            institution = getArguments().getString("table_institution");
            table_institution.setText(institution);

            place = getArguments().getString("table_place");
            table_place.setText(place);

            week = getArguments().getString("table_week");
            table_week.setText(week);

            time = getArguments().getString("table_time");
            table_time.setText(time);

            teacher = getArguments().getString("table_teacher");
            table_teacher.setText(teacher);

            edu_person = Integer.parseInt(getArguments().getString("table_edu_person"));
            table_edu_person.setText(String.valueOf(edu_person));

            apply_start = getArguments().getString("table_apply_start");
            table_apply_start.setText(apply_start);

            apply_end = getArguments().getString("table_apply_end");
            table_apply_end.setText(apply_end);

            edu_start = getArguments().getString("table_edu_start");
            table_edu_start.setText(edu_start);

            edu_end = getArguments().getString("table_edu_end");
            table_edu_end.setText(edu_end);

            target = getArguments().getString("table_target");
            table_target.setText(target);

            fee = getArguments().getString("table_fee");
            table_fee.setText(fee);

            teacher_info = getArguments().getString("table_teacher_info");
            table_teacher_info.setText(teacher_info);

            apply_url = getArguments().getString("table_apply_url");
            table_apply_url.setText(apply_url);

            cate = getArguments().getString("table_cate");
            table_cate.setText(cate);

            field= getArguments().getInt("field");

        }
        database = FirebaseDatabase.getInstance();
        check_fa=v.findViewById(R.id.favorite_Btn_check);
        check = new ArrayList<>();
        if (user != null) {
            datacheck = database.getReference("check").child(userid);
            datacheck.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                        check check = datasnapshot.getValue(check.class);
                        if (field == check.getInter()) {
                            check_fa.setChecked(true);
                            ch = check.getInter();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        database = FirebaseDatabase.getInstance();
        check_fa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_fa.setSelected(isChecked);
                if(check_fa.isChecked()){// 로그인 상태확인
                    if (user != null) {
                        datacheck2= database.getReference("favorite").child(userid);
                        datacheck2.orderByChild("field1").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                    edu edu3 = datasnapshot.getValue(edu.class);
                                    check.add(edu3.getFIELD1());
                                }
                                if(check.contains(field)){

                                }
                                else{
                                    Toast.makeText(getContext(), "관심강좌가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                    database = FirebaseDatabase.getInstance();
                                    databaseReference = database.getReference("favorite").child(userid);
                                    dataRef = databaseReference.push();
                                    String key = dataRef.getKey();
                                    edu edu= new edu();
                                    edu.setName(name);
                                    edu.setOutlook(outlook);
                                    edu.setInstitution(institution);
                                    edu.setPlace(place);
                                    edu.setWeek(week);
                                    edu.setTime(time);
                                    edu.setTeacher(teacher);
                                    edu.setEdu_person(edu_person);
                                    edu.setApply_start(apply_start);
                                    edu.setApply_end(apply_end);
                                    edu.setApply_url(apply_url);
                                    edu.setEdu_start(edu_start);
                                    edu.setEdu_end(edu_end);
                                    edu.setTarget(target);
                                    edu.setFee(fee);
                                    edu.setTeacher_info(teacher_info);
                                    edu.setCategory(cate);
                                    edu.setEdukey(key);
                                    edu.setFIELD1(field);
                                    dataRef.setValue(edu);

                                    int i = field;
                                    datainter = database.getReference("check").child(userid).push();
                                    datainter.child("inter").setValue(i);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(getContext(), "로그인이 필요한 기능입니다.", Toast.LENGTH_SHORT).show();
                        check_fa.setChecked(false);
                    }
                }
                else{
                    Toast.makeText(getContext(), "관심강좌를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    database = FirebaseDatabase.getInstance();;
                    dataremove=database.getReference("favorite").child(userid);
                    dataremove.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                edu edu2 = datasnapshot.getValue(edu.class);
                                if(edu2.getFIELD1()==field) {
                                    datasnapshot.getRef().removeValue();
                                    checkremove=database.getReference("check").child(userid);
                                    checkremove.orderByChild("inter").equalTo(field).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                                check check2 = datasnapshot.getValue(check.class);
                                                if(check2.getInter()==field) {
                                                    datasnapshot.getRef().removeValue();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        naver_web= (Button)v.findViewById(R.id.naver_web);
        naver_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String encodeStr = null;
                try {
                    encodeStr = URLEncoder.encode(institution, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String url = "nmap://place?lat="+lat+"&lng="+log+"&name="+encodeStr+"&zoom=10&appname=com.example.myapp";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);

                List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list == null || list.isEmpty()) {
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                } else {
                    getContext().startActivity(intent);
                }

            }
        });

        // 파이어베이스 데이터베이스 연동
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Map"); // DB 테이블 연결
        databaseReference.orderByChild("institution").equalTo(institution).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Map map = snapshot.getValue(Map.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    lat=map.getLatitude();
                    log=map.getLongitude();
                    Log.d(TAG, lat + ", " + log);
                    latLng=new LatLng(lat,log);
                    CameraPosition options = new CameraPosition(latLng, 16);
                    naverMap.setCameraPosition(options);
                    Marker marker = new Marker();
                    marker.setPosition(latLng);
                    marker.setMap(naverMap);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("HomeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        //lat2=Double.valueOf(lat);
        //log2=Double.parseDouble(log);

        mapFragment.getMapAsync(this);


        edu_comment_content = (EditText) v.findViewById(R.id.edu_comment_content);
        edu_comment_btn = (Button)v.findViewById(R.id.edu_comment_btn);
        if (user != null) {
            edu_comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long now = System.currentTimeMillis();
                    java.text.SimpleDateFormat dayTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = new Date(now);
                    str = dayTime.format(date);
                    str2 = edu_comment_content.getText().toString();
                    String eemail = useremail.replaceAll(EMAIL_PATTERN, "$1****$2");
                    dataRef = FirebaseDatabase.getInstance().getReference("comment_edu").push();
                    String mykey = dataRef.getKey();
                    Comment comment = new Comment(eemail, str, str2, name, mykey, userid);
                    dataRef.setValue(comment);
                    adapter.notifyDataSetChanged();

                    edu_comment_content.setText(null);
                }
            });
        }
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                final int position = viewHolder.getAdapterPosition();
                if (user != null) {
                    removeid = arrayList.get(position).getMyid().toString();
                    removekey = arrayList.get(position).getMykey();
                    databaseReference2 = database.getReference("comment_edu");
                    databaseReference2.orderByChild("myid").equalTo(removeid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                Comment comment2 = datasnapshot.getValue(Comment.class);
                                if (comment2.getMykey().equals(removekey)) {
                                    arrayList.remove(position);
                                    datasnapshot.getRef().removeValue();

                                }
                            }

                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        };
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_edu_comment);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        CommentAdapter.OnItemClickListener listener= null;

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference1 = database.getReference("comment_edu"); // DB 테이블 연결
        databaseReference1.orderByChild("key").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Comment comment = snapshot.getValue(Comment.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(comment); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("HomeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        adapter = new CommentAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
        if(user != null) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        return v;

    }
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

    }
}