package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //노티스 어댑터 관련 추가하기, ==>해당 클래스의 3개의 멤버 변수 추가하기
    private ListView noticeListView;
    private NoticeListAdapter  adapter;
    private List<Notice> noticeList;
    //시간표를 위해 코드 더 추가하기
    public static String userID; // 모든 클래스에서 접근이 가능하도록 만들어주기



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //여기 추가하기 (시간표 관련)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//화면 세로로 고정

        //메인 엑티비티가 실행이 되면
        userID = getIntent().getStringExtra("userID");



        //노티스 어뎁터 관련 추가하기
        noticeListView = (ListView) findViewById(R.id.noticeListView);
        //초기화 하기
        noticeList = new ArrayList<Notice>();
        //각각의 리스트에 예시 데이터 넣기
        //noticeList.add(new Notice("공지사항 입니다.", "박은실", "2021-02-19"));
        //noticeList.add(new Notice("공지사항 입니다.", "박은실", "2021-02-19"));
        //noticeList.add(new Notice("공지사항 입니다.", "박은실", "2021-02-19"));
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);//리스트뷰에 해당 어뎁터 연결

        final Button courseButton = (Button) findViewById(R.id.courseButton);
        final Button statisticButton = (Button) findViewById(R.id.statisticButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        final Button logoutButton = (Button) findViewById(R.id.logoutButton);

        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);



        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                statisticButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.commit();

            }
        });


        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());
                fragmentTransaction.commit();

            }
        });


        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        //공지사항 리스트 사용할 수 있도록 하기
        new BackgroundTask().execute();//정상적으로 디비에 접근해서 찾아올 수 있다.
    }

    //해당 공지사항의 디비를 접속할 수 있도록 클래스 만들기
    class BackgroundTask extends AsyncTask<Void,Void,String>{

        String target;//접속할 주소가 들어간다.

        @Override
        protected void onPreExecute() {
            target = "http://selene292.cafe24.com/NoticeList.php";//해당 파일의 해당 웹서버에 접속할 수 있도록
        }

        //실질적으로 데이터를 얻어올수 있는 코드
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                //해당 InputStream에 있는 내용들을 버퍼에 담아서 읽을 수 있도록 하기
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                //이 템프에 하나씩 읽어와서 문자열 형태로 저장할 수 있도록 하기
                StringBuilder stringBuilder = new StringBuilder();
                //템프에다가 하나씩 넣는다. 버퍼에서 가져온 값을 한 줄씩 읽으면서 템프에다가 하나씩 넣는다.
                while ((temp = bufferedReader.readLine())!=null){
                    //stringBuilder에 템프에 한줄씩 추가하면서 넣기
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        //해당 결과를 처리할 수 있음
        @Override
        protected void onPostExecute(String result) {//결과값 result로 받아오기

            try {
                JSONObject jsonObject =new JSONObject(result);//응답부분 처리하기
                //response에 각각의 공지사항 리스트가 담긴다.
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count =0;
                String noticeContent, noticeName, noticeDate;
                while(count<jsonArray.length()){//전체 크기보다 작을때 까지
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    //하나의 객체 만들어주기, 하나의 공지사항에 대한 객체 생성해주기
                    //하나의 생성자를 이용해서 객체 할당해주기
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    //여기 코드 한줄 더 추가하든 안하든 결과는 똑같이 잘 나옴
                    //adapter.notifyDataSetChanged();//어댑터에 데이터 변화가 있다고 알려주기@@@@@
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    //여기는 메인화면에서 두번 뒤로가기 버튼을 누르면 앱이 종료되는 코드 추가하기
    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {//1번 버튼을 누른 이후에 1.5초 이내로 또 버튼을 누르게 되면 자동종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();//.show() 추가해야 토스트 보인다.
        lastTimeBackPressed = System.currentTimeMillis();
    }
}