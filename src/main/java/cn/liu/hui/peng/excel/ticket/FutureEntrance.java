package cn.liu.hui.peng.excel.ticket;

/**
 * @description: 预测入口
 * @author: liuhp534
 * @create: 2019-06-30 10:30
 */
public class FutureEntrance {


    public static void main(String[] args) throws Exception {
        JdbcUtils.remote = Boolean.FALSE;
        long start = System.currentTimeMillis();
        positionPrint();
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

    private static void positionPrint() {
        String dataYearStart= "2018-01-01";
        boolean increasePeriodPrintFlag = true;
        boolean increasePeriodCountPrintFlag = false;
        boolean increasePeriodDetail = false;
        allState(dataYearStart,0, 7, "", increasePeriodPrintFlag, increasePeriodCountPrintFlag, increasePeriodDetail);
    }

    private static void allPrint() {
        String printPeriodNum = "2019075";
        String dataYearStart= "2018-01-01";
        fixPosition(dataYearStart, printPeriodNum);
    }

    /*
     * 变动偏移量
     * */
    private static void fixPosition(String dataYearStart, String printPeriodNum) {
        System.out.println("                         || ||                        ");
        System.out.println("                         || ||                        ");
        System.out.println("                         || ||                        ");
        for (int i = 0; i <= 5; i++) {
            System.out.println("=====================偏移量=" + i + "=====================");
            fixShiftCount(dataYearStart, i, printPeriodNum);//没有打印出来说明，连续的很少
            System.out.println("                         || ||                        ");
            System.out.println("                         || ||                        ");
            System.out.println("                         || ||                        ");
        }
    }

    /*
     *固定偏移量，变动位置
     * */
    private static void fixShiftCount(String dataYearStart, int shiftCount, String printPeriodNum) {
        //固定偏移量
        for (int i = 7; i >= 1; i--) {
            allState(dataYearStart, shiftCount, i, printPeriodNum);
        }
    }

    /*
     * 各个位置的指定偏移量的概况
     * */
    private static void allState(String dataYearStart, int shiftCount, int hitPosition, String printPeriodNum) {
        allState(dataYearStart, shiftCount, hitPosition, printPeriodNum, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
    }

    /*
     * 各个位置的指定偏移量的概况
     * */
    private static void allState(String dataYearStart, int shiftCount, int hitPosition, String printPeriodNum, boolean increasePeriodPrintFlag,
                                 boolean increasePeriodCountPrintFlag, boolean increasePeriodDetail) {
        try {
            RecordHistorySeries.dataYearStart = dataYearStart;
            RecordHistorySeries.increasePeriodPrintFlag = increasePeriodPrintFlag;//是否开启汇总
            //记录期数的汇总情况，将范围内的汇合到一起，并且按照最大的优先排序
            RecordHistorySeries.increasePeriodCountPrintFlag = increasePeriodCountPrintFlag;//默认执行操作
            RecordHistorySeries.increasePeriodDetail = increasePeriodDetail;//默认执行操作,是否打印范围汇合详情
            RecordHistorySeries.shiftCount = shiftCount;//偏移量0-5
            RecordHistorySeries.hitPosition = hitPosition;//命中的位置1-7
            RecordHistorySeries.printPeriodNum = printPeriodNum;//指定打印哪期，不指定打印全部
            RecordHistorySeries.createAllRepeatResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("------------------------------------出错了-----------------------------");
        }
    }
}
