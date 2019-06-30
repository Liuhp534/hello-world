package cn.liu.hui.peng.excel.ticket;

/**
 * @description: 预测入口
 * @author: liuhp534
 * @create: 2019-06-30 10:30
 */
public class FutureEntrance {

    public static void main(String[] args) throws Exception {
        //固定偏移量，变动位置 只能一个个执行，太多的
        /*allState(0, 1, "2019073");
        allState(0, 2, "2019073");
        allState(0, 3, "2019073");
        allState(0, 4, "2019073");
        allState(0, 5, "2019073");
        allState(0, 6, "2019073");
        allState(0, 7, "2019073");*/
        String printPeriodNum = "2019073";
        fixPosition(printPeriodNum);
        //allState(0, 7, "2019073");
    }

    /*
    * 变动偏移量
    * */
    private static void fixPosition(String printPeriodNum) {
        System.out.println("                         || ||                        ");
        System.out.println("                         || ||                        ");
        System.out.println("                         || ||                        ");
        for (int i = 0; i <= 5; i++) {
            System.out.println("=====================偏移量=" + i + "=====================");
            fixShiftCount(i, printPeriodNum);//没有打印出来说明，连续的很少
            System.out.println("                         || ||                        ");
            System.out.println("                         || ||                        ");
            System.out.println("                         || ||                        ");
        }
    }

    /*
     *固定偏移量，变动位置
     * */
    private static void fixShiftCount(int shiftCount, String printPeriodNum) {
        //固定偏移量
        for (int i = 7; i >= 1; i--) {
            allState(shiftCount, i, printPeriodNum);
        }
    }

    /*
     * 各个位置的指定偏移量的概况
     * */
    private static void allState(int shiftCount, int hitPosition, String printPeriodNum) {
        try {
            //记录期数的汇总情况，将范围内的汇合到一起，并且按照最大的优先排序
            RecordHistorySeries.increasePeriodCountPrintFlag = Boolean.TRUE;//默认执行操作
            RecordHistorySeries.increasePeriodDetail = Boolean.TRUE;//默认执行操作,是否打印范围汇合详情
            RecordHistorySeries.shiftCount = shiftCount;//偏移量0-5
            RecordHistorySeries.hitPosition = hitPosition;//命中的位置1-7
            RecordHistorySeries.printPeriodNum = printPeriodNum;//指定打印哪期，不指定打印全部
            RecordHistorySeries.createAllRepeatResult();
        } catch (Exception e) {
            System.out.println("------------------------------------出错了-----------------------------");
        }
    }
}
