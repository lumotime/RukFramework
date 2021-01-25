package com.j3dream.core.interf;

/**
 * <p>文件名称: OnIOProgressUpdateListener </p>
 * <p>所属包名: com.lumotime.core.interf</p>
 * <p>描述: IO 处理更新监听器 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 10:50 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface OnIOProgressUpdateListener {

    /**
     * 处理更新
     *
     * @param progress 处理进度
     */
    void onProgressUpdate(double progress);
}