package cn.com.oceansoft.osc.ms.service.impl;

import cn.com.oceansoft.osc.ms.domain.ApiQueryBase;
import cn.com.oceansoft.osc.ms.domain.News;
import cn.com.oceansoft.osc.ms.persistence.NewsMapper;
import cn.com.oceansoft.osc.ms.service.NewsService;
import cn.ucox.web.framework.view.ApiResult;
import cn.com.oceansoft.osc.ms.domain.ApiPager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    NewsMapper newsMapper;
    @Override
    public ApiResult deleteByPrimaryKey(String guid) {
        int result = newsMapper.deleteByPrimaryKey(guid);
        if(result > 0){
            return new ApiResult<ApiPager<News>>().success("删除成功");
        }else{
            return new ApiResult<ApiPager<News>>().success("删除失败");
        }
    }

    @Override
    public ApiResult insert(News news) {
        int result = newsMapper.insert(news);
        if(result > 0){
            return new ApiResult<ApiPager<News>>().success("增加成功");
        }else{
            return new ApiResult<ApiPager<News>>().success("增加失败");
        }
    }

    @Override
    public ApiResult updateByPrimaryKey(News news) {
        int result = newsMapper.updateByPrimaryKey(news);
        if(result > 0){
            return new ApiResult<ApiPager<News>>().success("修改成功");
        }else{
            return new ApiResult<ApiPager<News>>().success("修改失败");
        }

    }

    @Override
    public ApiResult selectByPrimaryKey(String guid) {
        News n = newsMapper.selectByPrimaryKey(guid);
        return new ApiResult<News>().success(n);
    }

    @Override
    public ApiResult getNewsByTitle(String title) {
        List<News> result =newsMapper.getNewsByTitle(title);
        if(result.isEmpty()){
            return  new ApiResult<List<News>>().success("该新闻不存在");
        }else {
            return  new ApiResult<List<News>>().success(result);
        }
    }

    @Override
    public ApiResult getNews(ApiQueryBase query) {
        PageHelper.startPage(query.getPi(), query.getPs(), true);
        Page<News> page=(Page<News>)newsMapper.getNews(query);
        ApiPager<News> apiPager=new ApiPager<News>(query.getPs(),query.getPi(),page.getTotal(),page.getResult());
        return new ApiResult<ApiPager<News>>().success(apiPager);
    }

    @Override
    public ApiResult getNewsById(String guid) {
        News n = newsMapper.getNewsById(guid);
        return new ApiResult<News>().success(n);
    }

    @Override
    public ApiResult getAllNews() {
        List<News> result =newsMapper.getAllNews();
        if(result.isEmpty()){
            return  new ApiResult<List<News>>().success("查询所有新闻失败！");
        }else {
            return  new ApiResult<List<News>>().success(result);
        }
    }

    @Override
    public ApiResult getNewsList(String guid, String title, String content, String author, String source, String publicTime, String newsType) {
        List<News> result = newsMapper.getNewsList(guid,title,content,author,source,publicTime,newsType);
        if(result.isEmpty()){
            return  new ApiResult<List<News>>().success("查询新闻失败！");
        }else {
            return  new ApiResult<List<News>>().success(result);
        }
    }

    public ApiResult updateIsDel(News news){
        int result = newsMapper.updateIsDel(news);
        if(result > 0){
            return new ApiResult<ApiPager<News>>().success("逻辑删除成功");
        }else{
            return new ApiResult<ApiPager<News>>().success("逻辑删除失败");
        }
    }

}
