package com.stonedt.intelligence.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.WebSocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WebSocketUtils {
	static JSONArray array = new JSONArray();
	
	public static void main(String[] args) {
		
		String xml = "";
		
	String data = data("疫情",xml,"1");
//	System.out.println(data);
	}
	
    public static String data(String keyword,String xml,String pageNoData){
    	
    	long start = System.nanoTime();
    	
    	array.clear();
        String message = null;
        xml = xml.replaceAll("北京", keyword);
        xml = xml.replaceAll("pageNoData", pageNoData);
        System.out.println(xml);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("eventType", "test");
        jsonObject.put("message", xml);
        
        String data = jsonObject.toJSONString();
        
        
        try {
            //实例WebSocketClient对象，并连接到WebSocket服务端
            MyWebSocketClient client = new MyWebSocketClient(new URI("ws://s1.stonedt.com:6388/ws"));
            client.connect();
            //等待服务端响应
            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("连接中···请稍后");
                Thread.sleep(1000);
            }
            //向WebSocket服务端发送数据
            client.send(data);
            
            
            //等待WebSocket服务端响应
//            while((message = client.getExcptMessage())==null){
//                System.out.println("服务忙等待...");
//                Thread.sleep(1000);
//            }
            
            while((message = client.getExcptMessage())==null||!JSONObject.parseObject(message).getString("eventType").equals("finish")){
              System.out.println("服务忙等待...");
              Thread.sleep(500);
          }
            
            
            
            //打印服务端返回的数据
            System.out.println("成功获取数据：" + message);
            //关闭连接
            client.close();
         //   System.out.println(array);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        
		return array.toJSONString();
    } 
    
}
