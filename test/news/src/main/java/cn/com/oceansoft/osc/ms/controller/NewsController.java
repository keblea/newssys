package cn.com.oceansoft.osc.ms.controller;

import cn.com.oceansoft.osc.ms.domain.ApiQueryBase;
import cn.com.oceansoft.osc.ms.domain.News;
import cn.com.oceansoft.osc.ms.service.NewsService;
import cn.com.oceansoft.osc.ms.utils.UUIDUtils;
import cn.ucox.web.framework.view.ApiResult;

import cn.com.oceansoft.osc.ms.domain.ApiQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = "/admin")
@Api(value = "警务公开", description = "警务资讯信息接口")
public class NewsController {
    @Resource
    NewsService newsService;

    @RequestMapping(value = "/version", method = GET)
    @ResponseBody
    public String getVersion() {
        return "version";
    }

    @RequestMapping(value = "/addnews", method = POST)
    @ApiOperation("新闻增加")
    @ApiImplicitParam(value = "增加新闻", name = "addNews")
    @ResponseBody
    public ApiResult addNews(@RequestBody News news) {
        String guid = UUIDUtils.getUUID();
        news.setGuid(guid);
        if(Boolean.valueOf(news.getHot())){
            news.setHot("Y");
        }else{
            news.setHot("N");
        }
        news.setPublicTime(new Date());
        return newsService.insert(news);
    }

    @ResponseBody
    @RequestMapping(value = "/getnewsbyid/{guid}", method = GET)
    @ApiOperation("获取新闻详情")
    @ApiImplicitParam(paramType = "query", name = "guid", dataType = "String", required = true, value = "新闻ID")
    public ApiResult getNewsById(@PathVariable("guid") String guid) {

        return newsService.getNewsById(guid);
    }

    @ResponseBody
    @RequestMapping(value = "/news/{title}", method = GET)
    @ApiOperation("根据新闻标题获取新闻信息")
    @ApiImplicitParam(paramType = "query", name = "title", dataType = "String", required = true, value = "新闻标题")
    public ApiResult getNewsByTitle(@PathVariable("title") String title) {
        return newsService.getNewsByTitle(title);
    }

    @RequestMapping(value = "news/delete/{guid}", method = DELETE)
    @ApiOperation("删除新闻")
    @ApiImplicitParam(value = "要删除新闻的id", name = "deleteByPrimaryKey", required = true)
    @ResponseBody
    public ApiResult deleteByPrimaryKey(@PathVariable String guid) {
        return newsService.deleteByPrimaryKey(guid);
    }

    @RequestMapping(value = "news/update", method = PUT)
    @ApiOperation("修改新闻")
    @ApiImplicitParam(value = "要修改的新闻", name = "updateByPrimaryKey")
    @ResponseBody
    public ApiResult updateByPrimaryKey(@RequestBody News news) {
        if(Boolean.valueOf(news.getHot())){
            news.setHot("Y");
        }else{
            news.setHot("N");
        }
        news.setUpdateTime(new Date());
        return newsService.updateByPrimaryKey(news);
    }

    @RequestMapping(value = "/news/page", method = GET)
    @ApiOperation("分页获取所有新闻")
    @ApiImplicitParam(value = "分页获取所有新闻", name = "page")
    @ResponseBody
    public ApiResult getNews(@ApiQuery ApiQueryBase query, HttpServletRequest request) {
        return newsService.getNews(query);
    }

    @ResponseBody
    @RequestMapping(value = "/getallnews", method = GET)
    @ApiOperation("获取所有新闻数据")
    public ApiResult getAllNews() {

        return newsService.getAllNews();
    }

    @ResponseBody
    @RequestMapping(value = "/getnewslist", method = GET)
    @ApiOperation("根据条件获取新闻数据")
    public ApiResult getNewsList(HttpSession session,
                                @RequestParam(value = "guid")String guid,
                                 @RequestParam(value = "title")String title,
                                 @RequestParam(value = "content")String content,
                                 @RequestParam(value = "author")String author,
                                 @RequestParam(value = "source")String source,
                                 @RequestParam(value = "publicTime")String publicTime,
                                 @RequestParam(value = "newsType")String newsType){
        return newsService.getNewsList(guid,title,content,author,source,publicTime,newsType);
    }
    @RequestMapping(value = "news/updatedel", method = PUT)
    @ApiOperation("修改新闻")
    @ApiImplicitParam(value = "要修改的新闻", name = "updateByPrimaryKey")
    @ResponseBody
    public ApiResult updateIsDel(@RequestBody News news){
        return newsService.updateIsDel(news);
    }
}
