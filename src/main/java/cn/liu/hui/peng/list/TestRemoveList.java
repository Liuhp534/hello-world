package cn.liu.hui.peng.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hz16092620
 * @date 2018年4月2日 上午10:19:11
 */
public class TestRemoveList {

    public static void main(String[] args) {
        //removeList();
        //copyList();
        removeOtherList();
        //testListAndSet();
        //testVarI();
    }

    /**
     * 避免冗余的计算极值，前提是改极值不会变动。
     */
    static void testVarI() {
        List<String> lista = new ArrayList<String>();
        lista.add("1");
        lista.add("2");
        lista.add("3");

        for (int i = 0, n = lista.size(); i < n; i++) {
            System.out.println(lista.get(i));
        }
    }

    /**
     * set [1, 2, 3, 4, 5, 6, 7, 8, 9]
     * list [1, 2, 3, 4, 5, 6, 7, 8, 9]
     * set [5, 6, 7, 8, 9]
     * list [2, 4, 6, 8]
     */
    static void testListAndSet() {
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
            set.add(i);
        }
        System.out.println("set " + set);
        System.out.println("list " + list);

        for (int i = 0; i < 5; i++) {
            set.remove(i);
            list.remove(i);
        }

        System.out.println("set " + set);
        System.out.println("list " + list);

    }

    /**
     * 静态工厂方法与构造器，方便使用，有名称，但是这个只是一个空的list，不支持添加、删除等其他操作
     */
    static void emptyList() {
        List<String> list = Collections.emptyList();
        list.add("a");
        System.out.println(list);
    }

    static void removeOtherList() {
        List<String> lista = new ArrayList<String>();
        lista.add("1");
        lista.add("2");
        lista.add("3");

        Map<String, Object> map = new HashMap<>();
        map.put("1", new Object());
        map.put("2", new Object());
        map.put("3", new Object());

        List<String> listb = new ArrayList<String>();
        listb.add("1");
        listb.add("2");

        for (int i = 0; i < listb.size(); i++) {
            lista.remove(listb.get(i));
            map.remove(listb.get(i));
        }

        System.out.println(lista.toString());
        System.out.println(map.toString());
    }

    /**
     * 数据的复制list
     */
    static void copyList() {
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");

        //List<String> copyList = new ArrayList<String>(list);//这里是复制对象
        List<String> copyList = new ArrayList<String>();
        copyList.addAll(list);//添加的复制品
        list.remove(0);

        System.out.println("list.size " + list.size());
        System.out.println("copyList.size " + copyList.size());
        System.out.println(copyList.toString());
    }

    /**
     * 移除功能
     */
    public static void removeList() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");
	
	/*String string = "ccc";
	if ("ccc".equals(string)) {
	    list.remove(string);
	}*/
	
/*	for (String string : list) {
	    if ("ccc".equals(string)) {
		list.remove(string);
	    }
        }*/
        for (int i = 0; i < list.size(); i++) {
            if ("ddd".equals(list.get(i))) {
                list.remove(i);
                i--;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
 