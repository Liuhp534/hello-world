package cn.liu.hui.peng.ioc.setter; 

/**
 * @author	hz16092620 
 * @date	2018年3月29日 上午11:47:38
 * @version      
 */
public class DowJonesNewsListener implements CommonNewsListener {

    @Override
    public void getNews() {
	System.out.println("获取DowJones新闻。。。。");
    }
    

}
 