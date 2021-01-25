package com.j3dream.core.util.idworker;

import com.j3dream.core.util.RandomUtils;

/**
 * <p>文件名称: UuidIdWork </p>
 * <p>所属包名: cn.ftxxkj.system.verification.common.util.idworker</p>
 * <p>描述: 设备ID 工作业务 </p>
 * <p>创建时间: 2020/8/12 09:15 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
class UuidIdWork implements IdWork {

    @Override
    public String nextId() {
        return RandomUtils.randomUUIDString();
    }
}
