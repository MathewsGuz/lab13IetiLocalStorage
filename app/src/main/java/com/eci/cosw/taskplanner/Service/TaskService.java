package com.eci.cosw.taskplanner.Service;

import com.eci.cosw.taskplanner.Model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TaskService {

    @GET("api/task/user/{userId}")
    Call<List<Task>> userTasks(@Path("userId") String userId);

}
