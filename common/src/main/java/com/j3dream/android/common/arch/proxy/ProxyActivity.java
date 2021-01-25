package com.j3dream.android.common.arch.proxy;

import com.j3dream.android.common.arch.IBaseView;

/**
 * <p>文件名称: ProxyActivity </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp.proxy</p>
 * <p>描述: 代理活动 </p>
 * <p>创建时间: 2020/4/24 11:21 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ProxyActivity<V extends IBaseView> extends ProxyImpl {
    public ProxyActivity(IBaseView view) {
        super(view);
    }
}
