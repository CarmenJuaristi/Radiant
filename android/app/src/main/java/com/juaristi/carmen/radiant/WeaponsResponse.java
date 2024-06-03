package com.juaristi.carmen.radiant;
import java.util.List;

public class WeaponsResponse {
    private int status;
    private List<Weapon> data;
    public int getStatus(){return status;}

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Weapon> getData() {
        return data;
    }

    public void setData(List<Weapon> data) {
        this.data = data;
    }
}
