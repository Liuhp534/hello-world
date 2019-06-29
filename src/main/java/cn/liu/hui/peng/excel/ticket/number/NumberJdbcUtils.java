package cn.liu.hui.peng.excel.ticket.number;

import cn.liu.hui.peng.excel.TicketData;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 数据库连接池
 * @author: liuhp534
 * @create: 2019-05-25 18:13
 */
public class NumberJdbcUtils {

    public static void main(String[] args) {
        System.out.println(getConn());
        getAll();
    }


    /*获取所有的数据无视删除状态，更具期数排序，降序*/
    public static List<TicketData> getAll() {
        List<TicketData> ticketDatas = new ArrayList<>();
        TicketData ticketData = null;
        Connection conn = getConn();
        String sql = "select * from ticket_number_data  order by period_num desc ";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("=============获取数据开始===============");
            while (rs.next()) {
                ticketData = new TicketData();
                ticketData.setId(rs.getInt(1));
                ticketData.setPeriodNum(rs.getInt(2));
                ticketData.setSpecial(rs.getString(10));
                ticketDatas.add(ticketData);
            }
            System.out.println("=============获取数据总量=" +  ticketDatas.size()+ "===============");
            System.out.println("=============获取数据结束===============");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketDatas;
    }

    /*获取所有的数据无视删除状态，更具期数排序，降序*/
    public static List<TicketData> getAllBySql(String sql) {
        List<TicketData> ticketDatas = new ArrayList<>();
        TicketData ticketData = null;
        Connection conn = getConn();
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("=============获取数据开始===============");
            while (rs.next()) {
                ticketData = new TicketData();
                ticketData.setId(rs.getInt(1));
                ticketData.setPeriodNum(rs.getInt(2));
                ticketData.setPosition1(rs.getString(4));
                ticketData.setPosition2(rs.getString(5));
                ticketData.setPosition3(rs.getString(6));
                ticketData.setPosition4(rs.getString(7));
                ticketData.setPosition5(rs.getString(8));
                ticketData.setPosition6(rs.getString(9));
                ticketData.setSpecial(rs.getString(10));
                ticketDatas.add(ticketData);
            }
            System.out.println("=============获取数据总量=" +  ticketDatas.size()+ "===============");
            System.out.println("=============获取数据结束===============");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketDatas;
    }

    public static int insert(List<TicketData> ticketDatas) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into ticket_number_data " +
                "(period_num, data_type, position1, position2, position3, position4, position5, position6, special, create_time) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            for (TicketData ticketData : ticketDatas) {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                pstmt.setInt(1, ticketData.getPeriodNum());
                pstmt.setInt(2, ticketData.getDataType());
                pstmt.setString(3, ticketData.getPosition1());
                pstmt.setString(4, ticketData.getPosition2());
                pstmt.setString(5, ticketData.getPosition3());
                pstmt.setString(6, ticketData.getPosition4());
                pstmt.setString(7, ticketData.getPosition5());
                pstmt.setString(8, ticketData.getPosition6());
                pstmt.setString(9, ticketData.getSpecial());
                pstmt.setString(10, ticketData.getCreateTime());
                i = pstmt.executeUpdate();
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /*预测之前将需要预测的删除状态*/
    public static int updateByPeriodNumToDeleted(int periodNum) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update ticket_number_data set deleted = 1 where period_num >= ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, periodNum);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /*获取预测使用的历史数据*/
    public static List<TicketData> getAllForCalculate() {
        List<TicketData> ticketDatas = new ArrayList<>();
        TicketData ticketData = null;
        Connection conn = getConn();
        String sql = "select * from ticket_number_data where create_time >= '2017-01-01' and deleted=0 order by period_num desc ";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            //System.out.println("=============获取历史数据数据开始===============");
            while (rs.next()) {
                ticketData = new TicketData();
                ticketData.setId(rs.getInt(1));
                ticketData.setPeriodNum(rs.getInt(2));
                ticketData.setPosition1(rs.getString(4));
                ticketData.setPosition2(rs.getString(5));
                ticketData.setPosition3(rs.getString(6));
                ticketData.setPosition4(rs.getString(7));
                ticketData.setPosition5(rs.getString(8));
                ticketData.setPosition6(rs.getString(9));
                ticketData.setSpecial(rs.getString(10));
                ticketDatas.add(ticketData);
            }
            System.out.println("=============获取历史数据数据总量=" +  ticketDatas.size()+ "===============");
            //System.out.println("=============获取历史数据数据结束===============");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketDatas;
    }

    /*获取被预测的数据*/
    public static TicketData getDeletedForCalculate() {
        TicketData ticketData = null;
        Connection conn = getConn();
        String sql = "select * from ticket_number_data where deleted=1 order by period_num asc limit 1 ";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                ticketData = new TicketData();
                ticketData.setId(rs.getInt(1));
                ticketData.setPeriodNum(rs.getInt(2));
                ticketData.setPosition1(rs.getString(4));
                ticketData.setPosition2(rs.getString(5));
                ticketData.setPosition3(rs.getString(6));
                ticketData.setPosition4(rs.getString(7));
                ticketData.setPosition5(rs.getString(8));
                ticketData.setPosition6(rs.getString(9));
                ticketData.setSpecial(rs.getString(10));
            }
            //System.out.println("=============获取被预测的数据=" +  (ticketData == null ? "无更多了" : ticketData.getPeriodNum()) + "===============");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketData;
    }

    /*将被预测的数据恢复状态*/
    public static int updateByPeriodNum(int periodNum) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update ticket_number_data set deleted = 0 where period_num = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, periodNum);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /*修复所有数据为正常状态*/
    public static void repeatAllData() {
        Connection conn = getConn();
        int i = 0;
        String sql = "update ticket_number_data set deleted = 0";
        PreparedStatement pstmt = null;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            System.out.println("==========修复所有数据为正常状态==========");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean remote = Boolean.FALSE;

    private static Connection getConn() {
        //remote = true;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/project?characterEncoding=utf-8";
        String username = "root";
        String password = "123456";
        if (remote) {
            url = "jdbc:mysql://119.23.27.169:3306/project?characterEncoding=utf-8";
            username = "root";
            password = "#Liuhp1990";
        }
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
