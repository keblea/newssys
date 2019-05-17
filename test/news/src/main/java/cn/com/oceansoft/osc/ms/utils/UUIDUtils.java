package cn.com.oceansoft.osc.ms.utils;

import java.util.UUID;

/**
 * Created by xieqy on 2019/5/10
 */
public class UUIDUtils {
   /* public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }*/

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
