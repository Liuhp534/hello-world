package cn.liu.hui.peng.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hz16092620
 * @date 2018年5月29日 下午3:45:29
 * @version
 */
public class DemoServer extends Thread {

    private ServerSocket serverSocket;
    
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public int getPort() {
	return serverSocket.getLocalPort();
    }

    public void run() {
	try {
	    serverSocket = new ServerSocket(0);
	    while (true) {
		Socket socket = serverSocket.accept();
		RequestHandler requestHandler = new RequestHandler(socket);
		executorService.execute(requestHandler);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (serverSocket != null) {
		try {
		    serverSocket.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DemoServer server = new DemoServer();
        server.start();
	while (true) {
	    try (Socket client = new Socket(InetAddress.getLocalHost(), server.getPort())) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		System.out.println(bufferedReader.readLine());
	    }
	}
    }
}

// 简化实现，不做读取，直接发送字符串
class RequestHandler extends Thread {
    
    private static Integer count = 0;

    private Socket socket;

    RequestHandler(Socket socket) {
	this.socket = socket;
    }

    @Override
    public void run() {
	try (PrintWriter out = new PrintWriter(socket.getOutputStream());) {
	    count ++;
	    System.out.println(Thread.currentThread().getName());
	    out.println("Hello world!" + count);
	    out.flush();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
