package com.juaristi.carmen.radiant;

public class UploadVideoRequestBody {
    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getAgent_name() {
        return agent_name;
    }

    private String video_url;
    private String image_url;
    private String agent_name;
}
