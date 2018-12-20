package cn.liu.hui.peng.ioc.setter; 

/**
 * @author	hz16092620 
 * @date	2018年3月29日 上午11:45:52
 * @version      
 */
public class MyNewsProvider implements CommonNewsProvider {
    
    private CommonNewsListener commonNewsListener;

    @Override
    public void injectNewsListener(CommonNewsListener commonNewsListener) {
	this.commonNewsListener = commonNewsListener;
    }
    
    public void getAndPersistNews() {
	commonNewsListener.getNews();
	System.out.println("保存新闻....");
    }
    
    
    public static void main(String[] args) {
	MyNewsProvider my = new MyNewsProvider();
	//ioc接口注入，根据注入的不同
	//my.injectNewsListener(new DowJonesNewsListener());
	my.injectNewsListener(new PeopleDayNewsListener());
	//获取新闻
	my.getAndPersistNews();
    }

}
 