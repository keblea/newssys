package cn.com.oceansoft.osc.ms.service;

import cn.com.oceansoft.osc.ms.domain.ApiQueryBase;
import cn.com.oceansoft.osc.ms.domain.News;
import cn.ucox.web.framework.view.ApiResult;

public interface NewsService {
    ApiResult deleteByPrimaryKey(String guid);
    ApiResult insert(News news);
    ApiResult updateByPrimaryKey(News news);
    ApiResult selectByPrimaryKey(String guid);
    ApiResult getNewsByTitle(String title);
    ApiResult getNews(ApiQueryBase query);
    ApiResult getNewsById(String guid);
    ApiResult getAllNews();
    ApiResult getNewsList(String guid,
                          String title,
                          String content,
                          String author,
                          String source,
                          String publicTime,
                          String newsType);
    ApiResult updateIsDel(News news);
}
