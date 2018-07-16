package hjin.commons.web.service;

import hjin.commons.web.bean.BaseUrlBean;

import java.util.Map;

/**
 * 全站url服务
 */
public interface IBaseUrlService {
    /**
     * 获取可用的urls
     *
     * @param forceRefresh 强制重新获取
     * @return
     */
    Map<String, ? extends BaseUrlBean> getActiveUrls(boolean forceRefresh);

    /**
     * 获取可用的urls.(动态数据源时,指定dbid)
     *
     * @param dbid         从指定数据源获取
     * @param forceRefresh 强制重新获取
     * @return
     */
    Map<String, ? extends BaseUrlBean> getActiveUrls(String dbid, boolean forceRefresh);
}
