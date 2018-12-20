package cn.liu.hui.peng.excel; 

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年10月9日 下午5:58:15
 * @version      
 */
public class PolicyRangeDTO implements Comparable<PolicyRangeDTO> {
    
    private String insureNumStr;
    
    private int insurantCount;
    
    private int relationCount;
    
    private int type;//1-前海重复的，2-中意自增的保单号
    
    
    
    @Override
    public int compareTo(PolicyRangeDTO o) {
	if (this.insurantCount > o.getInsurantCount()) {
	    return 1;
	} else if (this.insurantCount < o.getInsurantCount()) {
	    return -1;
	} 
	return 0;
    }


    public String getInsureNumStr() {
        return insureNumStr;
    }

    
    public void setInsureNumStr(String insureNumStr) {
        this.insureNumStr = insureNumStr;
    }

    
    public int getInsurantCount() {
        return insurantCount;
    }

    
    public void setInsurantCount(int insurantCount) {
        this.insurantCount = insurantCount;
    }

    
    public int getRelationCount() {
        return relationCount;
    }

    
    public void setRelationCount(int relationCount) {
        this.relationCount = relationCount;
    }


    
    public int getType() {
        return type;
    }


    
    public void setType(int type) {
        this.type = type;
    }
    
    

}
 