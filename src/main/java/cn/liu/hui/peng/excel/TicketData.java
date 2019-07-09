package cn.liu.hui.peng.excel;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @description: 彩票实体
 * @author: liuhp534
 * @create: 2019-05-25 15:55
 */
public class TicketData {

    private Integer id;

    private Integer periodNum;

    private Integer dataType;

    private String position1;

    private String position2;

    private String position3;

    private String position4;

    private String position5;

    private String position6;

    private String special;

    private Integer deleted;

    private String createTime;

    private Set<String> allAnimalSet = new LinkedHashSet<>();

    private List<String> allAnimalList = new LinkedList<>();

    private Set<String> allNumberSet = new LinkedHashSet<>();

    public Integer getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(Integer periodNum) {
        this.periodNum = periodNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    public String getPosition3() {
        return position3;
    }

    public void setPosition3(String position3) {
        this.position3 = position3;
    }

    public String getPosition4() {
        return position4;
    }

    public void setPosition4(String position4) {
        this.position4 = position4;
    }

    public String getPosition5() {
        return position5;
    }

    public void setPosition5(String position5) {
        this.position5 = position5;
    }

    public String getPosition6() {
        return position6;
    }

    public void setPosition6(String position6) {
        this.position6 = position6;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Set<String> getAllAnimalSet() {
        allAnimalSet.add(this.position1);
        allAnimalSet.add(this.position2);
        allAnimalSet.add(this.position3);
        allAnimalSet.add(this.position4);
        allAnimalSet.add(this.position5);
        allAnimalSet.add(this.position6);
        allAnimalSet.add(this.special);
        return allAnimalSet;
    }

    public List<String> getAllAnimalList() {
        allAnimalList.add(this.position1);
        allAnimalList.add(this.position2);
        allAnimalList.add(this.position3);
        allAnimalList.add(this.position4);
        allAnimalList.add(this.position5);
        allAnimalList.add(this.position6);
        allAnimalSet.add(this.special);
        return allAnimalList;
    }

    public Set<String> getAllNumberSet() {
        allNumberSet.add(this.position1);
        allNumberSet.add(this.position2);
        allNumberSet.add(this.position3);
        allNumberSet.add(this.position4);
        allNumberSet.add(this.position5);
        allNumberSet.add(this.position6);
        //allAnimalSet.add(this.special);
        return allNumberSet;
    }

    public void setAllAnimalSet(Set<String> allAnimalSet) {
        this.allAnimalSet = allAnimalSet;
    }
}
