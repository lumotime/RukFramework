package com.j3dream.android.common.data;

/**
 * <p>文件名称: BatteryInfo </p>
 * <p>所属包名: com.j3dream.android.common.data</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 12:35 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class BatteryInfo {

    /**
     * 电量
     */
    private String br;

    /**
     * 电池状态
     */
    private String status;

    /**
     * 电池充电状态
     */
    private String plugState;

    /**
     * 电池健康状况
     */
    private String health;

    /**
     * 是否有电池
     */
    private boolean present;

    /**
     * 电池的技术制造
     */
    private String technology;

    /**
     * 电池温度
     */
    private String temperature;

    /**
     * 电池电压
     */
    private String voltage;

    /**
     * 电池总电量
     */
    private String power;

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlugState() {
        return plugState;
    }

    public void setPlugState(String plugState) {
        this.plugState = plugState;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "BatteryInfo{" +
                "br='" + br + '\'' +
                ", status='" + status + '\'' +
                ", plugState='" + plugState + '\'' +
                ", health='" + health + '\'' +
                ", present=" + present +
                ", technology='" + technology + '\'' +
                ", temperature='" + temperature + '\'' +
                ", voltage='" + voltage + '\'' +
                ", power='" + power + '\'' +
                '}';
    }
}