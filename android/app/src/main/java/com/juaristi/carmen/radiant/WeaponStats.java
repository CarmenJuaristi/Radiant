package com.juaristi.carmen.radiant;

public class WeaponStats {
    private String reloadTimeSeconds;
    private String runSpeedMultiplier;
    private String fireRate;
    private String firstBulletAccuracy;

    public void setReloadTimeSeconds(String reloadTimeSeconds) {
        this.reloadTimeSeconds = reloadTimeSeconds;
    }

    public void setRunSpeedMultiplier(String runSpeedMultiplier) {
        this.runSpeedMultiplier = runSpeedMultiplier;
    }

    public void setFireRate(String fireRate) {
        this.fireRate = fireRate;
    }

    public void setFirstBulletAccuracy(String firstBulletAccuracy) {
        this.firstBulletAccuracy = firstBulletAccuracy;
    }

    public void setWallPenetration(String wallPenetration) {
        this.wallPenetration = wallPenetration;
    }

    private String wallPenetration;

    public String getReloadTimeSeconds() {
        return reloadTimeSeconds;
    }

    public String getRunSpeedMultiplier() {
        return runSpeedMultiplier;
    }

    public String getFireRate() {
        return fireRate;
    }

    public String getFirstBulletAccuracy() {
        return firstBulletAccuracy;
    }

    public String getWallPenetration() {
        return wallPenetration;
    }
}
