package cn.liu.hui.peng.excel.ticket.number;

/**
 * @description: 数字排除入口
 * @author: liuhp534
 * @create: 2019-06-30 13:35
 */
public class NumberFuturnEntrance {


    public static void main(String[] args) {
        String printPeriodNum = "2019073";
        //allState(5, 7, printPeriodNum);
        //fixPosition(printPeriodNum);
        //0-11, 0的组合=5200300， 6=177100=24秒，7=53130=6秒，一般7以上
        //偏移量//0-11，0表示排除23or24个, 1表示21or22, 5表示13or14, 6表示11or12, 7表示9or10, 8表示7or8, 9表示5or6
        fixShiftCount(8, printPeriodNum);
    }

    /*
     * 变动偏移量
     * */
    private static void fixPosition(String printPeriodNum) {
        System.out.println("                         || ||                        ");
        System.out.println("                         || ||                        ");
        System.out.println("                         || ||                        ");
        //偏移量//0-11，0表示排除23or24个, 1表示21or22, 5表示13or14, 6表示11or12, 7表示9or10, 8表示7or8, 9表示5or6
        for (int i = 5; i <= 10; i++) {
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
            NumberRecordHistorySeries.increasePeriodCountPrintFlag = Boolean.TRUE;//记录期数的汇总情况，将范围内的汇合到一起，并且按照最大的优先排序
            NumberRecordHistorySeries.increasePeriodDetail = Boolean.TRUE;//默认执行操作,是否打印范围汇合详情
            //偏移量//0-11，0表示排除23or24个, 1表示21or22, 6表示11or12, 7表示9or10, 8表示7or8, 9表示5or6
            NumberRecordHistorySeries.shiftCount = shiftCount;
            NumberRecordHistorySeries.hitPosition = hitPosition;//命中的位置1-7
            NumberRecordHistorySeries.printPeriodNum = printPeriodNum;//指定打印哪期，不指定打印全部
            NumberRecordHistorySeries.createAllRepeatResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----------------------------出错了-----------------------------");
        }
    }
}
