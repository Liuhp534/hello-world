package cn.liu.hui.peng.excel.ticket;

import cn.liu.hui.peng.excel.TicketData;

import java.util.*;

/**
 * @description: 正反次数偏差大的连续确比较稳定，可以作为观察对象
 * @author: liuhp534
 * @create: 2019-06-07 16:43
 */
public class DifferCountPrint {


    /*保存开始最大次数的，不是整个数据的*/
    static Map<String, String> resultMap;

    /*动态的历史数据，预测的时候才能够确定*/
    static List<TicketData> historyTicketDatas;

    /*初始化数据*/
    static {
        MathStack.createHT(false);//初始化组合数据
        Comparator<String> negativeComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {//s1 > s2 返回1是升序，s1 > s2 返回-1是降序
                String[] ss1 = s1.split("_");
                String[] ss2 = s2.split("_");
                if (Integer.valueOf(ss1[1]).intValue() > Integer.valueOf(ss2[1]).intValue()) {
                    return -1;
                } else if (Integer.valueOf(ss1[1]).intValue() < Integer.valueOf(ss2[1]).intValue()) {
                    return 1;
                } else {//如果相等，则比较前面那个数据
                    if (Integer.valueOf(ss1[0]).intValue() > Integer.valueOf(ss2[0]).intValue()) {
                        return -1;
                    } else if (Integer.valueOf(ss1[0]).intValue() < Integer.valueOf(ss2[0]).intValue()) {
                        return 1;
                    } else {//相等的话就表示同一个数据，并且会更新数据，这里很重要啊
                        return 0;
                    }
                }
            }
        };
        resultMap = new TreeMap<>(negativeComparator);
    }

    public static void main(String[] args) {
        JdbcUtils.repeatAllData();//先修复所有数据正常态

        JdbcUtils.repeatAllData();//先修复所有数据正常态
    }



    /*某种特定组合的连续情况，这种就不靠谱，靠历史的结果反推*/
    private static void calculateDifferCount() {
        TicketData futureTicketData = null;
        int startPeriodNum = 2019001;
        int calculateCount = 153;
        String ticketIdStr = "309";
        for (int i = 0; i < calculateCount; i ++) {
            //比如预测2019059，那么包含这期的之后的都是deleted=1，首先执行update语句
            JdbcUtils.updateByPeriodNumToDeleted(startPeriodNum);
            futureTicketData = JdbcUtils.getDeletedForCalculate();//获取隐藏数据的最小的那条，也就是被预测的数据。
            if (null == futureTicketData) {
                System.out.println("该期数不能预测");
                return;
            }
            //获取预测的数据
            historyTicketDatas = JdbcUtils.getAllForCalculate();//每次需要获取需要预测的历史数据，即被预测期数，之前的所有数据
            configResult(historyTicketDatas, MathStack.hMap.get(ticketIdStr), ticketIdStr);
            for (Map.Entry<String, String> entry : resultMap.entrySet()) {
                int tempCount = Integer.valueOf(entry.getKey().split("_")[1]);
                /*if (tempCount > ) {

                }*/
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
            resultMap.clear();//将这一次的统计情况清除
            //预测完成之后需要将被预测的恢复，来预测下期的数据
            JdbcUtils.updateByPeriodNum(startPeriodNum);
            //获取下一个预测
            futureTicketData = JdbcUtils.getDeletedForCalculate();
            if (null == futureTicketData) {//如果不存在下期，那么预测结束
                System.out.println("没有更多了，程序结束，预测完成，预测次数=" + (i + 1));
                break;
            }
            startPeriodNum = futureTicketData.getPeriodNum();
        }
    }


    /*计算每种组合出现连续中的次数,随着历史数据的变动，次数不同，这里是开头连续，不是全部连续*/
    private static void configResult(List<TicketData> ticketDatas, Set<String> hentry, String ticketIdStr) {
        String resulttemp = "";
        int resultcount = 1;
        for (int i = 0; i < ticketDatas.size(); i ++) {
            // 设置 id
            if (hentry.contains(ticketDatas.get(i).getSpecial())) {
                if ("正".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "正";
                } else if ("反".equals(resulttemp)) {//处于正的统计中，到这里表示断开了
                    resultMap.put(ticketIdStr+"_"+resultcount, "反" + resultcount);
                    break;
                }
            } else {
                if ("反".equals(resulttemp)) {
                    resultcount ++;
                } else if ("".equals(resulttemp)) {
                    resultcount = 1;
                    resulttemp = "反";
                } else if ("正".equals(resulttemp)) {//处于正的统计中，到这里表示断开了
                    resultMap.put(ticketIdStr+"_"+resultcount, "正" + resultcount);
                    break;
                }
            }
        }
    }
}
