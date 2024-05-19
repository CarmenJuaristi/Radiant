package com.juaristi.carmen.radiant;

import java.util.List;

public class Agent {
    private String uuid;
    private String displayName;
    private String displayIconSmall;
    private List<String> videoUrls;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayIconSmall() {
        return displayIconSmall;
    }

    public void setDisplayIconSmall(String displayIconSmall) {
        this.displayIconSmall = displayIconSmall;
    }
    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }
}
