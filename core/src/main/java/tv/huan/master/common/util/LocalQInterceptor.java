/**
 * hotel
 * QueryResInterceptor.java
 * QueryResInterceptor
 */
package tv.huan.master.common.util;

import org.hibernate.EmptyInterceptor;

/**
 * Hibernate拦截SQL，动态改变表名 
 *
 * @author hec
 * @version 1.0
 * @date 2011-9-6 下午3:27:46
 */
public class LocalQInterceptor extends EmptyInterceptor {


	private String langTable;
	
	public String onPrepareStatement(String sql) {  
		if(null == langTable)
			return sql;
        //根据需求替换sql中的表名  
		String prosql = sql.replaceAll("pushrecord", langTable);
        return prosql;
    }

	public void setLangTable(String langTable) {
		this.langTable = langTable;
	}  

}
