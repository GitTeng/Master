package tv.huan.master.common.util;

import java.io.IOException;
import java.util.Properties;
  
/**
 * 用于读取config.properties 配置文件
 * @Description: TODO
 * @author lixw 
 * @date 2014-3-1 下午12:53:06 
 
*/ 
public class ConfigTools {  
  
    private static Properties p = new Properties();
    static {  
        try {  
        	p.load(ConfigTools.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {  
            System.out.println("读取配置信息出错！");  
        }  
    }  
  
    /** 
     * 根据key得到value的值 
     */  
    public static String getValue(String key) {
        return p.getProperty(key);
    }
 }
