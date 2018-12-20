package cn.liu.hui.peng.ioc.setter; 

/**
 * @author	hz16092620 
 * @date	2018年3月29日 上午11:48:38
 * @version      
 */
public class PeopleDayNewsListener implements CommonNewsListener {

    @Override
    public void getNews() {
	System.out.println("获取人民日报新闻。。。。");
    }

}
 