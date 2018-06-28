package hjin.commons.web.service;

import hjin.commons.web.bean.BaseUrlBean;

import java.util.Map;

public interface IBaseUrlService {
    Map<String, ? extends BaseUrlBean> getActiveUrls(boolean forceRefresh);
}
