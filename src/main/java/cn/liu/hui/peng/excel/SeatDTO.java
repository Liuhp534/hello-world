package cn.liu.hui.peng.excel; 

/**
 * @author	hz16092620 
 * @date	2018年3月12日 上午10:52:01
 * @version      
 */
public class SeatDTO {
    
    private Integer seatId;
    
    private String seatName;
    
    private Integer seatType;
    
    private String seatNum;
    
    private String seatPhone;

    
    public SeatDTO(Integer seatId, String seatName, Integer seatType, String seatNum, String seatPhone) {
	this.seatId = seatId;
	this.seatName = seatName;
	this.seatType = seatType;
	this.seatNum = seatNum;
	this.seatPhone = seatPhone;
    }
    
    
    public Integer getSeatType() {
        return seatType;
    }



    
    public void setSeatType(Integer seatType) {
        this.seatType = seatType;
    }



    public Integer getSeatId() {
        return seatId;
    }

    
    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    
    public String getSeatName() {
        return seatName;
    }

    
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    
    public String getSeatNum() {
        return seatNum;
    }

    
    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    
    public String getSeatPhone() {
        return seatPhone;
    }

    
    public void setSeatPhone(String seatPhone) {
        this.seatPhone = seatPhone;
    }
    
    

}
 