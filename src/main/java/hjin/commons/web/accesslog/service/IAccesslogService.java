package hjin.commons.web.accesslog.service;


import hjin.commons.web.accesslog.HttpRequestBean;

import java.io.Serializable;

/**
 * @author xinqian
 * @cratedate 2016-09-01 10:22:12
 */
public interface IAccesslogService {
    /**
     * 初始化日志对象
     *
     * @param request
     * @param clzName
     * @param methodName
     * @param userid
     * @param logContent
     * @return
     */
    public Serializable initAccessLog(HttpRequestBean request, String clzName, String methodName, String userid,
                                      String logContent);

    /**
     * simple
     *
     * @param log
     */
    public void insertAccessLog(Serializable log);
}
