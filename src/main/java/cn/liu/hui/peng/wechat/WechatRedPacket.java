package cn.liu.hui.peng.wechat; 

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * @author	hz16092620 
 * @date	2018年3月10日 下午4:28:56
 * @version      
 */
public class WechatRedPacket {
    
    private static Integer redPacketCount = 15;
    
    private static BigDecimal redPacketMoney = new BigDecimal(200.00);
    
    private static BigDecimal max = null;
	
    private static BigDecimal min = null;
    
    private static FileOutputStream fileOut = null;
    
    public static void main(String[] args) throws FileNotFoundException {
	fileOut = new FileOutputStream("E:/201803_work/redPacket.txt", false);
	String outStr = "";
	try {
	    for (int i = 0; i < 20000; i++) {
		test();
	    }
	    outStr = "最大值:" + max.toString() + " 元"  + "\r\n";
	    fileOut.write(outStr.getBytes());
	    outStr = "最小值:" + min.toString() + " 元"  + "\r\n";
	    fileOut.write(outStr.getBytes());
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
	        fileOut.close();
            } catch (IOException e1) {
        	System.out.println("fileOut 关闭失败！！！");
            }
	}
        
	System.out.println("最大值:" + max.toString() + " 元");
	System.out.println("最小值:" + min.toString() + " 元");
    }
    
    public static void test() throws IOException {
	String outStr = "";
	RedPacket redPacket = new RedPacket();
	redPacket.setRedPacketCount(redPacketCount);
	redPacket.setRedPacketMoney(redPacketMoney);
	 
	BigDecimal total = new BigDecimal(0.00);
	BigDecimal redPacketMoney = null;
	int count = redPacket.getRedPacketCount();
	for (int i = 0; i < count; i++) {
	    redPacketMoney = getWechatMoney(redPacket);
	    if (redPacketMoney.compareTo(new BigDecimal(50)) >= 0) {
		System.out.println("大于50元的红包在第 " + (i + 1) + " 个位置。总共有（" + count + "）个红包。");
	    }
	    if (max == null) {
		max = redPacketMoney;
	    } else {
		max = max.max(redPacketMoney);
	    }
	    if (min == null) {
		min = redPacketMoney;
	    } else {
		min = min.min(redPacketMoney);
	    }
	    outStr = "----------------获取红包金额：" + redPacketMoney.toString() + " 元，还剩 ：" + redPacket.getRedPacketMoney().toString() + " 元。" + "\r\n";
	    fileOut.write(outStr.getBytes());
	    total = total.add(redPacketMoney);
        }
	outStr = "-----------------------------红包总量 ：" + total.toString() + " 元。-----------------------------" + "\r\n";
	fileOut.write(outStr.getBytes());
    }
    
    
    public static BigDecimal getWechatMoney(RedPacket redPacket) {
	if (redPacket == null) {
	    return new BigDecimal(0.00);
	} 
	if (redPacket.getRedPacketCount().intValue() == 1) {
	    BigDecimal result = redPacket.getRedPacketMoney();
	    redPacket.setRedPacketCount(redPacket.getRedPacketCount() - 1);
	    redPacket.setRedPacketMoney(new BigDecimal(0.00));
	    return result;
	}
	BigDecimal minMoney = getMinMoney(redPacket);
	BigDecimal maxMoney = getMaxMoney(redPacket);
	
	BigDecimal avgMaxMoney = getAvgMaxMoney(redPacket);
	//if (isBetweenMinAndMax(avgMaxMoney, minMoney, maxMoney)) {
	    Double d = new Random().nextDouble();
	    avgMaxMoney = avgMaxMoney.multiply(new BigDecimal(d));
	    avgMaxMoney = avgMaxMoney.setScale(2, RoundingMode.HALF_UP);
	    if (isBetweenMinAndMax(avgMaxMoney, minMoney, maxMoney)) {
		redPacket.setRedPacketCount(redPacket.getRedPacketCount() - 1);
		redPacket.setRedPacketMoney(redPacket.getRedPacketMoney().subtract(avgMaxMoney));
		return avgMaxMoney;
	    }
	//}
	
	redPacket.setRedPacketCount(redPacket.getRedPacketCount() - 1);
	redPacket.setRedPacketMoney(redPacket.getRedPacketMoney().subtract(minMoney));
	return minMoney;
    }
    
    private static boolean isBetweenMinAndMax(BigDecimal money, BigDecimal minMoney, BigDecimal maxMoney) {
	if (money.subtract(minMoney).multiply(new BigDecimal(100)).intValue() >= 0 
		&& money.subtract(maxMoney).multiply(new BigDecimal(100)).intValue() <= 0) {
	    return true;
	} else {
	    return false;
	}
    }
    
    private static BigDecimal getAvgMaxMoney(RedPacket redPacket) {
	BigDecimal money = redPacket.getRedPacketMoney();
	BigDecimal result = money.divide(new BigDecimal(redPacket.getRedPacketCount()), 2, RoundingMode.HALF_UP)
		.multiply(new BigDecimal(2));
	return result;
    }
    
    private static BigDecimal getMinMoney(RedPacket redPacket) {
	BigDecimal minMoney = new BigDecimal(0.01);
	BigDecimal otherMaxMoney = new BigDecimal(redPacket.getRedPacketCount() - 1).multiply(new BigDecimal(200.00));
	BigDecimal tempMoney = minMoney.add(otherMaxMoney);
	
	if (tempMoney.subtract(redPacket.getRedPacketMoney()).intValue() > 0) {
	    return minMoney.setScale(2, RoundingMode.HALF_UP);
	} else {
	    return minMoney = redPacket.getRedPacketMoney().subtract(otherMaxMoney).setScale(2, RoundingMode.HALF_UP);
	}
    }
    
    
    private static BigDecimal getMaxMoney(RedPacket redPacket) {
	BigDecimal maxMoney = new BigDecimal(200.00);
	BigDecimal otherMinMoney = new BigDecimal(redPacket.getRedPacketCount() - 1).multiply(new BigDecimal(0.01));
	BigDecimal tempMoney = maxMoney.add(otherMinMoney);
	
	if (tempMoney.subtract(redPacket.getRedPacketMoney()).intValue() <= 0) {
	    return maxMoney;
	} else {
	    maxMoney = redPacket.getRedPacketMoney().subtract(otherMinMoney);
	}
	return maxMoney.setScale(2, RoundingMode.HALF_UP);
    }

}

class RedPacket {
    
    private Integer redPacketCount = 0;
    
    private BigDecimal redPacketMoney = new BigDecimal(0.00);

    
    public Integer getRedPacketCount() {
        return redPacketCount;
    }

    
    public void setRedPacketCount(Integer redPacketCount) {
        this.redPacketCount = redPacketCount;
    }


    
    public BigDecimal getRedPacketMoney() {
        return redPacketMoney;
    }


    
    public void setRedPacketMoney(BigDecimal redPacketMoney) {
        this.redPacketMoney = redPacketMoney;
    }
}
 