package com.juaristi.carmen.radiant;

import java.util.List;

public class VideosResponse {
    private int status;
    private List<AgentVideo> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<AgentVideo> data) {
        this.data = data;
    }

    public List<AgentVideo> getData() {
        return data;
    }
}