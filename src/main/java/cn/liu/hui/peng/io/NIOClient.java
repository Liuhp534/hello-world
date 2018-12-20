package cn.liu.hui.peng.io; 

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author	hz16092620 
 * @date	2018年5月30日 下午4:01:21
 * @version      
 */
public class NIOClient {
    private static Selector selector;
    
    public static void main(String[] args) throws IOException {
	SocketChannel clientChannel = SocketChannel.open();
	clientChannel.configureBlocking(false);
	selector = Selector.open();
	clientChannel.register(selector, SelectionKey.OP_CONNECT);
	
	clientChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8888));
	while (true) {
	    selector.select();
	    Set<SelectionKey> sets = selector.selectedKeys();
	    Iterator<SelectionKey> it = sets.iterator();
	    while (it.hasNext()) {
		SelectionKey key = it.next();
		fun(key);
	    }
	    sets.clear();
	}
    }
    
    private static void fun(SelectionKey key) throws ClosedChannelException {
	if (key.isConnectable()) {
	    SocketChannel clientChannel = (SocketChannel) key.channel();
	    clientChannel.register(selector, SelectionKey.OP_READ);
	    System.out.println("Acceptable");
	}
    }

}
 