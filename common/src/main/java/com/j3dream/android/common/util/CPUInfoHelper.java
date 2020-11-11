package com.j3dream.android.common.util;

import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * <p>文件名称: CPUInfoHelper </p>
 * <p>所属包名: com.j3dream.android.common.util</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 12:24 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
class CPUInfoHelper {

    public static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return Pattern.matches("cpu[0-9]", pathname.getName());
        }
    };

    public static String putCpuAbi() {
        String[] abis;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String abi : abis) {
            stringBuilder.append(abi);
            stringBuilder.append(",");
        }
        try {
            return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getCpuTemp() {
        String temp = null;
        try {
            FileReader fr = new FileReader("/sys/class/thermal/thermal_zone9/subsystem/thermal_zone9/temp");
            BufferedReader br = new BufferedReader(fr);
            temp = br.readLine();
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return TextUtils.isEmpty(temp) ? null : temp.length() >= 5 ? (Integer.valueOf(temp) / 1000) + "" : temp.length() >= 4 ? (Integer.valueOf(temp) / 100) + "" : temp;
    }

    public static int getHeart() {

        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            cores = 0;
        }
        return cores;
    }

    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            result = "N/A";
        }
        return result.trim();
    }

    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            result = "N/A";
        }
        return result.trim();
    }
}
