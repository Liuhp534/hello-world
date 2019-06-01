package cn.liu.hui.peng.excel.ticket;

import cn.liu.hui.peng.date.CustomDateUtils;
import cn.liu.hui.peng.excel.CallRecordDTO;
import cn.liu.hui.peng.excel.SeatDTO;
import cn.liu.hui.peng.excel.TicketData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author hz16092620
 * @date 2018年3月9日 下午5:34:44
 */
public class TicketImport {


    static final List<TicketData> ticketDatas = new ArrayList<>(1600);

    public static void main(String[] args) throws IOException {
        //解析数据
        setObjs(0);
        /*setObjs(1);
        setObjs(2);
        setObjs(3);
        setObjs(4);
        setObjs(5);
        setObjs(6);
        setObjs(7);
        setObjs(8);
        setObjs(9);*/
        //setObjs(10);
        System.out.println(ticketDatas.size());
        Collections.sort(ticketDatas, new Comparator<TicketData>() {
            @Override
            public int compare(TicketData o1, TicketData o2) {
                if (o1.getPeriodNum() == o2.getPeriodNum()) {
                    return 0;
                } else if (o1.getPeriodNum() > o2.getPeriodNum()) {
                    return 1;
                } else if (o1.getPeriodNum() < o2.getPeriodNum()) {
                    return -1;
                }
                return 0;
            }
        });
        JdbcUtils.insert(ticketDatas);
    }


    public static void setObjs(int sheetNum) {
        File file = new File("C:\\Users\\liuhp\\Desktop\\ticket\\ticketDataTemp.xls");
        // 获取excel文档
        POIFSFileSystem fs;
        HSSFWorkbook wb;
        HSSFSheet sheetMain;
        int count = 0;
        int size = 0;
        try {
            fs = new POIFSFileSystem(new FileInputStream(file));
            wb = new HSSFWorkbook(fs);
            // 读取第一个Sheet
            sheetMain = wb.getSheetAt(sheetNum);
            int totalRow = sheetMain.getLastRowNum() + 1;
            int anlyzeExcelStart = 1;
            TicketData ticketData = new TicketData();
            ticketData.setDataType(1);
            int local = 0;
            HSSFRow row = null;
            Cell temp = null;
            String tempStr = null;
            DecimalFormat df = new DecimalFormat("0");
            for (int i = 0; i < totalRow; i++) {
                if (local >= 15) {
                    size ++;
                    local = 0;
                    ticketDatas.add(ticketData);
                    ticketData = new TicketData();
                    ticketData.setDataType(1);
                }
                local ++;
                row = sheetMain.getRow(i);
                temp = row.getCell(0);
                tempStr = temp == null ? "" : temp.toString();
                if (local == 1) {
                    configPeriodNum(ticketData, tempStr);
                } else {
                    configAnimal(ticketData, tempStr, local);
                }
                if (i == (totalRow - 1)) {
                    size ++;
                    ticketDatas.add(ticketData);
                }
                count++;// 统计解析行数
            }
        } catch (IOException e) {
            System.out.println("出错的行数: " + count);
            e.printStackTrace();
        }
        System.out.println(sheetNum + "_" + size);
    }

    /*设置生肖*/
    private static void configAnimal(TicketData ticketData, String rowData, int local) {
        if (StringUtils.isEmpty(rowData)) {
            return;
        }
        String animal = rowData.split("/")[0];
        if (local == 3) {
            ticketData.setPosition1(animal);
        } else if (local == 5) {
            ticketData.setPosition2(animal);
        } else if (local == 7) {
            ticketData.setPosition3(animal);
        } else if (local == 9) {
            ticketData.setPosition4(animal);
        } else if (local == 11) {
            ticketData.setPosition5(animal);
        } else if (local == 13) {
            ticketData.setPosition6(animal);
        } else if (local == 15) {
            ticketData.setSpecial(animal);
        }
    }

    /*获取年份和期数*/
    private static void configPeriodNum(TicketData ticketData, String rowData) {
        if (StringUtils.isEmpty(rowData)) {
            return;
        }
        //获取时间
        int timeStart = rowData.indexOf(" ");
        int timeEnd = rowData.lastIndexOf(" ");
        String dateTime = rowData.substring(timeStart+1, timeEnd);
        //获取期数
        int periodStart = rowData.indexOf("第");
        int periodEnd = rowData.indexOf("期");
        int periodTime = rowData.indexOf("年");
        String year = rowData.substring(timeStart+1, periodTime);
        String period = year + rowData.substring(periodStart+1, periodEnd);

        Date date = CustomDateUtils.string2Date(dateTime, CustomDateUtils.YYYYNMMYDDR);
        ticketData.setCreateTime(CustomDateUtils.date2String(date, CustomDateUtils.YYYY_MM_DD));
        ticketData.setPeriodNum(Integer.valueOf(period));
    }
}
 