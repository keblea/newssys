package cn.com.oceansoft.osc.ms.domain;

import cn.ucox.web.framework.exception.ParameterInvalidationException;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author wuzy
 * @created 2017-02-10-15:24
 * @email wzy@oceansoft.com.cn
 */
public class ApiQueryResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ApiQuery.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (binderFactory==null) {
            return null;
        }
        Class<?> targetType=parameter.getParameterType();

        // 获取查询条件map，所有查询都是and关系
        //查询条件，以and开头
        String queryPrefix="q_";
        StringBuilder where=new StringBuilder();
        Map<String, Object> conditions = new HashMap<>();
        Iterator<String> it=webRequest.getParameterNames();
        while(it.hasNext()){
            String name=it.next();
            if(name.startsWith(queryPrefix)){
                conditions.put(name,webRequest.getParameter(name));
                String condition=getCondition(name,webRequest.getParameter(name),queryPrefix);
                where.append(condition);
            }
        }
        System.out.println("****   where:"+where+"   ******");
        //end
        ApiQuery apiQuery=parameter.getParameterAnnotation(ApiQuery.class);
        String prefix=getprefix(apiQuery,targetType);

        Field[] fields=targetType.getDeclaredFields();
        Object target=targetType.newInstance();
        WebDataBinder binder = binderFactory.createBinder(webRequest, null,prefix);
        for(Field field:fields){
            field.setAccessible(true);
            String fieldName=field.getName();
            Class<?> fieldType=field.getType();
            try {
                Object arg = binder.convertIfNecessary(webRequest.getParameter(fieldName),fieldType, parameter);
                if("conditions".equals(fieldName)){
                    field.set(target, conditions);
                }else if("where".equals(fieldName)){
                    field.setAccessible(true);
                    field.set(target, where.toString());
                    field.setAccessible(false);
                }else if(arg!=null) {
                    field.set(target, arg);
                }
            }

            catch (Exception e)
            {
                throw  new ParameterInvalidationException();
            }


        }
        return target;
    }
    // 条件参数格式:query_name_s_lk，s代表字符串,lk代表like
    private String getCondition(String name, String value,String prefix) throws ParameterInvalidationException {

        String[] ss=name.split("_");
        String[] args=new String[4];
        if(ss.length>=4){
            args[3]=ss[ss.length-1];//lk
            args[2]=ss[ss.length-2];//s
            args[1]=name.substring(prefix.length(),name.length()-args[2].length()-args[3].length()-2);//name
            args[0]=ss[0];//query

            if((StringUtils.isEmpty(value)||"null".equals(value))&&!"n".equals(args[3])&&!"nn".equals(args[3])){
                return "";
            }
            StringBuilder condition=new StringBuilder(" and ");
            condition.append(args[1]);
            switch (args[3]){
                case "ge":
                    condition.append(" >= ");
                    break;
                case "gt":
                    condition.append(" > ");
                    break;
                case "le":
                    condition.append(" <= ");
                    break;
                case "lt":
                    condition.append(" < ");
                    break;
                case "eq":
                    condition.append(" = ");
                    break;
                case "ne":
                    condition.append(" != ");
                    break;
                case "lk":
                    condition.append(" like ");
                    break;
                case "nlk":
                    condition.append(" not like ");
                    break;
                case "llk":
                    condition.append(" like ");
                    break;
                case "rlk":
                    condition.append(" like ");
                    break;
                case "n":
                    condition.append(" is null ");
                    break;
                case "nn":
                    condition.append(" is not null ");
                    break;
                case "in":
                    condition.append(" in ");
                    break;
                default:
                    return "";
            }
            if("n".equals(args[2])){
                if(args[3].contains("k")){
                    throw new ParameterInvalidationException(name,args[3]);
                }
                if("in".equals(args[3])){
                    condition.append("("+value+")");
                }else{
                    condition.append(value);
                }
            }else if("d".equals(args[2])){
                if(args[3].contains("k")||"in".equals(args[3])){
                    throw new ParameterInvalidationException(name,args[3]);                 }
                condition.append("to_date('"+value+"','yy-mm-dd')");
            }else if("dd".equals(args[2])){
                if(args[3].contains("k")||"in".equals(args[3])){
                    throw new ParameterInvalidationException(name,args[3]);                 }
                condition.append("to_date('"+value+"','yy-mm-dd hh24:mi:ss')");
            }else if("s".equals(args[2])){
                switch (args[3]){
                    case "lk":
                        condition.append("'%"+value+"%'");
                        break;
                    case "nlk":
                        condition.append("'%"+value+"%'");
                        break;
                    case "llk":
                        condition.append("'%"+value+"'");
                        break;
                    case "rlk":
                        condition.append("'"+value+"%'");
                        break;
                    case "in":
                        String[] values=value.split(",");
                        String v="";
                        for (int i = 0; i < values.length; i++) {
                            if(i>0){
                                v+=",";
                            }
                            v+="'"+values[i]+"'";
                        }
                        condition.append("("+v+")");
                        break;
                    case "n":
                    case "nn":
                        break;
                    default:
                        condition.append("'"+value+"'");
                }

            }else{
                return "";
            }
            String c = condition.toString();
            //简单做下防sql注入处理
            if(value!=null){
                String v=value.toLowerCase();
                if(v.contains("--")||v.contains("exec")||v.contains("delete")||v.contains("master")||v.contains("truncate")||v.contains("declare")||v.contains("create")){
                    throw new ParameterInvalidationException(name,v);                 }
            }
            if(args[1].contains("--")||args[1].contains(" ")||args[1].contains("'")||args[1].contains(";")){
                throw new ParameterInvalidationException(name,args[1]);             }
            return c;
        }
        return "";
    }



    private String getprefix(ApiQuery apiQuery,Class<?> targetType) {
        String prefix=apiQuery.value();
        if("".equals(prefix)){
            prefix=getDefaultClassName(targetType);
        }
        return prefix;
    }
    private String getDefaultClassName(Class<?> targetType) {
        return ClassUtils.getShortNameAsProperty(targetType);
    }
}
