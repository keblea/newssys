package cn.com.oceansoft.osc.ms.persistence;

import cn.com.oceansoft.osc.ms.domain.ApiQueryBase;
import cn.com.oceansoft.osc.ms.domain.News;
import org.apache.ibatis.annotations.Param;


import java.util.List;


public interface NewsMapper {
    int deleteByPrimaryKey(String guid);

    int insert(News news);

    int updateByPrimaryKey(News news);

    int updateIsDel(News news);
    News selectByPrimaryKey(String guid);

    List<News> getNewsByTitle(@Param("title") String title);

    List<News> getNews(ApiQueryBase query);

    News  getNewsById( String guid);

    List<News> getAllNews();
    List<News> getNewsList(@Param(value = "guid") String guid,
                           @Param(value = "title") String title,
                           @Param(value = "content")String content,
                           @Param(value = "author")String author,
                           @Param(value = "source") String source,
                           @Param(value = "publicTime")String publicTime,
                           @Param(value = "newsType") String newsType
                           );
}
