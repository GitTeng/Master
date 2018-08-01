package tv.huan.master.common.spring;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/11/16 0016
 * Time: 15:13
 * To change this template use File | Settings | File Templates
 */
public class MyPerformanceMonitorInterceptor extends PerformanceMonitorInterceptor {
    private Long maxAllowedTimeMillis = 5000L;

    public MyPerformanceMonitorInterceptor() {
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = this.createInvocationTraceName(invocation);
        long beginTime = System.currentTimeMillis();

        Object var6;
        try {
            var6 = invocation.proceed();
        } finally {
            long runTime = System.currentTimeMillis() - beginTime;
            if (runTime > this.maxAllowedTimeMillis.longValue()) {
                this.defaultLogger.error("方法执行时间是 " + runTime + " ms，已超出速度允许值，请检查方法的执行效率:" + name);
            }

        }

        return var6;
    }

    protected boolean isLogEnabled(Log logger) {
        return true;
    }

    public Long getMaxAllowedTimeMillis() {
        return this.maxAllowedTimeMillis;
    }

    public void setMaxAllowedTimeMillis(Long maxAllowedTimeMillis) {
        this.maxAllowedTimeMillis = maxAllowedTimeMillis;
    }
}
