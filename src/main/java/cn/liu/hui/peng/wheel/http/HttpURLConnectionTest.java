package cn.liu.hui.peng.wheel.http; 

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * 
 *
 *
 * </p>
 * @author	hz16092620 
 * @date	2018年8月30日 下午4:46:31
 * @version      
 */
public class HttpURLConnectionTest {
    
    public static void main(String[] args) {
	//toInsure();
	testHttpGet();
    }
    
    /**
     * Test 承保
     * */
    static void toInsure() {
	String url = "https://tuneapi.qixin18.com/api/simpleInsure?sign=ddd1fe49ca5ef73de1b2772fbfc5c258";
	testHttpPost(url, toObj().toJSONString());
    }
    
    static JSONObject toObj() {
	JSONObject obj = new JSONObject(true);
	obj.put("transNo", "huize20180518000004");
	obj.put("partnerId", 1021430);
	obj.put("caseCode", "QX000000002606");
	obj.put("startDate", "2018-09-15");
	obj.put("endDate", "2019-09-14");
	JSONObject applicant = new JSONObject(true);
	applicant.put("cardType", 1);
	applicant.put("cardCode", "420625199206099819");
	applicant.put("mobile", "13925953457");
	applicant.put("email", "617456341@qq.com");
	applicant.put("cName", "刘备测试");
	applicant.put("sex", 1);
	applicant.put("birthday", "1992-06-09");
	JSONArray insurants = new JSONArray();
	JSONObject insurant = new JSONObject(true);
	insurant.put("insurantId", 101);
	insurant.put("cardType", 1);
	insurant.put("cardCode", "420625199206099819");
	insurant.put("relationId", 1);
	insurant.put("count", 1);
	insurant.put("singlePrice", 6000);
	insurant.put("cName", "刘备测试");
	insurant.put("job", "279434-279435-279436");
	insurant.put("sex", 1);
	insurant.put("birthday", "1992-06-09");
	insurants.add(insurant);
	JSONObject otherInfo = new JSONObject(true);
	JSONObject extMap = new JSONObject(true);
	extMap.put("购买份数", "1份");
	extMap.put("承保年龄", "18-65周岁");
	extMap.put("承保职业", "1-3类");
	extMap.put("保障期限", "1年");
	otherInfo.put("extMap", extMap);
	//加入数据
	obj.put("applicant", applicant);
	obj.put("insurants", insurants);
	obj.put("otherInfo", otherInfo);
	System.out.println(obj.toJSONString());
	return obj;
    }
    
    /**
     * 
     * java通过apache httpClient post测试
     * */
    static void testHttpPost(String url, String param) {
	//创建请求对象
	CloseableHttpClient httpClient = HttpClients.createDefault();
	CloseableHttpResponse httpResponse = null;
	HttpPost httpPost = new HttpPost(url);
	httpPost.addHeader("Content-Type", "application/json");//请求内容的格式
	
	//参数设置
	StringEntity stringEntity = new StringEntity(param, "utf-8");//接收json字符串
	stringEntity.setContentType("application/json");//请求内容的格式
	httpPost.setEntity(stringEntity);
	
	try {
	    //执行
	    httpResponse = httpClient.execute(httpPost);
	    // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            //解析数据
            String result = EntityUtils.toString(entity);
            System.out.println(result);
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (null != httpResponse) {
		try {
		    httpResponse.close();
		} catch (IOException e) {
		}
	    }
	    if (httpClient != null) {
		try {
		    httpClient.close();
		} catch (IOException e) {
		}
	    }
	}
	// 设置参数application/x-www-form-urlencoded
	/*
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * BasicNameValuePair param1 = new BasicNameValuePair("partnerId",
	 * "1021430"); BasicNameValuePair param2 = new
	 * BasicNameValuePair("transNo", "huize20171027100140000008");
	 * params.add(param1); params.add(param2); UrlEncodedFormEntity
	 * urlEntity = new UrlEncodedFormEntity(params,"utf-8");
	 * urlEntity.setContentType("application/json");
	 */
	/*
	 * //参数方式一 String paramStr =
	 * "{\"partnerId\":1021430,\"transNo\":\"huize20171027100140000008\"}";
	 */
	/*
	 * //参数方式二 Map<String, Object> map = new LinkedHashMap<>();
	 * map.put("partnerId", 1021430); map.put("transNo",
	 * "huize20171027100140000008");
	 */
    }
    
