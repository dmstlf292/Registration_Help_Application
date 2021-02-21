package com.example.registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest { // 아이디 중복체크
    final static private String URL="http://selene292.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    //생성자 만들기
    public ValidateRequest(String userID,  Response.Listener<String> listener){
        super(Method.POST,URL,listener, null);
        parameters = new HashMap<>();
    }

    @Override
    public Map<String , String> getParams(){
        return parameters;
    }

}
