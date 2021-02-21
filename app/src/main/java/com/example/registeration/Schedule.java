package com.example.registeration;

import android.content.Context;

public class Schedule {

    private String monday[]= new String[14];//13교시 까지 존재
    private String tuesday[]= new String[14];//13교시 까지 존재
    private String wednesday[]= new String[14];//13교시 까지 존재
    private String thursday[]= new String[14];//13교시 까지 존재
    private String friday[]= new String[14];//13교시 까지 존재

    //생성자 만들어주기, 초기화 하기
    public Schedule() {
        for(int i =0; i<14; i++){
            monday[i] ="";
            tuesday[i] ="";
            wednesday[i] ="";
            thursday[i] ="";
            friday[i] ="";
        }
    }

    public void addSchedule(String scheduleText){
        //월 : [3][4][5] // 월요일의 "월"이라는 데이터를 기준으로 2,3,4교시 데이터에 강의 정보를 파싱하여 들어갈 수 있도록 하는것
        //"월"을 기준으로 3,4,5 ---> 이런 문자열이 scheduleText로 들어갔을때 파싱해주면 되는것!
        int temp;
        if((temp =scheduleText.indexOf("월")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]="수업";
                }
            }
        }

        if((temp =scheduleText.indexOf("화")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]="수업";
                }
            }
        }

        if((temp =scheduleText.indexOf("수")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]="수업";
                }
            }
        }

        if((temp =scheduleText.indexOf("목")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]="수업";
                }
            }
        }

        if((temp =scheduleText.indexOf("금")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]="수업";
                }
            }
        }
    }








    public void addSchedule(String scheduleText, String courseTitle, String courseProfessor){
        String professor; //변수선언
        if (courseProfessor.equals("")){
            professor="";
        }else {
            professor = "";
        }

        int temp;
        if((temp =scheduleText.indexOf("월")) >-1){
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]=courseTitle+professor;
                }
            }
        }

        if((temp =scheduleText.indexOf("화")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]=courseTitle+professor;
                }
            }
        }

        if((temp =scheduleText.indexOf("수")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]=courseTitle+professor;
                }
            }
        }

        if((temp =scheduleText.indexOf("목")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]=courseTitle+professor;
                }
            }
        }

        if((temp =scheduleText.indexOf("금")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))]=courseTitle+professor;
                }
            }
        }
    }








    //수강신청의 날짜 데이터가 현재 자리잡고 있는 스케줄 데이터에 중복되지 않는지 중복체크
    public boolean validate (String scheduleText){
        if (scheduleText.equals("")){
            //공백이라면
            return true;
        }
        int temp;

        if((temp =scheduleText.indexOf("월")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    //공백이 아니라면
                    if (!monday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }

        if((temp =scheduleText.indexOf("화")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    //공백이 아니라면
                    if (!tuesday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }

        if((temp =scheduleText.indexOf("수")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    //공백이 아니라면
                    if (!wednesday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }

        if((temp =scheduleText.indexOf("목")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    //공백이 아니라면
                    if (!thursday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }

        if((temp =scheduleText.indexOf("금")) >-1){//temp 안에 "월"이라는 단어가 포함되어 있다면
            temp+=2;
            int startPoint = temp;
            int endPoint = temp;
            for(int i = temp; i<scheduleText.length() && scheduleText.charAt(i)!=':'; i++){
                //숫자 데이터가 들어가는것
                if(scheduleText.charAt(i)=='['){
                    startPoint = i;
                }
                if(scheduleText.charAt(i)==']'){
                    endPoint=i;
                    //공백이 아니라면
                    if (!friday[Integer.parseInt(scheduleText.substring(startPoint +1, endPoint))].equals("")){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //텍스트뷰에 해당 강의 목록들 보여주게 하기 위해서 세팅하기
    public void setting (AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wednesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday,
                         Context context){
        int maxLength=0;
        String maxString="";
        for (int i =0; i<14; i++){
            if (this.monday[i].length()>maxLength){
                maxLength = this.monday[i].length();
                maxString=this.monday[i];
            }
            if (this.tuesday[i].length()>maxLength){
                maxLength = this.tuesday[i].length();
                maxString=this.tuesday[i];
            }
            if (this.wednesday[i].length()>maxLength){
                maxLength = this.wednesday[i].length();
                maxString=this.wednesday[i];
            }
            if (this.thursday[i].length()>maxLength){
                maxLength = this.thursday[i].length();
                maxString=this.thursday[i];
            }
            if (this.friday[i].length()>maxLength){
                maxLength = this.friday[i].length();
                maxString=this.friday[i];
            }
        }






        for (int i =0; i<14; i++){
            if (!this.monday[i].equals("")){//현재 배열에 들어있는 값이 비어있지 않다면
                monday[i].setText(this.monday[i]);
                monday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                monday[i].setText(maxString);
            }
            if (!this.tuesday[i].equals("")){//현재 배열에 들어있는 값이 비어있지 않다면
                tuesday[i].setText(this.tuesday[i]);
                tuesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                tuesday[i].setText(maxString);
            }
            if (!this.wednesday[i].equals("")){//현재 배열에 들어있는 값이 비어있지 않다면
                wednesday[i].setText(this.wednesday[i]);
                wednesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                wednesday[i].setText(maxString);
            }
            if (!this.thursday[i].equals("")){//현재 배열에 들어있는 값이 비어있지 않다면
                thursday[i].setText(this.thursday[i]);
                thursday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                thursday[i].setText(maxString);
            }
            if (!this.friday[i].equals("")){//현재 배열에 들어있는 값이 비어있지 않다면
                friday[i].setText(this.friday[i]);
                friday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }else{
                friday[i].setText(maxString);
            }
            monday[i].setSingleLine();
            tuesday[i].setSingleLine();
            wednesday[i].setSingleLine();
            thursday[i].setSingleLine();
            friday[i].setSingleLine();

        }





    }
}