    /**
     * 
     * java通过apache httpClient get测试
     * */
    static void testHttpGet() {
	String url = "https://www.baidu.com";
	CloseableHttpClient httpClient = HttpClients.createDefault();
	HttpGet httpGet = new HttpGet(url);
	//ConnectTimeout访问主机时间，ConnectionRequestTimeout请求时间，SocketTimeout读取服务器数据时间
	RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(1500).setSocketTimeout(10000).build();
	httpGet.setConfig(config);
	
	try {
	    CloseableHttpResponse response = httpClient.execute(httpGet);
	    HttpEntity result = response.getEntity();
	    InputStream in = result.getContent();
	    Reader reader = new InputStreamReader(in, "utf-8");
	    //BufferedReader buffer = new BufferedReader(reader);
	    String temp = null;
	    int c = 0;
	    char[] chars = new char[1024];
	    StringBuilder sb = new StringBuilder();
	    while ((c = reader.read(chars)) != -1) {
		sb.append(chars, 0, c);
	    }
	    System.out.println(sb.toString());
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * 测试post请求
     * */
    static void testPost() {
	HttpURLConnection con = null;
	OutputStream out = null;
	InputStream in = null;
	Reader reader = null;
	BufferedReader buffer = null;
	StringBuilder sb = new StringBuilder();
	String urlStr = "https://tuneapi.qixin18.com/border/productList?sign=32705bad93f0a7b6b1274f258a565891";
	try {
	    String paramStr = "{\"partnerId\":1021430,\"transNo\":\"huize20171027100140000008\"}";
	    URL url = new URL(urlStr);
	    con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("POST");//设置请求方法
	    con.setDoOutput(true);//设置可以发送参数
	    con.setRequestProperty("Content-Type", "application/json");//请求内容格式application/x-www-form-urlencoded
	    out = con.getOutputStream();//获取输出流
	    out.write(paramStr.getBytes());//输出参数
	    //con.connect();
	    //获取输入流获取返回的数据。
	    in = con.getInputStream();
	    reader = new InputStreamReader(in, "utf-8");//iso-8859-1
	    buffer = new BufferedReader(reader);
	    String temp = null;
	    while (null != (temp = buffer.readLine())) {
		sb.append(temp);
	    }
	    System.out.println(sb.toString());
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {//关闭相应的资源和连接
	    if (null != buffer) {
		try {
		    buffer.close();
		} catch (IOException e) {
		}
	    }
	    if (null != reader) {
		try {
		    reader.close();
		} catch (IOException e) {
		}
	    }
	    if (null != in) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	    if (null != out) {
		try {
		    out.close();
		} catch (IOException e) {
		}
	    }
	    con.disconnect();
	}
    }
    
    
    /**
     * get 请求
     * */
    static void testGet() {
	String urlStr = "https://tuneapi.qixin18.com/border/productList?sign=62a90b62a43d112fa605a25e1a52a044";
	urlStr = "http://www.baidu.com";
	try {
	    URL url = new URL(urlStr);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.connect();
	    InputStream in = con.getInputStream();
	    BufferedInputStream buffer = new BufferedInputStream(in);
	    InputStreamReader read = new InputStreamReader(buffer);
	    int c = -1;
	    while ((c = read.read()) != -1) {
		System.out.print((char) c);
	    }
	    /*if (con.getResponseCode() == 200) {
		
	    }*/
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    
	}
    }

}
 