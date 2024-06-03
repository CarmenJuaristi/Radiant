package com.juaristi.carmen.radiant;

public class Weapon {
    private String uuid;
    private String displayName;
    private String displayIcon;
    private String reloadTimeSeconds;
    private String runSpeedMultiplier;
    private String fireRate;
    private String firstBulletAccuracy;
    private String wallPenetration;
    public String getUuid(){return uuid;}
    public void setUuid (String uuid){this.uuid = uuid;}

    public String getDisplayIcon() {
        return displayIcon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayIcon(String displayIcon) {
        this.displayIcon = displayIcon;
    }

    public String getReloadTimeSeconds() {
        return reloadTimeSeconds;
    }

    public void setReloadTimeSeconds(String reloadTimeSeconds) {
        this.reloadTimeSeconds = reloadTimeSeconds;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFireRate() {
        return fireRate;
    }

    public void setFireRate(String fireRate) {
        this.fireRate = fireRate;
    }

    public String getRunSpeedMultiplier() {
        return runSpeedMultiplier;
    }

    public void setRunSpeedMultiplier(String runSpeedMultiplier) {
        this.runSpeedMultiplier = runSpeedMultiplier;
    }

    public String getFirstBulletAccuracy() {
        return firstBulletAccuracy;
    }

    public void setFirstBulletAccuracy(String firstBulletAccuracy) {
        this.firstBulletAccuracy = firstBulletAccuracy;
    }

    public String getWallPenetration() {
        return wallPenetration;
    }

    public void setWallPenetration(String wallPenetration) {
        this.wallPenetration = wallPenetration;
    }
}
