package com.j3dream.android.common.data;

/**
 * <p>文件名称: MemoryInfo </p>
 * <p>所属包名: com.j3dream.android.common.data</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 13:20 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class MemoryInfo {

    /**
     * ram total
     */
    private String ramMemory;

    /**
     * ram  Available
     */
    private String ramAvailMemory;

    /**
     * rom Available
     */
    private String romMemoryAvailable;

    /**
     * rom total
     */
    private String romMemoryTotal;

    /**
     * sdcard Available
     */
    private String sdCardMemoryAvailable;

    /**
     * sdcard total
     */
    private String sdCardMemoryTotal;

    private String sdCardRealMemoryTotal;

    public String getRamMemory() {
        return ramMemory;
    }

    public void setRamMemory(String ramMemory) {
        this.ramMemory = ramMemory;
    }

    public String getRamAvailMemory() {
        return ramAvailMemory;
    }

    public void setRamAvailMemory(String ramAvailMemory) {
        this.ramAvailMemory = ramAvailMemory;
    }

    public String getRomMemoryAvailable() {
        return romMemoryAvailable;
    }

    public void setRomMemoryAvailable(String romMemoryAvailable) {
        this.romMemoryAvailable = romMemoryAvailable;
    }

    public String getRomMemoryTotal() {
        return romMemoryTotal;
    }

    public void setRomMemoryTotal(String romMemoryTotal) {
        this.romMemoryTotal = romMemoryTotal;
    }

    public String getSdCardMemoryAvailable() {
        return sdCardMemoryAvailable;
    }

    public void setSdCardMemoryAvailable(String sdCardMemoryAvailable) {
        this.sdCardMemoryAvailable = sdCardMemoryAvailable;
    }

    public String getSdCardMemoryTotal() {
        return sdCardMemoryTotal;
    }

    public void setSdCardMemoryTotal(String sdCardMemoryTotal) {
        this.sdCardMemoryTotal = sdCardMemoryTotal;
    }

    public String getSdCardRealMemoryTotal() {
        return sdCardRealMemoryTotal;
    }

    public void setSdCardRealMemoryTotal(String sdCardRealMemoryTotal) {
        this.sdCardRealMemoryTotal = sdCardRealMemoryTotal;
    }

    @Override
    public String toString() {
        return "MemoryInfo{" +
                "ramMemory='" + ramMemory + '\'' +
                ", ramAvailMemory='" + ramAvailMemory + '\'' +
                ", romMemoryAvailable='" + romMemoryAvailable + '\'' +
                ", romMemoryTotal='" + romMemoryTotal + '\'' +
                ", sdCardMemoryAvailable='" + sdCardMemoryAvailable + '\'' +
                ", sdCardMemoryTotal='" + sdCardMemoryTotal + '\'' +
                ", sdCardRealMemoryTotal='" + sdCardRealMemoryTotal + '\'' +
                '}';
    }
}
