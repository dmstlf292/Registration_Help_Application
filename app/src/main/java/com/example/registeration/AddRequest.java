package com.example.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddRequest extends StringRequest {
    final static private String URL="http://selene292.cafe24.com/CourseAdd.php";
    private Map<String, String> parameters;

    //생성자 만들기
    public AddRequest(String userID, String courseID, Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);
    }

    @Override
    public Map<String , String> getParams(){
        return parameters;
    }

}