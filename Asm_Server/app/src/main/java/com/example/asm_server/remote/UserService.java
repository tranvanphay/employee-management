package com.example.asm_server.remote;

import com.example.asm_server.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("json/")
    Call<List<User>> getUser();
@POST("api/add")
    Call<User> addUser(@Body User user);
@PUT("api/update/{id}")
    Call<User>  updateUser(@Path("id") String id,@Body User user);
@DELETE("api/delete/{id}")
Call<User> deleteUser(@Path("id") String id);
}
