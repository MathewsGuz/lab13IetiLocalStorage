package com.eci.cosw.taskplanner.Service;

import com.eci.cosw.taskplanner.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("api/user/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

}
