package com.j3dream.android.common.data;

import java.util.List;

/**
 * <p>文件名称: BluetoothInfo </p>
 * <p>所属包名: com.j3dream.android.common.data</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 13:05 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class BluetoothInfo {

    /**
     * 蓝牙地址
     */
    private String bluetoothAddress;

    /**
     * 蓝牙是否打开
     */
    private boolean isEnabled;

    /**
     * 连接的手机的信息
     */
    private List<BluetoothDeviceInfo> device;

    /**
     * 手机设置的名字
     */
    private String phoneName;

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<BluetoothDeviceInfo> getDevice() {
        return device;
    }

    public void setDevice(List<BluetoothDeviceInfo> device) {
        this.device = device;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    @Override
    public String toString() {
        return "BluetoothInfo{" +
                "bluetoothAddress='" + bluetoothAddress + '\'' +
                ", isEnabled=" + isEnabled +
                ", device=" + device +
                ", phoneName='" + phoneName + '\'' +
                '}';
    }

    public static class BluetoothDeviceInfo {
        /**
         * 连接手机的蓝牙地址
         */
        private String address;

        /**
         * 连接手机的蓝牙名字
         */
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "BluetoothDeviceInfo{" +
                    "address='" + address + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
