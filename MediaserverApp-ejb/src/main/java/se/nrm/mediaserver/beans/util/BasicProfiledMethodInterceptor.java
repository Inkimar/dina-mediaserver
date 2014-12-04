package se.nrm.mediaserver.beans.util;

import java.lang.reflect.Method;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.log4j.Logger;

/**
 * http://blog.javabenchmark.org/2013/03/write-your-own-profiler-with-jee6.html
 * obs : create the beans.xml in META-INF, configure log4j
 *
 * @author ingimar
 */
/**
 * An interceptor for profiling methods that have the
 * <code>@Profiled</code> annotation.
 */
@Profiled
@Interceptor
public class BasicProfiledMethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(BasicProfiledMethodInterceptor.class);

    /**
     * starts a stopwatch before a given method execution and stops it after the
     * method is proceeded. Then logs the elapsed time.
     *
     * @param context the invocation context.
     * @return the object produced by the method execution.
     * @throws Exception if an error occurs.
     */
    @AroundInvoke
    public Object profile(InvocationContext context) throws Exception {
        System.out.println("inne i profiler klockan 13:45");
        // starts the stopwatch
        final long startTime = System.currentTimeMillis();

        // executes the method that is profiled
        Object o = context.proceed();

        // stops the stopwatch and computes elapsed time
        final long elapsedTime = System.currentTimeMillis() - startTime;
        Method method = context.getMethod();
       
        // logs method and time
        LOGGER.info("Profiled Method: "+method + " in "+elapsedTime+" ms");
        return o;
    }
}
