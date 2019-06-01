package cn.liu.hui.peng.excel.ticket;

import cn.liu.hui.peng.date.CustomDateUtils;
import cn.liu.hui.peng.excel.TicketData;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 数据库连接池
 * @author: liuhp534
 * @create: 2019-05-25 18:13
 */
public class JdbcUtils {

    public static void main(String[] args) {
        System.out.println(getConn());
        getAll();
    }


    public static List<TicketData> getAll() {
        List<TicketData> ticketDatas = new ArrayList<>();
        TicketData ticketData = null;
        Connection conn = getConn();
        String sql = "select * from ticket_data where create_time >= '2018-01-01'  order by period_num desc ";
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

    public static int insert(List<TicketData> ticketDatas) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into ticket_data " +
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
        String sql = "update ticket_data set deleted = 1 where period_num >= ?";
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
        String sql = "select * from ticket_data where deleted=0 order by period_num desc ";
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
        String sql = "select * from ticket_data where deleted=1 order by period_num asc limit 1 ";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                ticketData = new TicketData();
                ticketData.setId(rs.getInt(1));
                ticketData.setPeriodNum(rs.getInt(2));
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
        String sql = "update ticket_data set deleted = 0 where period_num = ?";
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

    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/project";
        String username = "root";
        String password = "123456";
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