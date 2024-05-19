package com.juaristi.carmen.radiant;
import java.util.List;
public class AgentsResponse {
    private int status;
    private List<Agent> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Agent> getData() {
        return data;
    }

    public void setData(List<Agent> data) {
        this.data = data;
    }
}



