package cn.liu.hui.peng.post;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author hz16092620
 * @date 2018年3月15日 下午3:46:36
 * @version
 */
public class TestPost {
    
    public static void main(String[] args) {
	jsonPost("http://www.baidu.com", null);
    }

    /**
     * 发送HttpPost请求
     * 
     * @param strURL 服务地址
     * @param params
     * 
     * @return 成功:返回json字符串<br/>
     */
    public static String jsonPost(String strURL, Map<String, Object> params) {
	try {
	    URL url = new URL(strURL);// 创建连接
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setDoOutput(true);
	    connection.setDoInput(true);
	    connection.setUseCaches(false);
	    connection.setInstanceFollowRedirects(true);
	    connection.setRequestMethod("POST"); // 设置请求方式
	    connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
	    connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
	    connection.connect();
	    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
	    //开始访问
	    StringBuilder postData = new StringBuilder();
	    if (params != null) {
		for (Map.Entry<String, Object> param : params.entrySet()) {
		    if (postData.length() != 0)
			postData.append('&');
		    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		    postData.append('=');
		    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
	    }
	    out.append(postData);
	    out.flush();
	    out.close();

	    int code = connection.getResponseCode();
	    InputStream is = null;
	    if (code == 200) {
		is = connection.getInputStream();
	    } else {
		is = connection.getErrorStream();
	    }

	    // 读取响应
	    int length = (int) connection.getContentLength();// 获取长度
	    if (length != -1) {
		byte[] data = new byte[length];
		byte[] temp = new byte[512];
		int readLen = 0;
		int destPos = 0;
		while ((readLen = is.read(temp)) > 0) {
		    System.arraycopy(temp, 0, data, destPos, readLen);
		    destPos += readLen;
		}
		String result = new String(data, "UTF-8"); // utf-8编码
		return result;
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	    System.out.println("-----------------------------");
	}
	return "error"; // 自定义错误信息
    }
}
