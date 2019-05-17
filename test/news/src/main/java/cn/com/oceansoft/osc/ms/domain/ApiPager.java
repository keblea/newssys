package cn.com.oceansoft.osc.ms.domain;

import java.util.List;

/**
 * Created by wzy on 2016/10/2.
 */
public class ApiPager<T> {

    /**
     * 客户端分页大小
     */
    public static final int API_PAGE_SIZE = 10;

    private int pageIndex;
    private int totalPage;
    private int pageSize;
    private long totalRow;
    private List<T> list;

    public ApiPager() {

        super();
    }

    public ApiPager(long totalRow, List<T> list, int pageIndex) {
        this(API_PAGE_SIZE, pageIndex, 0 > totalRow ? 0 : totalRow, list);
    }

    public ApiPager(int pageSize, int pageIndex, long totalRow, List<T> list) {
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalRow = 0 > totalRow ? 0 : totalRow;
        this.totalPage = (int) (totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1);
        this.list = list;
    }

    public int getPageIndex() {
        return pageIndex;
    }
    //    public void setPageIndex(int pageIndex) {
    //        this.pageIndex = pageIndex;
    //    }

    public int getTotalPage() {
        return totalPage;
    }
    //    public void setTotalPage(int totalPage) {
    //        this.totalPage = totalPage;
    //    }

    public int getPageSize() {
        return pageSize;
    }
    //    public void setPageSize(int pageSize) {
    //        this.pageSize = pageSize;
    //    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isHasNext() {
        return pageIndex < totalPage;
    }

    public long getTotalRow() {
        return totalRow;
    }
    //    public void setTotalRow(long totalRow) {
    //        this.totalRow = totalRow;
    //    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
