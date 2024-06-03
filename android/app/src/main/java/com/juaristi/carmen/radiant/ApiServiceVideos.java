package com.juaristi.carmen.radiant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceVideos {
    @GET("agents/{agent_name}/videos")
    Call<List<AgentVideo>> getAgentVideos(@Path("agent_name") String agentName);
}
