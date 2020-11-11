package com.j3dream.android.common.data;

/**
 * <p>文件名称: BuildInfo </p>
 * <p>所属包名: com.j3dream.android.common.data</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 12:04 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class SysBuildInfo {

    /**
     * 主板名称
     */
    private String board;

    /**
     * 系统引导程序版本号
     */
    private String bootloader;

    /**
     * 系统定制商
     */
    private String brand;

    /**
     * 设备参数
     */
    private String device;

    /**
     * 显示屏参数
     */
    private String display;

    /**
     * 硬件名
     */
    private String fingerprint;

    /**
     * 内核命令行中的硬件名
     */
    private String hardware;

    /**
     * host
     */
    private String host;

    /**
     * 标签
     */
    private String id;

    /**
     * 硬件厂商
     */
    private String manufacturer;

    /**
     * 版本
     */
    private String model;

    /**
     * 手机厂商
     */
    private String product;

    /**
     * 返回无线电固件的版本字符串
     */
    private String radio;

    /**
     * 获取硬件序列号
     */
    private String serial;

    /**
     * 描述Build的标签
     */
    private String tags;

    /**
     * time
     */
    private long time;

    /**
     * type
     */
    private String type;

    /**
     * user
     */
    private String user;

    /**
     * os版本
     */
    private String osVersion;

    /**
     * 版本
     */
    private String releaseVersion;

    /**
     * 当前开发代码名称
     */
    private String codeName;

    /**
     * 基础源代码控件用于表示此构建的内部值
     */
    private String incremental;

    /**
     * SDK的版本
     */
    private int sdkInt;

    /**
     * SDK的预览版本
     */
    private int previewSdkInt;

    /**
     * 用户可见的安全补丁程序级别
     */
    private String securityPatch;


    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBootloader() {
        return bootloader;
    }

    public void setBootloader(String bootloader) {
        this.bootloader = bootloader;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getIncremental() {
        return incremental;
    }

    public void setIncremental(String incremental) {
        this.incremental = incremental;
    }

    public int getSdkInt() {
        return sdkInt;
    }

    public void setSdkInt(int sdkInt) {
        this.sdkInt = sdkInt;
    }

    public int getPreviewSdkInt() {
        return previewSdkInt;
    }

    public void setPreviewSdkInt(int previewSdkInt) {
        this.previewSdkInt = previewSdkInt;
    }

    public String getSecurityPatch() {
        return securityPatch;
    }

    public void setSecurityPatch(String securityPatch) {
        this.securityPatch = securityPatch;
    }

    @Override
    public String toString() {
        return "BuildInfo{" +
                "board='" + board + '\'' +
                ", bootloader='" + bootloader + '\'' +
                ", brand='" + brand + '\'' +
                ", device='" + device + '\'' +
                ", display='" + display + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                ", hardware='" + hardware + '\'' +
                ", host='" + host + '\'' +
                ", id='" + id + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", product='" + product + '\'' +
                ", radio='" + radio + '\'' +
                ", serial='" + serial + '\'' +
                ", tags='" + tags + '\'' +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", user='" + user + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", releaseVersion='" + releaseVersion + '\'' +
                ", codeName='" + codeName + '\'' +
                ", incremental='" + incremental + '\'' +
                ", sdkInt=" + sdkInt +
                ", previewSdkInt=" + previewSdkInt +
                ", securityPatch='" + securityPatch + '\'' +
                '}';
    }
}
