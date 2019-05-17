package cn.com.oceansoft.osc.ms.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author wuzy
 * @created 2017-02-13-16:00
 * @email wzy@oceansoft.com.cn
 */
public class ApiQueryBase {

    private int  ps=10;
    private int pi =1;
    private String sd;
    private String  sb    ;
    private String kw    ;

    private Map<String, Object> conditions = new HashMap<>();
    // 条件参数格式:q_my_name_s_lk
    // my_name为数据库字段名，大小写按数据库来，有前缀的加前缀t.my_name
    // s代表字符串,n数字,d日期,dd长格式日期
    // lk代表like,lt 代表 >,eq 代表 =，....
    // 以and开头
    private String where;

    public Map<String, Object> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Object> conditions) {
        this.conditions = conditions;
    }


    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getPi() {
        return pi;
    }

    public void setPi(int pi) {
        this.pi = pi;
    }

    /**
     * 过滤非法字符，防sql注入
     * @return
     */
    public String getSd() {
        if(sd!=null&&!Pattern.matches("[\\w.]*",sd)){
            return null;
        }
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getSb() {
        if(sb!=null&&!Pattern.matches("[\\w.]*",sb)){
            return null;

        }
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getWhere() {
        return where;
    }
    //去掉setWhere方法，否则要处理sql注入问题

}
