package com.eci.cosw.taskplanner.Service;

import com.eci.cosw.taskplanner.Model.LoginWrapper;
import com.eci.cosw.taskplanner.Model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("token/login")
    Call<Token> login(@Body LoginWrapper loginWrapper);

}
