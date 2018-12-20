package cn.liu.hui.peng.wheel.design.pattern.listener.impl; 

import cn.liu.hui.peng.wheel.design.pattern.listener.Handler;


/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年9月20日 下午5:43:11
 * @version      
 */
public class TwoHandler implements Handler {

    @Override
    public void handler(String param) {
	System.out.println("two handler param " + param);
    }

}
 