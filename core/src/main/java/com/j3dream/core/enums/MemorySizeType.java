package com.j3dream.core.enums;

/**
 * <p>文件名称: MemorySizeType </p>
 * <p>所属包名: com.lumotime.core.constant</p>
 * <p>描述: 内存大小类型 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 11:26 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public enum MemorySizeType {
    BYTE(1),
    KB(1024),
    MB(1048576),
    GB(1073741824),
    TB(1099511627776L);
    private long size;

    MemorySizeType(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
