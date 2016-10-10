package com.farmhulian.utils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * 设置PullToRefresh的加载刷新字样,一个工具类
 *  @author 谭杰栖
 */
public class RefreshUtils {
    /**
     * 设置刷新字样
     * @param pullToRefreshBase PullToRefresh控件
     * @param rc  正在刷新的字样
     * @param suc  刷新成功的字样
     * @param lable  刷新字样
     * @param ref   释放就开始刷新
     */
    public static void setPullReContent(PullToRefreshBase pullToRefreshBase, String rc, String suc, String lable, String ref){
        pullToRefreshBase.getLoadingLayoutProxy(true,false).setRefreshingLabel(rc); //正在刷新
        pullToRefreshBase.getLoadingLayoutProxy(true,false).setLastUpdatedLabel(suc + DateUtils.formatTime(System.currentTimeMillis()));
        pullToRefreshBase.getLoadingLayoutProxy(true,false).setPullLabel(lable);
        pullToRefreshBase.getLoadingLayoutProxy(true,false).setReleaseLabel(ref);
    }

    /**
     * 设置加载字样
     * @param pullToRefreshBase PullToRefresh控件
     * @param rc  正在加载的字样
     * @param suc  加载成功的字样
     * @param lable  加载字样
     * @param ref   释放就开始加载
     */
    public static void setPullMoreContent(PullToRefreshBase pullToRefreshBase,String rc, String suc, String lable, String ref){
        pullToRefreshBase.getLoadingLayoutProxy(false,true).setRefreshingLabel(rc); //正在刷新
        pullToRefreshBase.getLoadingLayoutProxy(false,true).setLastUpdatedLabel(suc + DateUtils.formatTime(System.currentTimeMillis()));
        pullToRefreshBase.getLoadingLayoutProxy(false,true).setPullLabel(lable);
        pullToRefreshBase.getLoadingLayoutProxy(false,true).setReleaseLabel(ref);
    }
}
