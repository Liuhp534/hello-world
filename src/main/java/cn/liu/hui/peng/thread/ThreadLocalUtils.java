package cn.liu.hui.peng.thread;

import cn.liu.hui.peng.wheel.CustomDateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 程序执行用时工具
 * @author: liuhp534
 * @create: 2019-03-24 10:26
 */
public class ThreadLocalUtils {


    public static void main(String[] args) {
        ThreadLocalUtils.start("excute");
        ThreadLocalUtils utils = new ThreadLocalUtils();
        utils.excute();
        ThreadLocalUtils.end("excute");
    }

    public void excute() {

        System.out.println("do something....start");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do something....end");
    }

    private static ThreadLocal<Map<String, Date>> threadLocal = new ThreadLocal<>();

    /**
    * @Description:
    *
    *方法执行调用
    *
    * @param
    * @return
    * @throws
    * @author Liuhp534
    * @date 2019/3/24 10:42
    */
    public static void start(String method) {
        if (null == method) {
            System.out.println("参数为空");
            return;
        }
        Map<String, Date> startMap = new HashMap<>();
        Date startDate = new Date();
        startMap.put(method, startDate);
        threadLocal.set(startMap);
        System.out.println(String.
                format("开始执行 %s 时间 %s", method, CustomDateUtils.date2String(startDate, CustomDateUtils.YYYY_MM_DD_HH_mm_ss)));
    }

    /**
    * @Description:
    *
    *方法执行结束调用
    *
    * @param
    * @return
    * @throws
    * @author Liuhp534
    * @date 2019/3/24 10:42
    */
    public static void end(String method) {
        Map<String, Date> startMap = threadLocal.get();
        Date endDate = new Date();
        Date startDate = startMap.get(method);
        System.out.println(String.
                format("执行结束 %s 时间 %s 用时 %s", method,
                        CustomDateUtils.date2String(endDate, CustomDateUtils.YYYY_MM_DD_HH_mm_ss), (endDate.getTime() - startDate.getTime())));
    }
}
