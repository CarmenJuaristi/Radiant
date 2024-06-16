package com.juaristi.carmen.radiant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceVideos {
    @GET("agents/{agent_name}/videos")
    Call<List<AgentVideo>> getAgentVideos(@Path("agent_name") String agentName);
    @GET("home/videos")
    Call<List<AgentVideo>> getVideos();
    @GET("user/videos")
    Call<List<AgentVideo>> getUserVideos(@Header("Session-Token") String session_Token);
    @POST("user/videos")
    Call<Void> postUserVideo(@Header("Session-Token") String sessionToken, @Body UploadVideoRequestBody body);



}
