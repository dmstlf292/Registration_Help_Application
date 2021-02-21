package com.example.registeration;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ListView courseListView;
    private StatisticsCourseListAdapter adapter;
    private List<Course> courseList;

    public static int totalCredit=0;
    public static TextView credit;

    private ArrayAdapter rankAdapter;
    private Spinner rankSpinner;

    private ListView rankListView;
    private RankListAdapter rankListAdapter;
    private List<Course> rankList;

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
        courseListView=(ListView)getView().findViewById(R.id.courseListView);
        courseList=new ArrayList<Course>();
        adapter=new StatisticsCourseListAdapter(getContext().getApplicationContext(), courseList, this);
        courseListView.setAdapter(adapter);
        new BackgroundTask().execute();
        totalCredit=0;
        credit=(TextView) getView().findViewById(R.id.totalCredit);
        rankSpinner = (Spinner)getView().findViewById(R.id.rankSpinner);
        rankAdapter=ArrayAdapter.createFromResource(getActivity(), R.array.rank, R.layout.spinner_item);
        rankSpinner.setAdapter(rankAdapter);
        rankSpinner.setPopupBackgroundResource(R.color.colorPrimary);
        rankListView=(ListView) getView().findViewById(R.id.rankListView);
        rankList=new ArrayList<Course>();
        rankListAdapter=new RankListAdapter(getContext().getApplicationContext(), rankList,this);
        rankListView.setAdapter(rankListAdapter);
        //new ByEntire().execute();
        rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(rankSpinner.getSelectedItem().equals("전체에서"))
                {
                    rankList.clear();
                    new ByEntire().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("우리과에서"))
                {
                    rankList.clear();
                    new ByMyMajor().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("남자 선호도"))
                {
                    rankList.clear();
                    new ByMale().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("여자 선호도"))
                {
                    rankList.clear();
                    new ByFemale().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("전공 인기도"))
                {
                    rankList.clear();
                    new ByMajor().execute();
                }
                else if(rankSpinner.getSelectedItem().equals("교양 인기도"))
                {
                    rankList.clear();
                    new ByRefinement().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class ByRefinement extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://selene292.cafe24.com/ByRefinement.php?userID="+URLEncoder.encode(MainActivity.userID,"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                int courseID, courseCredit, courseDivide, coursePersonnel;
                String courseGrade, courseTitle, courseProfessor, courseTime;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    courseID=object.getInt("courseID");
                    courseGrade=object.getString("courseGrade");
                    courseTitle=object.getString("courseTitle");
                    courseProfessor=object.getString("courseProfessor");
                    courseDivide=object.getInt("courseDivide");
                    coursePersonnel=object.getInt("coursePersonnel");
                    courseCredit =object.getInt("courseCredit");
                    courseTime=object.getString("courseTime");
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel,courseProfessor,courseTime));
                    count++;
                }
                rankListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class ByMajor extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://selene292.cafe24.com/ByMajor.php?userID="+URLEncoder.encode(MainActivity.userID,"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                int courseID, courseCredit, courseDivide, coursePersonnel;
                String courseGrade, courseTitle, courseProfessor, courseTime;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    courseID=object.getInt("courseID");
                    courseGrade=object.getString("courseGrade");
                    courseTitle=object.getString("courseTitle");
                    courseProfessor=object.getString("courseProfessor");
                    courseDivide=object.getInt("courseDivide");
                    coursePersonnel=object.getInt("coursePersonnel");
                    courseCredit =object.getInt("courseCredit");
                    courseTime=object.getString("courseTime");
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel,courseProfessor,courseTime));
                    count++;
                }
                rankListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class ByMyMajor extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://selene292.cafe24.com/ByMyMajor.php?userID="+URLEncoder.encode(MainActivity.userID,"UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                int courseID, courseCredit, courseDivide, coursePersonnel;
                String courseGrade, courseTitle, courseProfessor, courseTime;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    courseID=object.getInt("courseID");
                    courseGrade=object.getString("courseGrade");
                    courseTitle=object.getString("courseTitle");
                    courseProfessor=object.getString("courseProfessor");
                    courseDivide=object.getInt("courseDivide");
                    coursePersonnel=object.getInt("coursePersonnel");
                    courseCredit =object.getInt("courseCredit");
                    courseTime=object.getString("courseTime");
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel,courseProfessor,courseTime));
                    count++;
                }
                rankListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    class ByFemale extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://selene292.cafe24.com/ByFemale.php";
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                int courseID, courseCredit, courseDivide, coursePersonnel;
                String courseGrade, courseTitle, courseProfessor, courseTime;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    courseID=object.getInt("courseID");
                    courseGrade=object.getString("courseGrade");
                    courseTitle=object.getString("courseTitle");
                    courseProfessor=object.getString("courseProfessor");
                    courseDivide=object.getInt("courseDivide");
                    coursePersonnel=object.getInt("coursePersonnel");
                    courseCredit =object.getInt("courseCredit");
                    courseTime=object.getString("courseTime");
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel,courseProfessor,courseTime));
                    count++;
                }
                rankListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class ByMale extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://selene292.cafe24.com/ByMale.php";
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                int courseID, courseCredit, courseDivide, coursePersonnel;
                String courseGrade, courseTitle, courseProfessor, courseTime;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    courseID=object.getInt("courseID");
                    courseGrade=object.getString("courseGrade");
                    courseTitle=object.getString("courseTitle");
                    courseProfessor=object.getString("courseProfessor");
                    courseDivide=object.getInt("courseDivide");
                    coursePersonnel=object.getInt("coursePersonnel");
                    courseCredit =object.getInt("courseCredit");
                    courseTime=object.getString("courseTime");
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel,courseProfessor,courseTime));
                    count++;
                }
                rankListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class ByEntire extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://selene292.cafe24.com/ByEntire.php";
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                int courseID, courseCredit, courseDivide, coursePersonnel;
                String courseGrade, courseTitle, courseProfessor, courseTime;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    courseID=object.getInt("courseID");
                    courseGrade=object.getString("courseGrade");
                    courseTitle=object.getString("courseTitle");
                    courseProfessor=object.getString("courseProfessor");
                    courseDivide=object.getInt("courseDivide");
                    coursePersonnel=object.getInt("coursePersonnel");
                    courseCredit =object.getInt("courseCredit");
                    courseTime=object.getString("courseTime");
                    rankList.add(new Course(courseID, courseGrade, courseTitle, courseCredit, courseDivide, coursePersonnel,courseProfessor,courseTime));
                    count++;
                }
                rankListAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //이게 총 분석부분 (제일 윗부분))
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://selene292.cafe24.com/StatisticsCourseList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url=new URL(target);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder=new StringBuilder();
                while((temp=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject=new JSONObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray("response");
                int count=0;
                int courseID, courseDivide, coursePersonnel, courseRival;
                String courseGrade, courseTitle;
                while(count<jsonArray.length()){
                    JSONObject object=jsonArray.getJSONObject(count);
                    courseID=object.getInt("courseID");
                    courseGrade=object.getString("courseGrade");
                    courseTitle=object.getString("courseTitle");
                    courseDivide=object.getInt("courseDivide");
                    coursePersonnel=object.getInt("coursePersonnel");
                    courseRival=object.getInt("COUNT(SCHEDULE_UNI.courseID)");
                    int courseCredit =object.getInt("courseCredit");
                    totalCredit+=courseCredit;
                    courseList.add(new Course(courseID, courseGrade, courseTitle, courseDivide, coursePersonnel, courseRival, courseCredit));
                    count++;
                }
                adapter.notifyDataSetChanged();
                credit.setText(totalCredit+"학점");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}